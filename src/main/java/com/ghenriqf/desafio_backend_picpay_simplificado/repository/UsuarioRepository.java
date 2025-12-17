package com.ghenriqf.desafio_backend_picpay_simplificado.repository;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByCpf(String cpf);
    Optional<Usuario> findUsuarioById(Long id);
}
