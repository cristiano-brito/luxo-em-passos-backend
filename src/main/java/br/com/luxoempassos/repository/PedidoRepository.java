package br.com.luxoempassos.repository;

import br.com.luxoempassos.model.produto.Sandalia;
import br.com.luxoempassos.model.venda.Pedido;
import br.com.luxoempassos.model.venda.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByProtocolo(String protocolo);

    List<Pedido> findByStatus(StatusPedido status);

    @Query("SELECT COALESCE(SUM(p.valorTotal), 0) FROM Pedido p WHERE p.status = 'FINALIZADO'")
    BigDecimal calcularFaturamentoTotal();
}
