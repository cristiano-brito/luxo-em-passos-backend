package br.com.luxoempassos.model.cliente;

import java.math.BigDecimal;

public record Gold() implements PerfilFidelidade {
    @Override
    public BigDecimal aplicarDesconto(BigDecimal valor) {
        return valor.multiply(new BigDecimal("0.95")); // 5% de cortesia
    }

    @Override
    public Boolean possuiDireitoAFreteGratis() {
        return true; // Benefício intermediário
    }

    @Override
    public String descricao() { return "GOLD (5% OFF)"; }
}
