package com.springbatch.contasbancarias.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Conta;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class jdbcContaWriterConfig {

	String sqlScript = "insert into conta (tipo, limite, cliente_id) values (?, ?, ?)";

	@Bean
	public JdbcBatchItemWriter<Conta> jdbcBatchItemWriter(
			@Qualifier("appDataSource") DataSource appDataSource
			) {
		return new JdbcBatchItemWriterBuilder<Conta>()
				.dataSource(appDataSource)
				.sql(sqlScript)
				.itemPreparedStatementSetter(starValoresNoInsert())
				.build();
	}

	private ItemPreparedStatementSetter<Conta> starValoresNoInsert() {
		return new ItemPreparedStatementSetter<Conta>() {
			@Override
			public void setValues(Conta conta, PreparedStatement ps) throws SQLException {
					ps.setString(1, conta.getTipo().name());
					ps.setDouble(2, conta.getLimite());
					ps.setString(3, conta.getClienteId());
			}
		};
	}


//	@Bean
//	public ItemWriter<Conta> impressaoContaWriter() {
//		return contas -> contas.forEach(System.out::println);
//	}


}
