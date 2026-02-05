package br.com.luxoempassos.model.cliente;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "clientes", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cliente_cpf_tenant", columnNames = {"cpf", "tenant_id"}),
        @UniqueConstraint(name = "uk_cliente_email_tenant", columnNames = {"email", "tenant_id"})
})
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = String.class))
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, updatable = false)
    private String tenantId;

    @NotBlank(message = "O CPF é obrigatório")
    @Column(length = 11, nullable = false)
    private String cpf;

    @NotBlank(message = "O nome é obrigatório para clientes de luxo")
    @Column(nullable = false)
    private String nome;

    @Embedded
    private Endereco endereco;

    @Column(nullable = true)
    private String telefone;

    @Email(message = "O e-mail informado é inválido")
    @Column(nullable = true)
    private String email;

    @Column(updatable = false)
    private LocalDate dataCadastro;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cliente_gastos", joinColumns = @JoinColumn(name = "cliente_id"))
    private List<Gasto> historicoGastos = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nome, String cpf, Endereco endereco, String telefone, String email, LocalDate dataCadastro) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = (telefone != null && telefone.isBlank()) ? null : telefone;
        this.email = (email != null && email.isBlank()) ? null : email;
        this.dataCadastro = dataCadastro;
    }

    public static Cliente novo(String nome, String cpf, Endereco endereco, String telefone, String email, LocalDate dataCadastro) {
        return new Cliente(nome, cpf, endereco, telefone, email, dataCadastro);
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

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDate.now();
        this.tenantId = br.com.luxoempassos.config.TenantContext.getTenantId();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public Endereco getEndereco() { return endereco; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public List<Gasto> getHistoricoGastos() { return Collections.unmodifiableList(historicoGastos); }
    public String getTenantId() { return tenantId; }
}
