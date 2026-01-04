package br.com.luxoempassos.service;

import br.com.luxoempassos.model.venda.Pedido;
import br.com.luxoempassos.model.venda.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public interface IPedidoService {
    void registrarNovoPedido(Pedido pedido);
    void finalizarVenda(String protocolo);
    void cancelarVenda(String protocolo);
    List<Pedido> listarPedidosPorStatus(StatusPedido status);
    void exibirRelatorioFaturamento();
    BigDecimal calcularFaturamentoTotal();
}
