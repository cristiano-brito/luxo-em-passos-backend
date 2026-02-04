package br.com.luxoempassos.repository;

import br.com.luxoempassos.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // O Spring Data JPA vai gerar o SQL:
    // SELECT COUNT(*) > 0 FROM clientes WHERE cpf = ? AND tenant_id = ?
    boolean existsByCpfAndTenantId(String cpf, String tenantId);

    boolean existsByEmailAndTenantId(String email, String tenantId);
}
