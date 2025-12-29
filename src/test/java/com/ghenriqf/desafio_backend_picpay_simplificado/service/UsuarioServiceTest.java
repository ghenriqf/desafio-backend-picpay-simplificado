package com.ghenriqf.desafio_backend_picpay_simplificado.service;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.TipoDoUsuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.exceptions.AutorizacaoException;
import com.ghenriqf.desafio_backend_picpay_simplificado.exceptions.SaldoInsuficienteException;
import com.ghenriqf.desafio_backend_picpay_simplificado.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void validarTransacao_quandoUsuarioNaoAutorizado_deveLancarExcecao() {
        Usuario usuarioLojista = new Usuario();
        usuarioLojista.setTipo(TipoDoUsuario.LOJISTA);

        AutorizacaoException exception = assertThrows(AutorizacaoException.class, () -> {
            usuarioService.validarTransacao(usuarioLojista, new BigDecimal(10));
        });
        assertEquals("Usuário do tipo lojista não é autorizado para realizar transação.",exception.getMessage());
    }

    @Test
    void validarTransacao_quandoSaldoInsuficiente_deveLancarExcecao() {
        Usuario usuarioComSaldoInsuficiente = new Usuario();
        usuarioComSaldoInsuficiente.setTipo(TipoDoUsuario.COMUM);
        usuarioComSaldoInsuficiente.setSaldo(new BigDecimal(5));

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class, () -> {
            usuarioService.validarTransacao(usuarioComSaldoInsuficiente, new BigDecimal(10));
        });

        assertEquals("Usuário com saldo insuficiente.",exception.getMessage());
    }
}