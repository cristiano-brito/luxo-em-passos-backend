package br.com.luxoempassos.model.cliente;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
public record Gasto(BigDecimal valor, LocalDate data) {
}
