package com.springbatch.faturacartaocredito.dominio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoCredito {

    private Integer numero;
    private Cliente cliente;

}
