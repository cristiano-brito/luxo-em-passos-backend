package br.com.luxoempassos.repository;

import br.com.luxoempassos.model.venda.Pedido;
import br.com.luxoempassos.model.venda.StatusPedido;
import org.springframework.stereotype.Repository;;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PedidoRepository implements IPedidoRepository {
    private final Map<String, Pedido> database = new HashMap<>();

    @Override
    public void salvar(Pedido pedido) {
        database.put(pedido.getProtocolo().toUpperCase(), pedido);
    }

    @Override
    public Optional<Pedido> buscarPorProtocolo(String protocolo) {
        return Optional.ofNullable(database.get(protocolo.toUpperCase()));
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(database.values());
    }

    @Override
    public List<Pedido> listarPorStatus(StatusPedido status) {
        return database.values().stream()
                .filter(p -> p.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calcularFaturamentoTotal() {
        return database.values().stream()
                .filter(p -> p.getStatus() == StatusPedido.FINALIZADO)
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
