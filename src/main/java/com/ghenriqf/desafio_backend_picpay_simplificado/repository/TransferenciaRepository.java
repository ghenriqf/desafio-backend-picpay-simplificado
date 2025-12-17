package com.ghenriqf.desafio_backend_picpay_simplificado.repository;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.transferencia.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaRepository extends JpaRepository<Transferencia,Long> {
}
