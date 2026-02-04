package br.com.luxoempassos.dto;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.Endereco;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClienteDTO(
    Long id,
    @NotBlank String nome,
    @NotBlank String cpf,
    @Email String email,
    String telefone,
    String perfil,
    BigDecimal gastoTotalAcumulado,

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate dataCadastro,

    Endereco endereco
) {
    // Factory method para converter a Entidade em DTO
    public static ClienteDTO paraDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.perfil().descricao(),
                cliente.getGastoTotalAcumulado(),
                cliente.getDataCadastro(),
                cliente.getEndereco()
        );
    }
}