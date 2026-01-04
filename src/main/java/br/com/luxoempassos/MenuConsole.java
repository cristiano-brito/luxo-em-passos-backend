package br.com.luxoempassos;

import br.com.luxoempassos.exception.NegocioException;
import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.Endereco;
import br.com.luxoempassos.model.produto.Sandalia;
import br.com.luxoempassos.model.venda.Pedido;
import br.com.luxoempassos.repository.IClienteRepository;
import br.com.luxoempassos.repository.IProdutoRepository;
import br.com.luxoempassos.service.IPedidoService;
import br.com.luxoempassos.util.MoedaUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

@Component
public class MenuConsole {
    private final IPedidoService pedidoService;
    private final IClienteRepository clienteRepo;
    private final IProdutoRepository produtoRepo;
    private final Scanner scanner = new Scanner(System.in);

    public MenuConsole(IPedidoService pedidoService,
                       IClienteRepository clienteRepo,
                       IProdutoRepository produtoRepo) {
        this.pedidoService = pedidoService;
        this.clienteRepo = clienteRepo;
        this.produtoRepo = produtoRepo;
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== 👠 LUXO EM PASSOS - GESTÃO ===");
            System.out.println("1. Novo Pedido");
            System.out.println("2. Finalizar Pedido (Protocolo)");
            System.out.println("3. Cancelar Pedido");
            System.out.println("4. Relatório de Faturamento");
            System.out.println("5. Listar Clientes");
            System.out.println("6. Cadastrar Cliente");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcao(opcao);
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> criarPedido();
            case 2 -> finalizarPedido();
            case 3 -> cancelarPedido();
            case 4 -> pedidoService.exibirRelatorioFaturamento();
            case 5 -> listarClientes();
            case 6 -> cadastrarCliente();
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida!");
        }
    }

    private void criarPedido() {
        System.out.println("\n--- NOVO PEDIDO ---");
        listarClientes();
        System.out.print("Digite o UUID do Cliente: ");
        String uuidStr = scanner.nextLine();

        Cliente cliente = clienteRepo.listarTodos().stream()
                .filter(c -> c.getId().id().toString().equals(uuidStr))
                .findFirst()
                .orElseThrow(() -> new NegocioException("Cliente não encontrado."));

        System.out.println("\n--- PRODUTOS DISPONÍVEIS ---");
        produtoRepo.listarDisponiveis().forEach(p ->
                System.out.println(p.getSku() + " | " + p.getModelo() + " | Est: " + p.getEstoque())
        );

        System.out.print("Código (SKU) do Produto: ");
        String sku = scanner.nextLine();
        Sandalia sandalia = produtoRepo.buscarPorCodigo(sku)
                .orElseThrow(() -> new NegocioException("Produto não localizado."));

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(sandalia, 1);
        pedidoService.registrarNovoPedido(pedido);

        System.out.println("✅ Pedido Aberto! Protocolo: " + pedido.getProtocolo());
        System.out.println("Total: " + MoedaUtil.formatar(pedido.getValorTotal()));
    }

    private void cadastrarCliente() {
        System.out.println("\n--- CADASTRO ---");
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("E-mail: "); String email = scanner.nextLine();

        Endereco end = Endereco.criar("Rua Exemplo", "123", "Bairro", "Cidade", "00000");
        Cliente novo = Cliente.novo(nome, end, "9999", email, LocalDate.now());

        clienteRepo.salvar(novo);
        System.out.println("✅ Cliente salvo com ID: " + novo.getId());
    }

    private void listarClientes() {
        System.out.println("\n--- CLIENTES ---");
        clienteRepo.listarTodos().forEach(c ->
                System.out.println(c.getId().id() + " | " + c.getNome())
        );
    }

    private void finalizarPedido() {
        System.out.print("Protocolo: ");
        pedidoService.finalizarVenda(scanner.nextLine());
        System.out.println("💰 Sucesso!");
    }

    private void cancelarPedido() {
        System.out.print("Protocolo: ");
        pedidoService.cancelarVenda(scanner.nextLine());
        System.out.println("🚫 Cancelado.");
    }
}
