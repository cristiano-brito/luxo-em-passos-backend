package br.com.luxoempassos.model.cliente;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Embedded
    private Endereco endereco;

    private String telefone;
    private String email;
    private LocalDate dataCadastro;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cliente_gastos", joinColumns = @JoinColumn(name = "cliente_id"))
    private List<Gasto> historicoGastos = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nome, Endereco endereco, String telefone, String email, LocalDate dataCadastro) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public static Cliente novo(String nome, Endereco endereco, String telefone, String email, LocalDate dataCadastro) {
        return new Cliente(nome, endereco, telefone, email, dataCadastro);
    }

    public PerfilFidelidade perfil() {
        BigDecimal gastoNoMesAtual = calcularGastoMesAtual();

        // Regra Black: Gasto Mensal > 5.000 (Exemplo de valor mensal)
        if (gastoNoMesAtual.compareTo(new BigDecimal("3000.00")) >= 0) {
            return new Black(); // Ganha 10% de desconto
        }

        // Regra Gold: Gasto Mensal > 1.500
        if (gastoNoMesAtual.compareTo(new BigDecimal("1500.00")) >= 0) {
            return new Gold(); // Ganha 5% de desconto
        }

        return new Standard();
    }

    private BigDecimal calcularGastoMesAtual() {
        LocalDate hoje = LocalDate.now();
        return historicoGastos.stream()
                .filter(g -> g.data().getMonth() == hoje.getMonth() &&
                        g.data().getYear() == hoje.getYear())
                .map(Gasto::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getGastoTotalAcumulado() {
        return historicoGastos.stream()
                .map(Gasto::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sem filtro de data!
    }

    public void registrarCompra(BigDecimal valor) {
        this.historicoGastos.add(new Gasto(valor, LocalDate.now()));
    }

    public void estornarGasto(BigDecimal valor) {
        historicoGastos.removeIf(g -> g.valor().equals(valor) && g.data().equals(LocalDate.now()));
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Endereco getEndereco() { return endereco; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public List<Gasto> getHistoricoGastos() { return Collections.unmodifiableList(historicoGastos); }
}
