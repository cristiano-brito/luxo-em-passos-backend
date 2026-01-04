package br.com.luxoempassos.model.cliente;

public record Endereco(
    String logradouro,
    String numero,
    String bairro,
    String cidade,
    String cep
) {

    public Endereco {
        if (logradouro == null || logradouro.isBlank()) {
            throw new IllegalArgumentException("Logradouro deve ser informado.");
        }

        if (cep == null || !cep.matches("\\d{5}-?\\d{3}")) {
            throw new IllegalArgumentException("CEP inválido para padrão de luxo.");
        }

        if (cidade == null || cidade.isBlank()) {
            throw new IllegalArgumentException("Cidade deve ser informada.");
        }

        // Normaliza o CEP (remove hífen se existir)
        cep = cep.replace("-", "");
    }

    public static Endereco criar(String logradouro, String numero, String bairro,
                                 String cidade, String cep) {
        return new Endereco(logradouro, numero, bairro, cidade, cep);
    }

    public String resumoParaEtiqueta() {
        return "%s, %s - %s".formatted(logradouro, numero, cidade);
    }

    public String cepFormatado() {
        if (cep.length() == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        return cep;
    }
}
