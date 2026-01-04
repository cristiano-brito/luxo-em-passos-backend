package br.com.luxoempassos.exception;

public class EstoqueInsuficienteException extends NegocioException {

    public EstoqueInsuficienteException(String modelo, int solicitado, int disponivel) {
        super(String.format(
                "Luxo em Passos - Estoque Insuficiente: O item '%s' possui apenas %d unidade(s) disponível(is), mas você tentou retirar %d.",
                modelo, disponivel, solicitado
        ));
    }
}
