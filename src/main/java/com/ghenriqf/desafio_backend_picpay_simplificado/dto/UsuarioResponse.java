package com.ghenriqf.desafio_backend_picpay_simplificado.dto;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.TipoDoUsuario;

public record UsuarioResponse (
    Long id,
    String nomeCompleto,
    String email,
    TipoDoUsuario tipoDoUsuario
){

}
