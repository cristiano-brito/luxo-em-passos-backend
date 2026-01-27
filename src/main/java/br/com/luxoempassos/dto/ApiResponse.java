package br.com.luxoempassos.dto;

import java.time.LocalDateTime;

public record ApiResponse<T>(
    boolean sucesso,
    String mensagem,
    T dados,
    String timestamp,
    long tempoProcessamentoMs
) {
    public static <T> ApiResponse<T> ok(T dados, String mensagem, long tempoInicio) {
        return new ApiResponse<>(true, mensagem, dados, LocalDateTime.now().toString(), System.currentTimeMillis() - tempoInicio);
    }

    public static <T> ApiResponse<T> erro(String mensagem, long tempoInicio) {
        return new ApiResponse<>(false, mensagem, null, LocalDateTime.now().toString(), System.currentTimeMillis() - tempoInicio);
    }
}
