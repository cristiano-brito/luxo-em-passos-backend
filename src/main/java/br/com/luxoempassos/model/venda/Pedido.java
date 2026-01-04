package br.com.luxoempassos.model.venda;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.exception.NegocioException;
import br.com.luxoempassos.model.produto.Sandalia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Pedido {
    private final String protocolo;
    private final Cliente cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private StatusPedido status = StatusPedido.ABERTO;

    public Pedido(Cliente cliente) {
        this.protocolo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.cliente = cliente;
    }

    public void adicionarItem(Sandalia sandalia, int quantidade) {
        // Regra de Negócio: Baixa estoque antes de confirmar o item
        sandalia.baixarEstoque(quantidade);

        ItemPedido novoItem = new ItemPedido(sandalia, quantidade, sandalia.getPrecoVenda());
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
            item.sandalia().abastecerEstoque(item.quantidade());
        });

        if (this.status == StatusPedido.FINALIZADO) {
            this.cliente.estornarGasto(this.getValorTotal());
        }

        this.status = StatusPedido.CANCELADO;
        System.out.println("Pedido " + protocolo + " cancelado e estoque devolvido.");
    }

    public String getProtocolo() { return protocolo; }
    public StatusPedido getStatus() {
        return status;
    }
    public BigDecimal getValorTotal() { return valorTotal; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return Collections.unmodifiableList(itens); }
}
