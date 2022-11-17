package com.springbatch.jdbccursorreader.reader;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.jdbccursorreader.dominio.Cliente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class JdbcCursorReaderConfig {

	@Bean
	public JdbcCursorItemReader<Cliente> jdbcCursorReader(@Qualifier("aplicacaoDataSource") DataSource aplicacaoDatasource) {

		String queryClientes = "select * from cliente";

		return new JdbcCursorItemReaderBuilder<Cliente>()
				.name("jdbcCursorReader")
				.dataSource(aplicacaoDatasource)
				.sql(queryClientes)
				.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class)) // Se o nome das colunas for igual ao nome das propriedades do objeto, o rowmapper já faz automaticamente
				.build();
	}
}
