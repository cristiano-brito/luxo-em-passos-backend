package br.com.luxoempassos.repository;

import br.com.luxoempassos.model.produto.Sandalia;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProdutoRepository implements IProdutoRepository {
    private final Map<String, Sandalia> tabelaProdutos = new HashMap<>();

    @Override
    public void salvar(Sandalia sandalia) {
        tabelaProdutos.put(sandalia.getSku(), sandalia);
    }

    @Override
    public Optional<Sandalia> buscarPorCodigo(String codigo) {
        return Optional.ofNullable(tabelaProdutos.get(codigo));
    }

    @Override
    public List<Sandalia> listarDisponiveis() {
        return tabelaProdutos.values().stream()
                .filter(p -> p.getEstoque() > 0)
                .toList();
    }
}
