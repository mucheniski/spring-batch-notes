package com.springbatch.arquivodelimitado.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivodelimitado.dominio.Cliente;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ArquivoDelimitadoWriterConfig {

	FileSystemResource arquivoClientesSaida = new FileSystemResource("files/arquivo-saida.txt");

	/*
	 O delimitador padrão e a virgula, se quiser mudar pasta inserir em delimiter
	 como o ponto e vírgula por exemplo.
	 */
	@Bean
	public FlatFileItemWriter<Cliente> arquivoDelimitadoWriter() {
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("arquivoDelimitadoWriter")
				.resource(arquivoClientesSaida)
				.delimited()
				.delimiter(";") // Caso queira tirar o delimitador padrão que é a vírgula
				.names("nome", "sobrenome", "idade", "email")
				.build();
	}
}
