package br.com.luxoempassos.dto;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.Endereco;

import java.math.BigDecimal;

public record ClienteDTO(
    Long id,
    String nome,
    String email,
    String telefone,
    String perfil,
    BigDecimal gastoTotalAcumulado,
    Endereco endereco
) {
    // Factory method para converter a Entidade em DTO
    public static ClienteDTO paraDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.perfil().descricao(),
                cliente.getGastoTotalAcumulado(),
                cliente.getEndereco()
        );
    }
}