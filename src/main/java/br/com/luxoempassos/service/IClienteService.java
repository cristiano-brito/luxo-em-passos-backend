package br.com.luxoempassos.service;

import br.com.luxoempassos.dto.ClienteDTO;
import br.com.luxoempassos.model.cliente.Cliente;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IClienteService {
    ClienteDTO salvar(ClienteDTO dto);
    List<ClienteDTO> listarTodos();
    Optional<Cliente> buscarPorId(Long id);
    void registrarCompra(Long clienteId, BigDecimal valor);
}
