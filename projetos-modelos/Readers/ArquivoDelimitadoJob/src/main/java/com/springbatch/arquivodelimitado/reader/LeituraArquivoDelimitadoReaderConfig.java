package com.springbatch.arquivodelimitado.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivodelimitado.dominio.Cliente;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class LeituraArquivoDelimitadoReaderConfig {

	FileSystemResource arquivoClientes = new FileSystemResource("files/clientes.txt");

	@StepScope
	@Bean
	public FlatFileItemReader<Cliente> leituraArquivoDelimitadoReader() {

		return new FlatFileItemReaderBuilder<Cliente>()
				.name("leituraArquivoDelimitadoReader")
				.resource(arquivoClientes)
				.delimited()
				.names(new String[] {"nome", "sobrenome", "idade", "email"})
				.targetType(Cliente.class)
				.build();


    }
}
