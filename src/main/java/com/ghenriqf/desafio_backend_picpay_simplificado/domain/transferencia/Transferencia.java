package com.ghenriqf.desafio_backend_picpay_simplificado.domain.transferencia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transferencias")
@Table(name = "transferencias")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private User remetente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private User destinatario;

    private LocalDateTime dataHora;
}
