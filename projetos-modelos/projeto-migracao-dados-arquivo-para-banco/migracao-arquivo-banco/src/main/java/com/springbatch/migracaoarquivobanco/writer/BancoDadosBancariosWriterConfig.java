package com.springbatch.migracaoarquivobanco.writer;

import com.springbatch.migracaoarquivobanco.domain.DadosBancarios;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BancoDadosBancariosWriterConfig {

    String sqlInsert = "INSERT INTO migracao_dados.dados_bancarios\n" +
            "(id, pessoa_id, agencia, conta, banco)\n" +
            "VALUES(:id, :pessoaId, :agencia, :conta, :banco);\n";

    @Bean
    public JdbcBatchItemWriter<DadosBancarios> bancoDadosBancariosWriter(@Qualifier("aplicacaoDataSource") DataSource aplicacaoDataSource) {

        return new JdbcBatchItemWriterBuilder<DadosBancarios>()
                .dataSource(aplicacaoDataSource)
                .sql(sqlInsert)
                .beanMapped() // como nao tem nada complexo, como a data por exemplo, o beanMapped ja resolve
                .build();

    }

}
