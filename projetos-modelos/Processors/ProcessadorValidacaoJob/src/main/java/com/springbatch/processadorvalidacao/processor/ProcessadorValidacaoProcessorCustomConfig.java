package com.springbatch.processadorvalidacao.processor;

import com.springbatch.processadorvalidacao.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ProcessadorValidacaoProcessorCustomConfig {

	private Set<String> emails = new HashSet<>();

	@Bean
	public ItemProcessor<Cliente, Cliente> procesadorValidacaoCustomProcessor() {

		// Não altera o dado, só valida e passa validado para o escritor
		// Com validações mais customizadas
		ValidatingItemProcessor<Cliente> processor = new ValidatingItemProcessor<>();
		processor.setValidator(customValidator());
		processor.setFilter(true); // Não interrompe o batch
		return processor;

	}

	// Verificar se existe cliente duplicado
	private Validator<Cliente> customValidator() {
		return new Validator<Cliente>() {
			@Override
			public void validate(Cliente cliente) throws ValidationException {
				if (emails.contains(cliente.getEmail()))
					throw new ValidationException(String.format("O cliente %s já foi processado", cliente.getEmail()));
				emails.add(cliente.getEmail());
			}
		};
	}

}
