package com.ghenriqf.desafio_backend_picpay_simplificado.service;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.NotificacaoDTO;
import com.ghenriqf.desafio_backend_picpay_simplificado.exceptions.ServicoExternoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacaoService {
    @Value("${email.service.url}")
    private String emailServiceUrl;

    private final RestTemplate restTemplate;

    public NotificacaoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarNotificacao (Usuario usuario, String mensagem) throws Exception {
        String email = usuario.getEmail();
        NotificacaoDTO notificacaoDTO = new NotificacaoDTO(email,mensagem);

        ResponseEntity<String> notificacaoResponse = restTemplate.postForEntity(emailServiceUrl, notificacaoDTO, String.class);

        if (!(notificacaoResponse.getStatusCode() == HttpStatus.OK)) {
            throw new ServicoExternoException("Serviço de envio de email não ativo.");
        }
    }
}
