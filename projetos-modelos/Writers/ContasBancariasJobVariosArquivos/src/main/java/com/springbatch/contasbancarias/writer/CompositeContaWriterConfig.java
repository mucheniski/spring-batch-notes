package com.springbatch.contasbancarias.writer;

import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompositeContaWriterConfig {

    /*
    O escritor composto me permite deletar o a escrita para um ou mais writers, na ordem em que eu informar aqui
    isso é útil para gerar arquivos de saída após escrita em banco por exemplo.
    Como os writers já são beans registrados pelo spring, posso injetá-los aqui.
    * Como existem dois beans de FlatFileItemWriter, preciso informar com um
    * Qualificador, qual bean uso aqui
     */
    @Bean
    public CompositeItemWriter<Conta> escritorCompostoBancoEArquivo(
            JdbcBatchItemWriter<Conta> jdbcBatchItemWriter,
            @Qualifier("fileContaWriter") FlatFileItemWriter<Conta> fileContaWriter
    ) {
        return new CompositeItemWriterBuilder<Conta>()
                .delegates(jdbcBatchItemWriter, fileContaWriter)
                .build();
    }

}
