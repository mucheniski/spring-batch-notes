package com.springbatch.faturacartaocredito.dominio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    private Integer id;
    private CartaoCredito cartaoCredito;
    private String descricao;
    private Double valor;
    private Date data;

}
