package br.com.luxoempassos.controller;

import br.com.luxoempassos.model.venda.Pedido;
import br.com.luxoempassos.model.venda.StatusPedido;
import br.com.luxoempassos.service.IPedidoService;
import br.com.luxoempassos.util.MoedaUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final IPedidoService service;

    public PedidoController(IPedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody Pedido pedido) {
        service.registrarNovoPedido(pedido);
        return ResponseEntity.ok("Pedido criado! Protocolo: " + pedido.getProtocolo());
    }

    @PutMapping("/{protocolo}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable String protocolo) {
        service.finalizarVenda(protocolo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/faturamento")
    public String obterFaturamentoFormatado() {
        BigDecimal total = service.calcularFaturamentoTotal();
        return "Faturamento Total: " + MoedaUtil.formatar(total);
    }

    @GetMapping
    public List<Pedido> listar(@RequestParam(name = "status", required = false) StatusPedido status) {
        if (status != null) {
            return service.listarPedidosPorStatus(status);
        }
        return List.of();
    }
}
