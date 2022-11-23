package com.springbatch.migracaoarquivobanco.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@EnableBatchProcessing
@Configuration
public class MigracaoDadosJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /*
    * Como os steps são independentes, eles podem ser executados de forma paralela
    * para otimizar a execução do job
    * */
    @Bean
    public Job migracaoDadosJob(
            @Qualifier("migrarPessoasStep") Step migrarPessoasStep,
            @Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep
    ) {
        return jobBuilderFactory
                .get("migracaoDadosJob")
                .start(stepsParalelos(migrarPessoasStep, migrarDadosBancariosStep))
                .end() // Como iniciei um flw preciso finalizar
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Flow stepsParalelos(Step migrarPessoasStep, Step migrarDadosBancariosStep) {

        Flow migrarDadosBancariosFlow = new FlowBuilder<Flow>("migrarDadosBancariosFlow")
                .start(migrarDadosBancariosStep)
                .build();

        Flow migrarPessoasFlow = new FlowBuilder<Flow>("migrarPessoasFlow")
                .start(migrarPessoasStep)
                .build();

        Flow stepsParalelosFlow = new FlowBuilder<Flow>("stepsParalelosFlow")
                .start(migrarPessoasFlow)
                .split(new SimpleAsyncTaskExecutor()) // Gerenciador de tarefas paralelas
                .add(migrarDadosBancariosFlow)
                .build();

        return stepsParalelosFlow;

    }

}
