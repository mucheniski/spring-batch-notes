package com.springbatch.processadorscript.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ScriptItemProcessor;
import org.springframework.batch.item.support.builder.ScriptItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.processadorscript.dominio.Cliente;

@Configuration
public class ProcessadorScriptProcessorConfig {
	@Bean
	public ItemProcessor<Cliente, Cliente> processadorScriptProcessor() {

		// Para funcionar o script inserir esse comando no VM arguments -Dnashorn.args=-scripting
		String txtScript = 	" var email = item.getEmail(); " +
							" var arquivoExiste = `ls | grep ${email}.txt`; " +
							" if (!arquivoExiste) item;" +
							" else null; ";

		// Como nao foi informado onde buscar os dados no script, ele valida na home do linux se tem algum txt com o nome do fltro
		return new ScriptItemProcessorBuilder<Cliente, Cliente>()
				.language("nashorn")
				//.scriptResource("files/script.sh") // Se fosse script em arquivo seria aqui
				.scriptSource(txtScript)
				.build();
	}
}
