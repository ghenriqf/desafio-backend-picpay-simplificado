package com.ghenriqf.desafio_backend_picpay_simplificado.service;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.transacao.Transacao;
import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.TransacaoDTO;
import com.ghenriqf.desafio_backend_picpay_simplificado.exceptions.AutorizacaoException;
import com.ghenriqf.desafio_backend_picpay_simplificado.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final NotificacaoService notificacaoService;
    private final UsuarioService usuarioService;
    private final RestTemplate restTemplate;

    @Value("${authorize.service.url}")
    private String authorizeServiceUrl;

    public TransacaoService(TransacaoRepository transacaoRepository, NotificacaoService notificacaoService, UsuarioService usuarioService, RestTemplate restTemplate) {
        this.transacaoRepository = transacaoRepository;
        this.notificacaoService = notificacaoService;
        this.usuarioService = usuarioService;
        this.restTemplate = restTemplate;
    }

    public Transacao criarTransacao (TransacaoDTO transacaoDTO) throws Exception {
        Usuario remetente = usuarioService.findUsuarioById(transacaoDTO.remetenteId());
        Usuario destinatario = usuarioService.findUsuarioById(transacaoDTO.destinatarioId());

        usuarioService.validarTransacao(remetente,transacaoDTO.valor());

        boolean isAutorizado = autorizacaoTransacao(remetente,transacaoDTO.valor());

        if (!isAutorizado) {
            throw new AutorizacaoException("Transação não autorizada.");
        }

        Transacao transacao = new Transacao();
        transacao.setValor(transacaoDTO.valor());
        transacao.setRemetente(remetente);
        transacao.setDestinatario(destinatario);
        transacao.setDataHora(LocalDateTime.now());

        remetente.setSaldo(remetente.getSaldo().subtract(transacao.getValor()));
        destinatario.setSaldo(destinatario.getSaldo().add(transacao.getValor()));

        transacaoRepository.save(transacao);
        usuarioService.salvarUsuario(remetente);
        usuarioService.salvarUsuario(destinatario);
        notificacaoService.enviarNotificacao(remetente,"Transação realizada com sucesso.");
        notificacaoService.enviarNotificacao(destinatario,"Transação recebida com sucesso.");

        return transacao;
    }

    public boolean autorizacaoTransacao (Usuario usuario, BigDecimal valor) {
        ResponseEntity<Map> autorizacaoResponse = restTemplate.getForEntity(authorizeServiceUrl, Map.class);

        if (autorizacaoResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) autorizacaoResponse.getBody().get("message");
            return "autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
