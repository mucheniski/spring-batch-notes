//package com.springbatch.processadorvalidacao.processor;
//
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.springbatch.processadorvalidacao.dominio.Cliente;
//
//@Configuration
//public class ProcessadorValidacaoProcessorConfig {
//	@Bean
//	public ItemProcessor<Cliente, Cliente> procesadorValidacaoProcessor() {
//
//		// Não altera o dado, só valida e passa validado para o escritor
//		BeanValidatingItemProcessor<Cliente> processor = new BeanValidatingItemProcessor<>();
//		processor.setFilter(true); // Apenas filtra os itens inválidos mas não impede o processamento dos demais
//		return processor;
//
//	}
//}
