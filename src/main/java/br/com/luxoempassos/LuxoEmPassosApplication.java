package br.com.luxoempassos;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.Endereco;
import br.com.luxoempassos.model.produto.Categoria;
import br.com.luxoempassos.model.produto.Sandalia;
import br.com.luxoempassos.service.IClienteService;
import br.com.luxoempassos.service.IProdutoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class LuxoEmPassosApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuxoEmPassosApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(MenuConsole menu,
                                 IClienteService clienteService,
                                 IProdutoService produtoService) { // Injetando o Service
        return args -> {
            // Carga inicial para testes no H2
            if (clienteService.listarTodos().isEmpty()) {
                System.out.println("🌱 Populando banco de dados inicial...");

                // 1. Cliente Inicial
                Endereco end = Endereco.criar("Av. Brasil", "10", "Centro", "Rio de Janeiro", "20000-000");
                clienteService.salvar(Cliente.novo("Sophia Loren", end, "21999998888", "sophia@luxo.com", LocalDate.now()));

                // 2. Produtos Iniciais (Agora com o campo 'tamanho' que adicionamos)
                // SKU, Modelo, Tamanho, Preço, Estoque
                produtoService.salvar(new Sandalia("SND-01", "Scarpin Luxo", 37, Categoria.SCARPIN, new BigDecimal("500.00"), 10));
                produtoService.salvar(new Sandalia("SND-02", "Sandália Festa", 35, Categoria.SALTO_ALTO, new BigDecimal("350.00"), 5));

                System.out.println("✅ Dados carregados com sucesso.");
            }

            // Inicia o loop do menu no console
            menu.exibir();
        };
    }
}
