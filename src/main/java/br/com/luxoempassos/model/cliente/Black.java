package br.com.luxoempassos.model.cliente;

import java.math.BigDecimal;

public record Black() implements PerfilFidelidade {
    @Override
    public BigDecimal aplicarDesconto(BigDecimal valor) {
        return valor.multiply(new BigDecimal("0.85")); // 15% de exclusividade
    }

    @Override
    public Boolean possuiDireitoAFreteGratis() {
        return true; // Benefício elite
    }
}
