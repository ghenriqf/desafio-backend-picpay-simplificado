package com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario;

import com.ghenriqf.desafio_backend_picpay_simplificado.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "usuarios")
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String senha;

    private BigDecimal saldo;

    private TipoDoUsuario tipo;

    public Usuario(UsuarioDTO usuarioDTO) {
        this.nomeCompleto = usuarioDTO.nomeCompleto();
        this.cpf = usuarioDTO.cpf();
        this.email = usuarioDTO.cpf();
        this.saldo = usuarioDTO.saldo();
        this.tipo = usuarioDTO.tipoDoUsuario();
        this.senha = usuarioDTO.senha();
    }
}
