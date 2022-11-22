package com.springbatch.migracaoarquivobanco.domain;

import lombok.Data;

@Data
public class DadosBancarios {

    private Integer id;
    private Integer pessoaId;
    private Integer agenica;
    private Integer conta;
    private Integer banco;

}
