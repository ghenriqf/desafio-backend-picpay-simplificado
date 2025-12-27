package com.ghenriqf.desafio_backend_picpay_simplificado.service;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.transacao.Transacao;
import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.TransacaoRequest;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.TransacaoResponse;
import com.ghenriqf.desafio_backend_picpay_simplificado.exceptions.AutorizacaoException;
import com.ghenriqf.desafio_backend_picpay_simplificado.mapper.TransacaoMapper;
import com.ghenriqf.desafio_backend_picpay_simplificado.mapper.UsuarioMapper;
import com.ghenriqf.desafio_backend_picpay_simplificado.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final NotificacaoService notificacaoService;
    private final UsuarioService usuarioService;
    private final TransacaoMapper transacaoMapper;
    private final AutorizacaoService autorizacaoService;

    @Value("${authorize.service.url}")
    private String authorizeServiceUrl;

    public TransacaoService(TransacaoRepository transacaoRepository, NotificacaoService notificacaoService, UsuarioService usuarioService, RestTemplate restTemplate, UsuarioMapper usuarioMapper, TransacaoMapper transacaoMapper, AutorizacaoService autorizacaoService) {
        this.transacaoRepository = transacaoRepository;
        this.notificacaoService = notificacaoService;
        this.usuarioService = usuarioService;
        this.transacaoMapper = transacaoMapper;
        this.autorizacaoService = autorizacaoService;
    }

    @Transactional(rollbackOn = Exception.class)
    public TransacaoResponse criarTransacao (TransacaoRequest transacaoRequest)  {
        Usuario remetente = usuarioService.findUsuarioById(transacaoRequest.remetenteId());
        Usuario destinatario = usuarioService.findUsuarioById(transacaoRequest.destinatarioId());

        usuarioService.validarTransacao(remetente, transacaoRequest.valor());

        boolean isAutorizado = autorizacaoService.autorizacaoTransacao(remetente, transacaoRequest.valor());

        if (!isAutorizado) {
            throw new AutorizacaoException("Transação não autorizada.");
        }

        Transacao transacao = new Transacao();
        transacao.setValor(transacaoRequest.valor());
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

        return transacaoMapper.toDto(transacao);
    }
}
