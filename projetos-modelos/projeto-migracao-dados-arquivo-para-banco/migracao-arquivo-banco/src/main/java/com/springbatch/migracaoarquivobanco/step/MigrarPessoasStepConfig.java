package com.springbatch.migracaoarquivobanco.step;

import com.springbatch.migracaoarquivobanco.domain.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MigrarPessoasStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /*
     * org.springframework.batch.item.WriterNotOpenException: Writer must be open before it can be written to
     * Para evitar esse erro, preciso que sejam declarados os streams do ClassifierCompositeItemWriter pois precisam ser informados
     * a abertura e fechamento do componente, abaixo do writer com .stream() assim o spring gerencia a abertura e o fechamento deles
     * isso precisa ser feito sempre que utilizamos o ClassifierCompositeItemWriter porque ele não implementa a interface ItemStream
     * e nao fecha as operacoes automaticamente
     * Todos os escritores de arquivos usados no classifier precisam estar nos streams
     * */
    @Bean
    public Step migrarPessoasStep(
            ItemReader<Pessoa> arquivoPessoaReader,
            ClassifierCompositeItemWriter<Pessoa> pessoaClassifierWriter,
            FlatFileItemWriter<Pessoa> arquivoPessoasInvalidasWriter
    ) {
        return stepBuilderFactory
                .get("migrarPessoasStep")
                .<Pessoa, Pessoa>chunk(1)
                .reader(arquivoPessoaReader)
                .writer(pessoaClassifierWriter)
                .stream(arquivoPessoasInvalidasWriter)
                .build();
    }

}
