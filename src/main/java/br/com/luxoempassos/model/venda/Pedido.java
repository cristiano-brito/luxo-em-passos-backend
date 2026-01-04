package br.com.luxoempassos.model.venda;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.exception.NegocioException;
import br.com.luxoempassos.model.produto.Sandalia;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String protocolo;

    @ManyToOne(fetch = FetchType.LAZY) // Relacionamento com Cliente (Dono do pedido)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.ABERTO;

    private LocalDateTime dataHora;
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    protected Pedido() {}

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.protocolo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.status = StatusPedido.ABERTO;
        this.dataHora = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;
    }

    public void adicionarItem(Sandalia sandalia, int quantidade) {
        // Regra de Negócio: Baixa estoque antes de confirmar o item
        sandalia.baixarEstoque(quantidade);

        ItemPedido novoItem = new ItemPedido(sandalia, quantidade, sandalia.getPrecoVenda());

        // VÍNCULO BI-DIRECIONAL: Crucial para o JPA persistir a relação
        novoItem.setPedido(this);

        this.itens.add(novoItem);
        recalcularTotal();
    }

    private void recalcularTotal() {
        BigDecimal totalBruto = itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // O perfil de fidelidade aplica o desconto sobre a soma dos itens
        this.valorTotal = cliente.perfil().aplicarDesconto(totalBruto);
    }

    public void finalizarPedido() {
        if (this.status != StatusPedido.ABERTO) {
            throw new NegocioException("Apenas pedidos ABERTOS podem ser finalizados. Status atual: " + this.status);
        }

        if (itens.isEmpty()) {
            throw new NegocioException("Não é possível finalizar um pedido sem itens.");
        }

        this.status = StatusPedido.FINALIZADO;
        this.cliente.registrarCompra(this.getValorTotal());

        System.out.println("LOG: Pedido finalizado e gasto registrado para " + cliente.getNome());
    }

    public void cancelarPedido() {
        if (this.status == StatusPedido.CANCELADO) {
            throw new NegocioException("O pedido já está cancelado.");
        }

        if (this.status == StatusPedido.FINALIZADO) {
            // Lógica extra: Se já foi finalizado, precisamos estornar o gasto do cliente
            this.cliente.estornarGasto(this.getValorTotal());
            // E devolver ao estoque (implementaremos na lógica de estoque)
        }

        this.itens.forEach(item -> {
            item.getSandalia().abastecerEstoque(item.getQuantidade());
        });

        this.status = StatusPedido.CANCELADO;
        System.out.println("Pedido " + protocolo + " cancelado e estoque devolvido.");
    }

    public String getProtocolo() { return protocolo; }
    public StatusPedido getStatus() {
        return status;
    }
    public LocalDateTime getDataHora() { return dataHora; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return Collections.unmodifiableList(itens); }
}
