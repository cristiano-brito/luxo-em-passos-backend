package br.com.luxoempassos.model.cliente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {
    private final IdCliente id;
    private final String nome;
    private final Endereco endereco;
    private final String telefone;
    private final String email;
    private final LocalDate dataCadastro;
    private final List<Gasto> historicoGastos = new ArrayList<>();

    public Cliente(IdCliente id, String nome, Endereco endereco, String telefone, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public static Cliente novo(String nome, Endereco endereco, String telefone, String email, LocalDate dataCadastro) {
        return new Cliente(IdCliente.gerar(), nome, endereco, telefone, email, dataCadastro);
    }

    public PerfilFidelidade perfil() {
        BigDecimal gastoNoMesAtual = calcularGastoMesAtual();

        // Regra Black: Gasto Mensal > 5.000 (Exemplo de valor mensal)
        if (gastoNoMesAtual.compareTo(new BigDecimal("5000.00")) >= 0) {
            return new Black();
        }

        // Regra Gold: Gasto Mensal > 1.500
        if (gastoNoMesAtual.compareTo(new BigDecimal("1500.00")) >= 0) {
            return new Gold();
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

    public void registrarCompra(BigDecimal valor) {
        this.historicoGastos.add(new Gasto(valor, LocalDate.now()));
    }

    public void estornarGasto(BigDecimal valor) {
        historicoGastos.removeIf(g -> g.valor().equals(valor) && g.data().equals(LocalDate.now()));
    }

    public IdCliente getId() { return this.id; }
    public String getNome() { return nome; }
    public Endereco getEndereco() { return endereco; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public List<Gasto> getHistoricoGastos() { return Collections.unmodifiableList(historicoGastos); }
}
