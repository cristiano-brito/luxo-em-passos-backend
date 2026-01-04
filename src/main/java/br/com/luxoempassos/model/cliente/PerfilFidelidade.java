package br.com.luxoempassos.model.cliente;

import java.math.BigDecimal;

public sealed interface PerfilFidelidade permits Standard, Gold, Black {
    BigDecimal aplicarDesconto(BigDecimal valor);
    Boolean possuiDireitoAFreteGratis();
    String descricao();
}
