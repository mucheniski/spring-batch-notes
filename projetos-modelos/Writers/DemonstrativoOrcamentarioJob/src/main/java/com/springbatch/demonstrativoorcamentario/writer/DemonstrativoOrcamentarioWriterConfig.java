package com.springbatch.demonstrativoorcamentario.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {

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
