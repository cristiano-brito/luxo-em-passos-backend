package br.com.luxoempassos.model.produto;

import java.math.BigDecimal;

public enum Categoria {
    RASTEIRINHA(new BigDecimal("1.50")),    // 50% de markup
    SALTO_ALTO(new BigDecimal("1.80")),     // 80% de markup
    SCARPIN(new BigDecimal("1.75")),        // 75% de markup (o padrão anterior)
    EDICAO_LIMITADA(new BigDecimal("2.50")); // 150% de markup

    private final BigDecimal markup;

    Categoria(BigDecimal markup) {
        this.markup = markup;
    }

    public BigDecimal getMarkup() {
        return markup;
    }
}
