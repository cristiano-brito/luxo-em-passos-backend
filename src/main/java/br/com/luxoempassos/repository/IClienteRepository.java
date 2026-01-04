package br.com.luxoempassos.repository;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.IdCliente;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    void salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(IdCliente id);
    List<Cliente> listarTodos();
}
