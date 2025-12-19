package com.ghenriqf.desafio_backend_picpay_simplificado.service;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.TipoDoUsuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.UsuarioRequest;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.UsuarioResponse;
import com.ghenriqf.desafio_backend_picpay_simplificado.exceptions.AutorizacaoException;
import com.ghenriqf.desafio_backend_picpay_simplificado.exceptions.SaldoInsuficienteException;
import com.ghenriqf.desafio_backend_picpay_simplificado.exceptions.UsuarioNaoEncontradoException;
import com.ghenriqf.desafio_backend_picpay_simplificado.mapper.UsuarioMapper;
import com.ghenriqf.desafio_backend_picpay_simplificado.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse criarUsuario (UsuarioRequest dto) {
        Usuario novoUsuario = usuarioMapper.toEntity(dto);
        usuarioRepository.save(novoUsuario);
        return usuarioMapper.toDTO(novoUsuario);
    }

    public void validarTransacao(Usuario usuario, BigDecimal valorTransferencia) throws Exception {
        if (usuario.getTipo() == TipoDoUsuario.LOJISTA) {
            throw new AutorizacaoException("Usuário do tipo lojista não é autorizado para realizar transação.");
        }

        if (usuario.getSaldo().compareTo(valorTransferencia) < 0) {
            throw new SaldoInsuficienteException("Usuário com saldo insuficiente.");
        }
    }

    public Usuario findUsuarioById(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findUsuarioById(id).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
        return usuario;
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public List<UsuarioResponse> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuarioMapper::toDTO).toList();
    }
}
