package com.ghenriqf.desafio_backend_picpay_simplificado.controller;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.transacao.Transacao;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.TransacaoDTO;
import com.ghenriqf.desafio_backend_picpay_simplificado.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<Transacao> criarTransacao (@RequestBody TransacaoDTO transacaoDTO) throws Exception {
        Transacao transacao = transacaoService.criarTransacao(transacaoDTO);
        return new ResponseEntity<>(transacao, HttpStatus.OK);
    }
}
