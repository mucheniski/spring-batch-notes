package com.springbatch.faturacartaocredito.step;

import com.springbatch.faturacartaocredito.dominio.FaturaCartaoCredito;
import com.springbatch.faturacartaocredito.dominio.Transacao;
import com.springbatch.faturacartaocredito.reader.FaturaCartaoCreditoReader;
import com.springbatch.faturacartaocredito.writer.TotalTransacoesFooterCallback;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaturaCartaoCreditoStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step faturaCartaoCreditoStep(
            ItemStreamReader<Transacao> transacaoReader,
            ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregaDadosClienteProcesser,
            ItemWriter<FaturaCartaoCredito> faturaCartaoCreditoWriter,
            TotalTransacoesFooterCallback totalTransacoesFooterCallbackListener
    ) {
        return stepBuilderFactory
                .get("faturaCartaoCreditoStep")
                .<FaturaCartaoCredito, FaturaCartaoCredito>chunk(1)
                .reader(new FaturaCartaoCreditoReader(transacaoReader))
                .processor(carregaDadosClienteProcesser)
                .writer(faturaCartaoCreditoWriter)
                .listener(totalTransacoesFooterCallbackListener)
                .build();
    }

}
