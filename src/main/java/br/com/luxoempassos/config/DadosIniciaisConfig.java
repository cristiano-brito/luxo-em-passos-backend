package br.com.luxoempassos.config;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.Endereco;
import br.com.luxoempassos.model.produto.Categoria;
import br.com.luxoempassos.model.produto.Sandalia;
import br.com.luxoempassos.repository.IClienteRepository;
import br.com.luxoempassos.repository.IProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DadosIniciaisConfig {
    @Bean
    public CommandLineRunner carregarDados(IClienteRepository clienteRepository, IProdutoRepository produtoRepository) {
        return args -> {
            // Cliente inicial
            Endereco end = Endereco.criar("Av. Brasil", "10", "Centro", "RJ", "20000-000");
            clienteRepository.salvar(Cliente.novo("Sophia Loren", end, "219999", "sophia@luxo.com", LocalDate.now()));

            // Produtos iniciais
            produtoRepository.salvar(new Sandalia("SND-01", "Scarpin Luxo", 37, Categoria.SCARPIN, new BigDecimal("500.00"), 10));
            produtoRepository.salvar(new Sandalia("SND-02", "Sandália Festa", 35, Categoria.SALTO_ALTO, new BigDecimal("350.00"), 5));

            System.out.println("🌱 Dados iniciais carregados com sucesso pelo Spring!");
        };
    }
}
