package com.ghenriqf.desafio_backend_picpay_simplificado.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }
}
