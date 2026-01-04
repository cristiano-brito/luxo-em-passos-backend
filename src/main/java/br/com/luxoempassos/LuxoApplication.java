package br.com.luxoempassos;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.Endereco;
import br.com.luxoempassos.model.produto.Categoria;
import br.com.luxoempassos.model.produto.Sandalia;
import br.com.luxoempassos.repository.*;
import br.com.luxoempassos.service.IPedidoService;
import br.com.luxoempassos.service.PedidoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class LuxoApplication {
    public static void main(String[] args) {
        // 1. Iniciamos o Spring e guardamos o "contexto" em uma variável
        ConfigurableApplicationContext context = SpringApplication.run(LuxoApplication.class, args);

        // 2. Pegamos o MenuConsole de DENTRO do Spring (para ele ter os Repositories)
        MenuConsole menu = context.getBean(MenuConsole.class);

        // 3. Só agora rodamos o exibir
        menu.exibir();
    }
}
