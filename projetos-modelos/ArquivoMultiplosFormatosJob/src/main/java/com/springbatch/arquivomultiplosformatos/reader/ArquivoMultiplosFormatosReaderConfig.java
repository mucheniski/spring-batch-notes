package com.springbatch.arquivomultiplosformatos.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ArquivoMultiplosFormatosReaderConfig {

	FileSystemResource arquivoClientes = new FileSystemResource("files/clientes.txt");

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Bean
	public FlatFileItemReader arquivoMultiplosFormatosItemReader(LineMapper lineMapperCustomizado) {

		return new FlatFileItemReaderBuilder()
				.name("arquivoMultiplosFormatosItemReader")
				.resource(arquivoClientes)
				.lineMapper(lineMapperCustomizado)
				.build();
	}

}
