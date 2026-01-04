package br.com.luxoempassos.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoedaUtil {
    // Definindo o padrão brasileiro (R$ 1.234,56)
    private static final NumberFormat FORMATADOR = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static String formatar(BigDecimal valor) {
        if (valor == null) return "R$ 0,00";
        return FORMATADOR.format(valor);
    }
}
