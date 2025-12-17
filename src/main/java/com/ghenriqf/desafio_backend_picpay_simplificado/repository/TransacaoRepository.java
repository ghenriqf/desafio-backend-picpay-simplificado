package com.ghenriqf.desafio_backend_picpay_simplificado.repository;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.transferencia.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao,Long> {
}
