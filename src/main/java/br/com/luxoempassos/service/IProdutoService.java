package br.com.luxoempassos.service;

import br.com.luxoempassos.model.produto.Sandalia;

import java.util.List;
import java.util.Optional;

public interface IProdutoService {
    void salvar(Sandalia sandalia);
    List<Sandalia> listarTodos();
    Optional<Sandalia> buscarPorSku(String sku);
    void atualizarEstoque(String sku, int quantidade);
    boolean existe(String sku);
}
