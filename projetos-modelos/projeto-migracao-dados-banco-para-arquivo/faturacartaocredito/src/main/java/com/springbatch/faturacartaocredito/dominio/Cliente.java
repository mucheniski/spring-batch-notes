package com.springbatch.faturacartaocredito.dominio;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cliente {

    private Integer id;
    private String nome;
    private String endereco;

}
