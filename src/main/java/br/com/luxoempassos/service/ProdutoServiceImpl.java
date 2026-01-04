package br.com.luxoempassos.service;

import br.com.luxoempassos.model.produto.Sandalia;
import br.com.luxoempassos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements IProdutoService {
    private final ProdutoRepository repository;

    // Injeção de dependência pelo construtor
    public ProdutoServiceImpl(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void salvar(Sandalia sandalia) {
        repository.save(sandalia);
    }

    @Override
    public List<Sandalia> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Sandalia> buscarPorSku(String sku) {
        return repository.findById(sku);
    }

    @Override
    @Transactional
    public void atualizarEstoque(String sku, int quantidade) {
        Sandalia sandalia = repository.findById(sku)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + sku));

        sandalia.abastecerEstoque(quantidade);
        repository.save(sandalia); // O save no final garante o update no banco
    }

    @Override
    public boolean existe(String sku) {
        return repository.existsById(sku);
    }
}
