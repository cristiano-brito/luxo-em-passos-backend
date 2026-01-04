package br.com.luxoempassos.model.venda;

import br.com.luxoempassos.model.produto.Sandalia;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_sku")
    private Sandalia sandalia;

    private int quantidade;

    @Column(name = "preco_venda_ato")
    private BigDecimal precoVendaNoAto;

    protected ItemPedido() {}

    public ItemPedido(Sandalia sandalia, int quantidade, BigDecimal precoVendaNoAto) {
        this.sandalia = sandalia;
        this.quantidade = quantidade;
        this.precoVendaNoAto = precoVendaNoAto;
    }

    public BigDecimal calcularSubtotal() {
        return precoVendaNoAto.multiply(BigDecimal.valueOf(quantidade));
    }

    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public Sandalia getSandalia() { return sandalia; }
    public int getQuantidade() { return quantidade; }
    public BigDecimal getPrecoVendaNoAto() { return precoVendaNoAto; }
}
