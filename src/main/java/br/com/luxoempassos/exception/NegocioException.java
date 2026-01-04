package br.com.luxoempassos.exception;

public class NegocioException extends RuntimeException {
    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
