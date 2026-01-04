package br.com.luxoempassos.repository;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.IdCliente;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ClienteRepository implements IClienteRepository {
    private final Map<IdCliente, Cliente> tabelaClientes = new HashMap<>();

    @Override
    public void salvar(Cliente cliente) {
        tabelaClientes.put(cliente.getId(), cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(IdCliente id) {
        return Optional.ofNullable(tabelaClientes.get(id));
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(tabelaClientes.values());
    }
}
