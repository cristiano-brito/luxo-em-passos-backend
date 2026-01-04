package br.com.luxoempassos.model.cliente;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Gasto(BigDecimal valor, LocalDate data) {
}
