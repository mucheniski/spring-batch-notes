package com.springbatch.migracaoarquivobanco.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Pessoa {

    private Integer id;
    private String nome;
    private String email;
    private String dataNascimento;
    private Integer idade;

}
