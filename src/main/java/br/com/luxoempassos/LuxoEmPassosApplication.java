package br.com.luxoempassos;

import br.com.luxoempassos.config.TenantContext;
import br.com.luxoempassos.dto.ClienteDTO;
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
                                 IProdutoService produtoService) {
        return args -> {
            try {
                // 1. Definimos um ID de "loja" para os dados do sistema
                TenantContext.setTenantId("LOJA-SISTEMA");

                if (clienteService.listarTodos().isEmpty()) {
                    System.out.println("🌱 Populando banco de dados inicial...");

                    Endereco end = Endereco.criar("Av. Brasil", "10", "Centro", "Rio de Janeiro", "20000-000", "RJ");
                    clienteService.salvar(ClienteDTO.paraDTO(
                            Cliente.novo("Sophia Loren", "15062656001", end, "21999998888", "sophia@luxo.com", LocalDate.now())
                    ));

                    produtoService.salvar(new Sandalia("SND-01", "Scarpin Luxo", 37, Categoria.SCARPIN, new BigDecimal("500.00"), 10));
                    produtoService.salvar(new Sandalia("SND-02", "Sandália Festa", 35, Categoria.SALTO_ALTO, new BigDecimal("350.00"), 5));

                    System.out.println("✅ Dados carregados com sucesso.");
                }
            } finally {
                // 2. IMPORTANTE: Limpamos para que o MenuConsole não fique "preso" nesse tenant
                TenantContext.clear();
            }

            // 3. Inicia o loop do menu no console
            menu.exibir();
        };
    }
}
