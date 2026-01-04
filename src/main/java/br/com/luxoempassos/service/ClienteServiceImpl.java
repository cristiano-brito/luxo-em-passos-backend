package br.com.luxoempassos.service;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;

    // Injeção de dependência via construtor
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    @Transactional
    public void registrarCompra(Long clienteId, BigDecimal valor) {
        // 1. Busca o cliente no banco
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + clienteId));

        // 2. Executa a regra de negócio que você criou na classe Cliente
        cliente.registrarCompra(valor);

        // 3. Salva a alteração (O Hibernate detecta a mudança e atualiza a tabela de gastos)
        clienteRepository.save(cliente);
    }
}
