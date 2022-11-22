package com.springbatch.migracaoarquivobanco.reader;

import com.springbatch.migracaoarquivobanco.domain.DadosBancarios;
import com.springbatch.migracaoarquivobanco.domain.Pessoa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Configuration
public class ArquivoDadosBancariosReaderConfig {

    FileSystemResource arquivoDadosBancarios = new FileSystemResource("files/dados_bancarios.csv");

    @Bean
    public FlatFileItemReader<DadosBancarios> arquivoDadosBancarios() throws ParseException {
        return new FlatFileItemReaderBuilder<DadosBancarios>()
                .name("arquivoPessoaReader")
                .resource(arquivoDadosBancarios)
                .delimited()
                .names("pessoaId", "agencia", "conta", "banco", "id")
                .addComment("--") // Ignorar as linhas que tenham comentarios com --
                .targetType(DadosBancarios.class) // Como não tem data que é um objeto complexo, pode ser usado o targetType
                .build();
    }



}
