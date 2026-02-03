package br.com.luxoempassos.model.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Endereco(
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String cep,
        @Column(length = 2)
        String uf
) {

    public Endereco {
        // Validações de integridade
        if (logradouro == null || logradouro.isBlank()) throw new IllegalArgumentException("Logradouro deve ser informado.");
        if (cidade == null || cidade.isBlank()) throw new IllegalArgumentException("Cidade deve ser informada.");
        if (uf == null || uf.length() != 2) throw new IllegalArgumentException("UF deve conter exatamente 2 caracteres.");

        if (cep == null || !cep.matches("\\d{5}-?\\d{3}")) {
            throw new IllegalArgumentException("CEP inválido para padrão de luxo.");
        }
    }

    public static Endereco criar(String logradouro, String numero, String bairro,
                                 String cidade, String cep, String uf) {

        if (logradouro == null || logradouro.isBlank() || cep == null || uf == null) {
            return null;
        }

        return new Endereco(logradouro, numero, bairro, cidade, cep.replaceAll("\\D", ""), uf.toUpperCase().trim());
    }

    public String resumoParaEtiqueta() {
        return "%s, %s - %s/%s".formatted(logradouro, numero, cidade, uf);
    }
}