package com.springbatch.faturacartaocredito.dominio;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FaturaCartaoCredito {

    private Cliente cliente;
    private CartaoCredito cartaoCredito;
    private List<Transacao> transacoes = new ArrayList<>();

}
