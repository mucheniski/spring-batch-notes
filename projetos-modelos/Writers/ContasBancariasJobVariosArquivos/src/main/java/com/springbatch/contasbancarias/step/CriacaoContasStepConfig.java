package com.springbatch.contasbancarias.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Cliente;
import com.springbatch.contasbancarias.dominio.Conta;

@Configuration
public class CriacaoContasStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	/*
	* org.springframework.batch.item.WriterNotOpenException: Writer must be open before it can be written to
	* Para evitar esse erro, preciso que sejam declarados os streams do ClassifierCompositeItemWriter pois precisam ser informados
	* a abertura e fechamento do componente, abaixo do writer com .stream() assim o spring gerencia a abertura e o fechamento deles
	* */
	@Bean
	public Step criacaoContasStep(
			ItemReader<Cliente> leituraClientesReader, 
			ItemProcessor<Cliente, Conta> geracaoContaProcessor,
			ClassifierCompositeItemWriter<Conta> classificadorConta,
			@Qualifier("clienteInvalidoWriter") FlatFileItemWriter clienteInvalidoWriter,
			@Qualifier("fileContaWriter") FlatFileItemWriter<Conta> fileContaWriter) {
		return stepBuilderFactory
				.get("criacaoContasStep")
				.<Cliente, Conta>chunk(100)
				.reader(leituraClientesReader)
				.processor(geracaoContaProcessor)
				.writer(classificadorConta)
				.stream(clienteInvalidoWriter)
				.stream(fileContaWriter)
				.build();
	}
}
