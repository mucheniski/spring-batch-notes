package com.springbatch.contasbancarias.writer;

import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassifierContaWriterConfig {

    /*
    * Como existem dois beans de FlatFileItemWriter, preciso informar com um
    * Qualificador, qual bean uso aqui
    * */
    @Bean
    public ClassifierCompositeItemWriter<Conta> classificadorConta (
            @Qualifier("clienteInvalidoWriter") FlatFileItemWriter clienteInvalidoWriter,
            CompositeItemWriter<Conta> escritorCompostoBancoEArquivo
    ) {

        return new ClassifierCompositeItemWriterBuilder<Conta>()
                .classifier(classificadorConta(escritorCompostoBancoEArquivo, clienteInvalidoWriter))
                .build();

    }

    private Classifier<Conta, ItemWriter<? super Conta>> classificadorConta(CompositeItemWriter<Conta> escritorCompostoBancoEArquivo, FlatFileItemWriter clienteInvalidoWriter) {

        return new Classifier<Conta, ItemWriter<? super Conta>>() {
            @Override
            public ItemWriter<? super Conta> classify(Conta conta) {
                if (conta.getTipo() != null)
                    return escritorCompostoBancoEArquivo;
                else
                    return clienteInvalidoWriter;
            }
        };

    }

}
