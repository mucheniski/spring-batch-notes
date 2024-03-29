package com.springbatch.processadorscript.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.springbatch.processadorscript.dominio.Cliente;

@Configuration
public class ProcessadorScriptReaderConfig {

	FileSystemResource arquivoClientes = new FileSystemResource("files/clientes.txt");

	@Bean
	public FlatFileItemReader<Cliente> processadorScriptReader() {
		return new FlatFileItemReaderBuilder<Cliente>()
				.name("processadorScriptReader")
				.resource(arquivoClientes)
				.delimited()
				.names("nome", "idade", "email")
				.targetType(Cliente.class)
				.build();
	}
}
