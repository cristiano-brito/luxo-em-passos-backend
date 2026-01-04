package br.com.luxoempassos.model.venda;

import br.com.luxoempassos.model.produto.Sandalia;

import java.math.BigDecimal;

public record ItemPedido(
        Sandalia sandalia,
        int quantidade,
        BigDecimal precoVendaNoAto // Preço unitário capturado da sandália
) {
    public BigDecimal calcularSubtotal() {
        return precoVendaNoAto.multiply(BigDecimal.valueOf(quantidade));
    }
}
