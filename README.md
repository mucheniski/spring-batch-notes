# spring-batch-notes
Documentação spring batch - https://docs.spring.io/spring-batch/docs/current/reference/html/index.html  
Overview Spring Batch - https://giuliana-bezerra.medium.com/spring-batch-para-desenvolvimento-de-jobs-1674ec5b9a20  



## Sistema Batch  
Um sistema que realiza um processamento de uma quantidade finita de dados sem interação ou interrupção.  

## Spring Batch  
Framework para desenvolvimento de sistemas batch usando o spring com java.  

A estrutura básice de um projeto spring batch consiste em Job > Step > Reader > Processor > Writer  
Todos os componentes são criados com builders que já são inclusos no spring batch, como por exemplo o JobBuilderFactory.  

## Job  
Informa qual o serviço a ser executado no processo.  

## Step  
Podem ser simples ou complexas, podem ser encadeadas em vários steps sequencias, são informados no job.  
Para tarefas simples usamos tesklets.  

![](/img/PrimeiroProjetoSpringBatch.png)  
O metodo job precisa conter a anotação @Bean para que seja inserido no contexto do spring.  
A classe precisa conter as anotaçoes @Configuration para indicar que é uma classe de configuração spring e @EnableBatchProcessing para que o spring batch seja habilidado e possa ser executado corretamente.  

Steps mais complexos são basedos em chunks(pedaços), nesse caso possuem reader, processor e writer.  

