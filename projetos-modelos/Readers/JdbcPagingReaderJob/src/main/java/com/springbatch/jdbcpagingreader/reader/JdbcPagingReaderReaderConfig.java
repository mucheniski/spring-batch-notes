package com.springbatch.jdbcpagingreader.reader;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.jdbcpagingreader.dominio.Cliente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class JdbcPagingReaderReaderConfig {
	@Bean
	public JdbcPagingItemReader<Cliente> jdbcPagingReader(
			@Qualifier("aplicacaoDataSource") DataSource aplicacaoDataSource,
			PagingQueryProvider pagingQueryProvider
	) {

		return new JdbcPagingItemReaderBuilder<Cliente>()
				.name("jdbcPagingReader")
				.dataSource(aplicacaoDataSource)
				.queryProvider(pagingQueryProvider) // Configura a consulta paginada
				.pageSize(1)
				.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
				.build();
	}

	// Monta a consulta paginada
	@Bean
	public SqlPagingQueryProviderFactoryBean pagingQueryProvider(@Qualifier("aplicacaoDataSource") DataSource aplicacaoDataSource) {
		SqlPagingQueryProviderFactoryBean sqlPagingQueryProvider = new SqlPagingQueryProviderFactoryBean();
		sqlPagingQueryProvider.setDataSource(aplicacaoDataSource);
		sqlPagingQueryProvider.setSelectClause("select *");
		sqlPagingQueryProvider.setFromClause("from cliente");
		sqlPagingQueryProvider.setSortKey("email"); // Quando a consulta é paginada, precisa garantir uma ordenação por uma chave

		return sqlPagingQueryProvider;
	}


}
