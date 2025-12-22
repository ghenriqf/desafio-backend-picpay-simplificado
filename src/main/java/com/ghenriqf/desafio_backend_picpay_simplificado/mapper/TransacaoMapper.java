package com.ghenriqf.desafio_backend_picpay_simplificado.mapper;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.transacao.Transacao;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.TransacaoResponse;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {

    private final UsuarioMapper usuarioMapper;

    public TransacaoMapper(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    public TransacaoResponse toDto(Transacao transacao) {
        return new TransacaoResponse(
                transacao.getId(),
                transacao.getValor(),
                usuarioMapper.toDTO(transacao.getRemetente()),
                usuarioMapper.toDTO(transacao.getDestinatario()),
                transacao.getDataHora()
        );
    }
}
