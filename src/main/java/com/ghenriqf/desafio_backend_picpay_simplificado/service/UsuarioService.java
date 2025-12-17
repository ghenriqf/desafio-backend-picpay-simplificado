package com.ghenriqf.desafio_backend_picpay_simplificado.service;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.TipoDoUsuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validarTransacao(Usuario usuario, BigDecimal valorTransferencia) throws Exception {
        if (usuario.getTipo() == TipoDoUsuario.LOJISTA) {
            throw new Exception("Usuário do tipo lojista não é autorizado para realizar transação.");
        }

        if (usuario.getSaldo().compareTo(valorTransferencia) < 0) {
            throw new Exception("Usuário com saldo insuficiente.");
        }
    }

    public Usuario findUsuarioById(Long id) throws Exception {
        return usuarioRepository.findUsuarioById(id)
                .orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
