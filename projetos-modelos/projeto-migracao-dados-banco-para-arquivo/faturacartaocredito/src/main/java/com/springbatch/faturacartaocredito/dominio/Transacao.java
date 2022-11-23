package com.springbatch.faturacartaocredito.dominio;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Transacao {

    private Integer id;
    private CartaoCredito cartaoCredito;
    private String descricao;
    private Double valor;
    private Date data;

}
