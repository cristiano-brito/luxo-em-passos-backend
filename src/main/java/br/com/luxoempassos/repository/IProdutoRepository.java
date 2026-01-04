package br.com.luxoempassos.repository;

import br.com.luxoempassos.model.produto.Sandalia;

import java.util.List;
import java.util.Optional;

public interface IProdutoRepository {
    void salvar(Sandalia sandalia);
    Optional<Sandalia> buscarPorCodigo(String codigo);
    List<Sandalia> listarDisponiveis();
}
