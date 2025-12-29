package com.ghenriqf.desafio_backend_picpay_simplificado.service;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.transacao.Transacao;
import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.TipoDoUsuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.TransacaoRequest;
import com.ghenriqf.desafio_backend_picpay_simplificado.mapper.TransacaoMapper;
import com.ghenriqf.desafio_backend_picpay_simplificado.repository.TransacaoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private NotificacaoService notificacaoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private AutorizacaoService autorizacaoService;

    @Mock
    private TransacaoMapper transacaoMapper;

    @InjectMocks
    private TransacaoService transacaoService;

    private AutoCloseable closeable;


    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void criarTransacao_quandoDadosValidos_deveCriarTransacaoComSucesso() {

        Usuario remetente = new Usuario(1L, "Remetente", "1234567890", "remetente@email.com","12345", new BigDecimal(100), TipoDoUsuario.COMUM);

        Usuario destinatario = new Usuario(2L,"Destinatario", "0987654321", "destinatario@email.com","54321", new BigDecimal(50), TipoDoUsuario.COMUM);

        // quando o metodo findUsuarioById for chamado com o ID 1L, retorne o usuário remetente
        when(usuarioService.findUsuarioById(1L)).thenReturn(remetente);
        when(usuarioService.findUsuarioById(2L)).thenReturn(destinatario);

        when(autorizacaoService.autorizacaoTransacao(any(), any())).thenReturn(true);

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal(50),1L,2L);
        transacaoService.criarTransacao(transacaoRequest);

        verify(transacaoRepository,times(1)).save(any());
        verify(usuarioService,times(1)).salvarUsuario(remetente);
        verify(usuarioService,times(1)).salvarUsuario(destinatario);

        verify(notificacaoService,times(1)).enviarNotificacao(remetente,"Transação realizada com sucesso.");
        verify(notificacaoService,times(1)).enviarNotificacao(destinatario,"Transação recebida com sucesso.");


        assertEquals(new BigDecimal("50"), remetente.getSaldo());
        assertEquals(new BigDecimal("100"), destinatario.getSaldo());

        verify(usuarioService, times(2)).salvarUsuario(any(Usuario.class));
    }
}