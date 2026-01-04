package br.com.luxoempassos.repository;

import br.com.luxoempassos.model.venda.Pedido;
import br.com.luxoempassos.model.venda.StatusPedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IPedidoRepository {
    void salvar(Pedido pedido);
    Optional<Pedido> buscarPorProtocolo(String protocolo);
    List<Pedido> listarTodos();
    List<Pedido> listarPorStatus(StatusPedido status);
    BigDecimal calcularFaturamentoTotal();
}
