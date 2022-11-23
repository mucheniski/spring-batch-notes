package com.springbatch.faturacartaocredito.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaturaCartaoCredito {

    private Cliente cliente;
    private CartaoCredito cartaoCredito;
    private List<Transacao> transacoes = new ArrayList<>();

    public Double getTotal() {
        return transacoes
                .stream()
                .mapToDouble(Transacao::getValor)
                .reduce(0.0, Double::sum);
    }
}
