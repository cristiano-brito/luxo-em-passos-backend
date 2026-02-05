package br.com.luxoempassos.service;

import br.com.luxoempassos.dto.ClienteDTO;
import br.com.luxoempassos.exception.NegocioException;
import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.Endereco;
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
    public ClienteDTO salvar(ClienteDTO dto) {
        // 1. Validação de Negócio (Pré-checa antes de tentar inserir)
        String currentTenant = br.com.luxoempassos.config.TenantContext.getTenantId();

        String emailTratado = (dto.email() != null && dto.email().isBlank()) ? null : dto.email();
        String telefoneTratado = (dto.telefone() != null && dto.telefone().isBlank()) ? null : dto.telefone();

        if (clienteRepository.existsByCpfAndTenantId(dto.cpf(), currentTenant)) {
            throw new NegocioException("Este CPF já está cadastrado.");
        }

        if (emailTratado != null) {
            if (clienteRepository.existsByEmailAndTenantId(emailTratado, currentTenant)) {
                throw new NegocioException("Este e-mail já está sendo utilizado por outra cliente.");
            }
        }

        Endereco endereco = null;
        if (dto.endereco() != null) {
            endereco = Endereco.criar(
                    dto.endereco().logradouro(),
                    dto.endereco().numero(),
                    dto.endereco().bairro(),
                    dto.endereco().cidade(),
                    dto.endereco().cep(),
                    dto.endereco().uf()
            );
        }

        Cliente cliente = new Cliente(
                dto.nome(),
                dto.cpf(),
                endereco,
                telefoneTratado,
                emailTratado,
                java.time.LocalDate.now()
        );

        Cliente salvo = clienteRepository.save(cliente);

        return ClienteDTO.paraDTO(salvo);
    }

    @Override
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteDTO::paraDTO)
                .toList();
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
