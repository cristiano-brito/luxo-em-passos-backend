package br.com.luxoempassos.repository;

import br.com.luxoempassos.model.produto.Sandalia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Sandalia, String> {
}
