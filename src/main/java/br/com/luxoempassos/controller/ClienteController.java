package br.com.luxoempassos.controller;

import br.com.luxoempassos.dto.ApiResponse;
import br.com.luxoempassos.exception.NegocioException;
import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cliente>>> listarTodos() {
        long inicio = System.currentTimeMillis();

        List<Cliente> clientes = clienteService.listarTodos();

        ApiResponse<List<Cliente>> resposta = ApiResponse.ok(
                clientes,
                "Lista de clientes recuperada com sucesso!",
                inicio
        );

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> cadastrar(@Valid @RequestBody Cliente cliente) {
        long inicio = System.currentTimeMillis();

        Cliente salvo = clienteService.salvar(cliente);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(salvo, "Cliente cadastrado com sucesso!", inicio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
