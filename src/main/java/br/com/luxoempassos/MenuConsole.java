package br.com.luxoempassos;

import br.com.luxoempassos.model.cliente.Cliente;
import br.com.luxoempassos.model.cliente.Endereco;
import br.com.luxoempassos.model.produto.Sandalia;
import br.com.luxoempassos.model.venda.Pedido;
import br.com.luxoempassos.service.IClienteService;
import br.com.luxoempassos.service.IPedidoService;
import br.com.luxoempassos.service.IProdutoService;
import br.com.luxoempassos.util.MoedaUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class MenuConsole {
    private final IPedidoService pedidoService;
    private final IClienteService clienteService;
    private final IProdutoService produtoService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuConsole(IPedidoService pedidoService,
                       IClienteService clienteService,
                       IProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== 👠 LUXO EM PASSOS - GESTÃO H2 ===");
            System.out.println("1. Novo Pedido (Venda)");
            System.out.println("2. Finalizar Pedido (Protocolo)");
            System.out.println("3. Cancelar Pedido (Estorno)");
            System.out.println("4. Relatório de Faturamento");
            System.out.println("5. Listar Clientes");
            System.out.println("6. Cadastrar Novo Cliente");
            System.out.println("7. Consultar Estoque de Sandálias");
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
            case 7 -> listarProdutos();
            case 0 -> System.out.println("Encerrando sistema...");
            default -> System.out.println("Opção inválida!");
        }
    }

    private void criarPedido() {
        System.out.println("\n--- NOVO PEDIDO ---");

        // 1. Seleção de Cliente
        listarClientes();
        System.out.print("Digite o ID do Cliente: ");
        Long idCliente = Long.parseLong(scanner.nextLine());
        Cliente cliente = clienteService.buscarPorId(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        // 2. Seleção de Produto
        listarProdutos();
        System.out.print("\nDigite o SKU da Sandália: ");
        String sku = scanner.nextLine();
        Sandalia sandalia = produtoService.buscarPorSku(sku)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        System.out.print("Quantidade: ");
        int qtd = Integer.parseInt(scanner.nextLine());

        // 3. Processamento do Pedido (Lógica de Negócio encapsulada na Entity)
        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(sandalia, qtd);

        // 4. Persistência (Salva Pedido, Itens e atualiza Estoque da Sandália)
        pedidoService.registrarNovoPedido(pedido);

        System.out.println("\n✅ Pedido Gerado com Sucesso!");
        System.out.println("Protocolo: " + pedido.getProtocolo());
        System.out.println("Cliente: " + cliente.getNome() + " (" + cliente.perfil().descricao() + ")");
        System.out.println("Total: " + MoedaUtil.formatar(pedido.getValorTotal()));
    }

    private void finalizarPedido() {
        System.out.print("Digite o Protocolo para FINALIZAR: ");
        String protocolo = scanner.nextLine();
        pedidoService.finalizarVenda(protocolo);
        System.out.println("💰 Venda confirmada e faturamento registrado!");
    }

    private void cancelarPedido() {
        System.out.print("Digite o Protocolo para CANCELAR: ");
        String protocolo = scanner.nextLine();
        pedidoService.cancelarVenda(protocolo);
        System.out.println("🚫 Venda cancelada e estoque devolvido.");
    }

    private void listarClientes() {
        System.out.println("\n--- CLIENTES NO BANCO ---");
        List<Cliente> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            clientes.forEach(c -> System.out.println(
                    "ID: " + c.getId() +
                            " | Nome: " + c.getNome() +
                            " | Perfil: " + c.perfil().descricao() +
                            " | Gasto Acumulado: " + MoedaUtil.formatar(c.getGastoTotalAcumulado())
            ));
        }
    }

    private void listarProdutos() {
        System.out.println("\n--- ESTOQUE DE SANDÁLIAS ---");
        List<Sandalia> produtos = produtoService.listarTodos();
        if (produtos.isEmpty()) System.out.println("Estoque vazio.");
        produtos.forEach(p -> System.out.println(
                "SKU: " + p.getSku() + " | " + p.getModelo() + " (T:" + p.getTamanho() +
                        ") | Preço: " + MoedaUtil.formatar(p.getPrecoVenda()) + " | Est: " + p.getEstoque()
        ));
    }

    private void cadastrarCliente() {
        System.out.println("\n--- NOVO CLIENTE ---");
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();

        Endereco endereco = Endereco.criar("Rua das Palmeiras", "100", "Centro", "São Paulo", "01000-000", "BA");
        Cliente novo = Cliente.novo(nome, endereco, "(11) 99999-9999", email, LocalDate.now());

        clienteService.salvar(novo);
        System.out.println("✅ Cliente " + nome + " cadastrado com ID: " + novo.getId());
    }
}
