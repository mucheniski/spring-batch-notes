package com.springbatch.migracaoarquivobanco.domain;

import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.util.Date;

@Data
@Builder
public class Pessoa {

    private Integer id;
    private String nome;
    private String email;
    private String dataNascimento;
    private Integer idade;

    public boolean ehValida() {
        return !Strings.isBlank(nome) && !Strings.isBlank(email) && dataNascimento != null;
    }
}
