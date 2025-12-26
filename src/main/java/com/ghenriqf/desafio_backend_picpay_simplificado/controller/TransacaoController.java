package com.ghenriqf.desafio_backend_picpay_simplificado.controller;

import com.ghenriqf.desafio_backend_picpay_simplificado.dto.TransacaoRequest;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.TransacaoResponse;
import com.ghenriqf.desafio_backend_picpay_simplificado.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
@Tag(name = "Transações", description = "Endpoints de transações financeiras")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Operation(
            summary = "Criar transação",
            description = "Cria uma nova transação com os dados fornecidos no corpo da requisição."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "403", description = "Ação não autorizada"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "422", description = "Saldo insuficiente"),
                    @ApiResponse(responseCode = "503", description = "Serviço externo indisponível")
            }
    )
    @PostMapping
    public ResponseEntity<TransacaoResponse> criarTransacao (@RequestBody @Valid TransacaoRequest transacaoRequest) throws Exception {
        TransacaoResponse response = transacaoService.criarTransacao(transacaoRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
