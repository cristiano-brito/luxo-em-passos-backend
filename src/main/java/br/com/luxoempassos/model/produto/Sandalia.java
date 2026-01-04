package br.com.luxoempassos.model.produto;

import br.com.luxoempassos.exception.EstoqueInsuficienteException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Sandalia {
    private final String sku; // Identificador único de estoque (ex: SND-001)
    private final String modelo;
    private final int tamanho;
    private final Categoria categoria;
    private BigDecimal precoCusto; // Valor pago à fábrica
    private BigDecimal precoVenda; // Valor com markup de 75%
    private int estoque;

    private static final BigDecimal MARKUP = new BigDecimal("1.75");

    public Sandalia(String sku, String modelo, int tamanho, Categoria categoria, BigDecimal precoCustoInicial, int estoque) {
        this.sku = Objects.requireNonNull(sku, "SKU é obrigatório");
        this.modelo = Objects.requireNonNull(modelo, "Modelo é obrigatório");
        this.categoria = Objects.requireNonNull(categoria, "Categoria é obrigatória");
        this.tamanho = tamanho;
        this.estoque = estoque;
        definirPrecoCusto(precoCustoInicial);
    }

    public void definirPrecoCusto(BigDecimal novoCusto) {
        if (novoCusto == null || novoCusto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Custo deve ser maior que zero.");
        }
        this.precoCusto = novoCusto;

        // CÁLCULO DINÂMICO: Custo * Markup da Categoria
        this.precoVenda = novoCusto.multiply(this.categoria.getMarkup())
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void abastecerEstoque(int quantidade) {
        if (quantidade <= 0) return;
        this.estoque += quantidade;
    }

    public void baixarEstoque(int quantidade) {
        if (quantidade > this.estoque) {
            throw new EstoqueInsuficienteException(this.modelo, quantidade, this.estoque);
        }
        this.estoque -= quantidade;
    }

    public String getSku() { return sku; }
    public String getModelo() { return modelo; }
    public BigDecimal getPrecoVenda() { return precoVenda; }
    public Categoria getCategoria() { return categoria; }
    public BigDecimal getPrecoCusto() { return precoCusto; }
    public int getEstoque() { return estoque; }
}
