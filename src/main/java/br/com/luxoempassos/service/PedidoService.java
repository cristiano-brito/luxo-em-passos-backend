package br.com.luxoempassos.service;

import br.com.luxoempassos.exception.NegocioException;
import br.com.luxoempassos.model.venda.Pedido;
import br.com.luxoempassos.model.venda.StatusPedido;
import br.com.luxoempassos.repository.IPedidoRepository;
import br.com.luxoempassos.util.MoedaUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService implements IPedidoService {
    private final IPedidoRepository repository;

    public PedidoService(IPedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrarNovoPedido(Pedido pedido) {
        repository.salvar(pedido);
    }

    @Override
    public void finalizarVenda(String protocolo) {
        Pedido pedido = buscarOuFalhar(protocolo);
        pedido.finalizarPedido();
        repository.salvar(pedido);
    }

    @Override
    public void cancelarVenda(String protocolo) {
        Pedido pedido = buscarOuFalhar(protocolo);
        pedido.cancelarPedido();
        repository.salvar(pedido);
    }

    @Override
    public List<Pedido> listarPedidosPorStatus(StatusPedido status) {
        return repository.listarPorStatus(status);
    }

    @Override
    public void exibirRelatorioFaturamento() {
        System.out.println("Faturamento Total: " +
                MoedaUtil.formatar(repository.calcularFaturamentoTotal()));
    }

    @Override
    public BigDecimal calcularFaturamentoTotal() {
        return repository.calcularFaturamentoTotal();
    }

    private Pedido buscarOuFalhar(String protocolo) {
        return repository.buscarPorProtocolo(protocolo)
                .orElseThrow(() -> new NegocioException("Pedido não encontrado: " + protocolo));
    }
}
