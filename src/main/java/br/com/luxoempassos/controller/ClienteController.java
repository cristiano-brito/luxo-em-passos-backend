package br.com.luxoempassos.controller;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.repository.IClienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final IClienteRepository repository;

    public ClienteController(IClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return repository.listarTodos();
    }

    @PostMapping
    public void cadastrar(@RequestBody Cliente cliente) {
        repository.salvar(cliente);
    }
}
