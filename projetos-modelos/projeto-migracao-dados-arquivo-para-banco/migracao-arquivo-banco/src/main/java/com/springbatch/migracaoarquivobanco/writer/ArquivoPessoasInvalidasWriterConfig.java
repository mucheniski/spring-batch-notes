package com.springbatch.migracaoarquivobanco.writer;

import com.springbatch.migracaoarquivobanco.domain.Pessoa;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ArquivoPessoasInvalidasWriterConfig {

    FileSystemResource arquivoPessoasInvalidas = new FileSystemResource("files/pessoas-invalidas.csv");

    @Bean
    public FlatFileItemWriter<Pessoa> arquivoPessoasInvalidasWriter() {
        return new FlatFileItemWriterBuilder<Pessoa>()
                .name("arquivoPessoasInvalidasWriter")
                .resource(arquivoPessoasInvalidas)
                .delimited()
                .delimiter(";")
                .names("id", "nome")
                .build();
    }

}
