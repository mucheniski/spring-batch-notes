package com.springbatch.arquivolargurafixa.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivolargurafixa.dominio.Cliente;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ArquivoLarguraFixaWriterConfig {

	FileSystemResource arquivoSaida = new FileSystemResource("files/escrita.txt");


	/*
	Por padrão o preenchimento de espaços indicado com o percent % é feito na esquerda
	para corrigirmos isso e preenchermos os espaços a direita colocamos o %-TamanhoColuna
	 */

	@Bean
	public FlatFileItemWriter<Cliente> escritaArquivoLarguraFixaWriter() {
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("escritaArquivoLarguraFixaWriter")
				.resource(arquivoSaida)
				.formatted() // Indica que é um arquivo formatado
				.format("%-9s %-9s %-2s %-19s") // %-TamanhoColuna String - indica que a coluna tem tamanho fixo, completada com espaços se o texto nao atingir o tamanho informado
				.names("nome", "sobrenome", "idade", "email") // Nomes das colunas do arquivo escrito
				.build();
	}
}
