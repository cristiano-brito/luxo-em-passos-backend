package br.com.luxoempassos.model.cliente;

import java.math.BigDecimal;

public record Standard() implements PerfilFidelidade {
    @Override
    public BigDecimal aplicarDesconto(BigDecimal valor) {
        return valor;
    }

    @Override
    public Boolean possuiDireitoAFreteGratis() {
        return false; // Clientes padrão pagam frete
    }
}
