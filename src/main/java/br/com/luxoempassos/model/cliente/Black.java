package br.com.luxoempassos.model.cliente;

import java.math.BigDecimal;

public record Black() implements PerfilFidelidade {
    @Override
    public BigDecimal aplicarDesconto(BigDecimal valor) {
        return valor.multiply(new BigDecimal("0.90")); // 10% de exclusividade
    }

    @Override
    public Boolean possuiDireitoAFreteGratis() {
        return true; // Benefício elite
    }

    @Override
    public String descricao() { return "BLACK (10% OFF)"; }
}
