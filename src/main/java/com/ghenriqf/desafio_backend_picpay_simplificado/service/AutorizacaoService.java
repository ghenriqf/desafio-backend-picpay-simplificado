package com.ghenriqf.desafio_backend_picpay_simplificado.service;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AutorizacaoService {

    private final RestTemplate restTemplate;
    @Value("${authorize.service.url}")
    private String authorizeServiceUrl;

    public AutorizacaoService(RestTemplate restTemplate, String authorizeServiceUrl) {
        this.restTemplate = restTemplate;
    }
    public boolean autorizacaoTransacao (Usuario usuario, BigDecimal valor) {
        ResponseEntity<Map> autorizacaoResponse = restTemplate.getForEntity(authorizeServiceUrl, Map.class);

        if (autorizacaoResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) autorizacaoResponse.getBody().get("message");
            return "autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
