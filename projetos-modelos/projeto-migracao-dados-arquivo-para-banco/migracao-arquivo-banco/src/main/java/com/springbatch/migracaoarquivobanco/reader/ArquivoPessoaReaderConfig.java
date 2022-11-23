package com.springbatch.migracaoarquivobanco.reader;

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
public class ArquivoPessoaReaderConfig {

    FileSystemResource arquivoPessoas = new FileSystemResource("files/pessoas.csv");

    @Bean
    public FlatFileItemReader<Pessoa> arquivoPessoaReader() throws ParseException {
        return new FlatFileItemReaderBuilder<Pessoa>()
                .name("arquivoPessoaReader")
                .resource(arquivoPessoas)
                .delimited()
                .names("nome", "email", "dataNascimento", "idade", "id")
                .addComment("--") // Ignorar as linhas que tenham comentarios com --
                .fieldSetMapper(mapeadorCamposPessoa())
                .build();
    }

    private FieldSetMapper<Pessoa> mapeadorCamposPessoa() throws ParseException{
        return new FieldSetMapper<Pessoa>() {
            @Override
            public Pessoa mapFieldSet(FieldSet fieldSet) throws BindException {
                return Pessoa.builder()
                        .nome(fieldSet.readString("nome"))
                        .email(fieldSet.readString("email"))
                        .dataNascimento(fieldSet.readString("dataNascimento"))
                        .idade(fieldSet.readInt("idade"))
                        .id(fieldSet.readInt("id"))
                        .build();
            }
        };
    }

}
