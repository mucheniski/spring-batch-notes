package com.springbatch.faturacartaocredito.dominio;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartaoCredito {

    private Integer numero;
    private Cliente cliente;

}
