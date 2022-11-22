package com.springbatch.demonstrativoorcamentario.writer;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {


	/*
	@Value("#{jobParameters['demonstrativosOrcamentarios']}") Resource demonstrativosOrcamentarios, serve para buscar o recurso do que foi
	configurado na execução do job Run As > Run Configuration
	 */
	@StepScope // Precisa dessa anotação para acessar um arquivo no momento da execução do projeto Run Configurations
	@Bean
	public MultiResourceItemWriter<GrupoLancamento> variosDemonstrativosOrcamentariosWriter(
			@Value("#{jobParameters['demonstrativosOrcamentarios']}") Resource demonstrativosOrcamentarios,
			FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter
	) {
		return new MultiResourceItemWriterBuilder<GrupoLancamento>()
				.name("variosDemonstrativosOrcamentariosWriter")
				.resource(demonstrativosOrcamentarios)
				.delegate(demonstrativoOrcamentarioWriter)
				.resourceSuffixCreator(criadorSulfixo()) // Salvar o sulfixo como .txt  por exemplo
				.itemCountLimitPerResource(1) // A cada recurso um arquivo, ou seja, um para cada GrupoLancamento que é o objeto sendo tratado - Só é checado no final de cada chunk
				.build();
	}

	private ResourceSuffixCreator criadorSulfixo() {
		return new ResourceSuffixCreator() {
			@Override
			public String getSuffix(int index) {
				return index + ".txt";
			}
		};
	}


	@Bean
	public FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter(
			DemonstrativoOrcamentarioRodape customFooter
	) {

		FileSystemResource arquivoDemonstrativoOrcamentario = new FileSystemResource("files/arquivoDemonstrativoOrcamentario.txt");

		return new FlatFileItemWriterBuilder<GrupoLancamento>()
				.name("demonstrativoOrcamentarioWriter")
				.resource(arquivoDemonstrativoOrcamentario)
				.headerCallback(customHeader()) // Cabeçalho
				.lineAggregator(customLineAggregator())
				.footerCallback(customFooter) // Rodapé
				.build();

	}

	private FlatFileHeaderCallback customHeader() {
		return new FlatFileHeaderCallback() {
			@Override
			public void writeHeader(Writer writer) throws IOException {

				writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s\n", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
				writer.append(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t HORA: %s\n", new SimpleDateFormat("HH:MM").format(new Date())));
				writer.append(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO\n"));
				writer.append(String.format("----------------------------------------------------------------------------\n"));
				writer.append(String.format("CODIGO NOME VALOR\n"));
				writer.append(String.format("\t Data Descricao Valor\n"));
				writer.append(String.format("----------------------------------------------------------------------------\n"));

			}
		};
	}

	private LineAggregator<GrupoLancamento> customLineAggregator() {

		return new LineAggregator<GrupoLancamento>() {
			@Override
			public String aggregate(GrupoLancamento grupoLancamento) {


				// Linha de lançamento
				String linhaGrupoLancamento =
						String.format(
								"[%d] %s - %s\n",
								grupoLancamento.getCodigoNaturezaDespesa(),
								grupoLancamento.getDescricaoNaturezaDespesa(),
								NumberFormat.getCurrencyInstance().format(grupoLancamento.getTotal())
						);

				// Lançamentos
				StringBuilder linhaLancamento = new StringBuilder();
				for (Lancamento lancamento : grupoLancamento.getLancamentos()) {
					linhaLancamento.append(
							String.format(
								"\t [%s] %s - %s\n",
								new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()),
								lancamento.getDescricao(),
								NumberFormat.getCurrencyInstance().format(lancamento.getValor())
							)
					);
				}

				return linhaGrupoLancamento + linhaLancamento.toString();
			}
		};

	}


//	@Bean
//	public ItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter() {
//		return itens -> {
//			System.out.println("\n");
//			System.out.println(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
//			System.out.println(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t HORA: %s", new SimpleDateFormat("HH:MM").format(new Date())));
//			System.out.println(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO"));
//			System.out.println(String.format("----------------------------------------------------------------------------"));
//			System.out.println(String.format("CODIGO NOME VALOR"));
//			System.out.println(String.format("\t Data Descricao Valor"));
//			System.out.println(String.format("----------------------------------------------------------------------------"));
//
//			Double totalGeral = 0.0;
//			for (GrupoLancamento grupo : itens) {
//				System.out.println(String.format("[%d] %s - %s", grupo.getCodigoNaturezaDespesa(),
//						grupo.getDescricaoNaturezaDespesa(),
//						NumberFormat.getCurrencyInstance().format(grupo.getTotal())));
//				totalGeral += grupo.getTotal();
//				for (Lancamento lancamento : grupo.getLancamentos()) {
//					System.out.println(String.format("\t [%s] %s - %s", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
//							NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
//				}
//			}
//			System.out.println("\n");
//			System.out.println(String.format("\t\t\t\t\t\t\t  Total: %s", NumberFormat.getCurrencyInstance().format(totalGeral)));
//			System.out.println(String.format("\t\t\t\t\t\t\t  Código de Autenticação: %s", "fkyew6868fewjfhjjewf"));
//		};
//	}



}
