package br.com.luxoempassos.service;

import br.com.luxoempassos.exception.NegocioException;
import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.venda.Pedido;
import br.com.luxoempassos.model.venda.StatusPedido;
import br.com.luxoempassos.repository.ClienteRepository;
import br.com.luxoempassos.repository.PedidoRepository;
import br.com.luxoempassos.util.MoedaUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoServiceImpl implements IPedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public void registrarNovoPedido(Pedido pedido) {

        Cliente cliente = pedido.getCliente();

        cliente.registrarCompra(pedido.getValorTotal());

        clienteRepository.save(cliente);

        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void finalizarVenda(String protocolo) {
        Pedido pedido = buscarOuFalhar(protocolo);
        pedido.finalizarPedido();
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void cancelarVenda(String protocolo) {
        Pedido pedido = buscarOuFalhar(protocolo);
        pedido.cancelarPedido();
        pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> listarPedidosPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    @Override
    public void exibirRelatorioFaturamento() {
        BigDecimal total = pedidoRepository.calcularFaturamentoTotal();

        BigDecimal totalExibicao = (total != null) ? total : BigDecimal.ZERO;

        System.out.println("Faturamento Total: " + MoedaUtil.formatar(totalExibicao));
    }

    @Override
    public BigDecimal calcularFaturamentoTotal() {
        return pedidoRepository.calcularFaturamentoTotal();
    }

    private Pedido buscarOuFalhar(String protocolo) {
        return pedidoRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new NegocioException("Pedido não encontrado: " + protocolo));
    }
}
