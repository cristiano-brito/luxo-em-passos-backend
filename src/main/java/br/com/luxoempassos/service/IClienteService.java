package br.com.luxoempassos.service;

import br.com.luxoempassos.model.cliente.Cliente;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IClienteService {
    Cliente salvar(Cliente cliente);
    List<Cliente> listarTodos();
    Optional<Cliente> buscarPorId(Long id);
    void registrarCompra(Long clienteId, BigDecimal valor);
}
