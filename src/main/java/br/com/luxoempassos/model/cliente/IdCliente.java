package br.com.luxoempassos.model.cliente;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.UUID;

public record IdCliente(UUID id) {
    public static IdCliente gerar() {
        return new IdCliente(UUID.randomUUID());
    }

    @JsonValue
    @Override
    public String toString() {
        return id.toString();
    }
}
