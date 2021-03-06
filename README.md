# spring-batch-notes
Documentação spring batch - https://docs.spring.io/spring-batch/docs/current/reference/html/index.html  
Overview Spring Batch - https://giuliana-bezerra.medium.com/spring-batch-para-desenvolvimento-de-jobs-1674ec5b9a20  



## Sistema Batch  
Um sistema que realiza um processamento de uma quantidade finita de dados sem interação ou interrupção.  

## Spring Batch  
Framework para desenvolvimento de sistemas batch usando o spring com java.  

A estrutura básice de um projeto spring batch consiste em Job > Step > Reader > Processor > Writer  
Todos os componentes são criados com builders que já são inclusos no spring batch, como por exemplo o JobBuilderFactory.  

## Modelo de execução de um processo spring batch  
![](/img/modeloExecucaoSpringBatch.png)  


## Job  
Informa qual o serviço a ser executado no processo.  

## Step  
Podem ser simples ou complexas, podem ser encadeadas em vários steps sequencias, são informados no job.  
Para tarefas simples usamos tesklets.  

![](/img/PrimeiroProjetoSpringBatch.png)  
O metodo job precisa conter a anotação @Bean para que seja inserido no contexto do spring.  
A classe precisa conter as anotaçoes @Configuration para indicar que é uma classe de configuração spring e @EnableBatchProcessing para que o spring batch seja habilidado e possa ser executado corretamente.  

Steps mais complexos são basedos em chunks(pedaços), nesse caso possuem reader, processor e writer.  

## Job Repository  
É o repositório onde ficam armazenados os metadados do spring batch, que são as informações sobre execução dos jobs, steps, etc...
O spring batch cria as tabelas automáticamente na base configurada usando a propriedade no application.properties  
~~~
    spring.batch.initialize-schema=always
~~~  
As tabelas criadas podem responder algumas perguntas como:  
BATCH_JOB_INSTANCE - Quantas vezes o batch executou com sucesso, execuções lógicas.  
BATCH_JOB_EXECUTION - Quantas vezes o batch executou ao todo, por completo, incluindo execuções com falhas.  
BATCH_JOB_EXECUTION_CONTEXT - Mostra informações importantes para o contexto de execução do job, qualquer informação adicional importante para sabermos sobre a execução, como por exemplo informações de negócio.  
BATCH_JOB_EXECUTION_PARAMS - Informa para cada execução, qual parâmetro foi utilizado. Observar as colunas KEY_NAME e LONG_VAL.  

BATCH_STEP_EXECUTION - Quais steps foram executados, o JOB_EXECUTION_ID vincula o step ao JOB. Também tem a coluna COMMIT_COUNT que mostra a contagem de quantas transacoes foram efetuadas no step.  
BATCH_STEP_EXECUTION_CONTEXT - Podem ser adicionadas informações específicas no step para entender melhor o funcionamento dele, como mapa de dados chave valor. na coluna SHORT_CONTEXT por exemplo, existe uma propriedade read.count que é um ponteiro que mostra quantos registros foram lidos e commitados nesse step. Isso serve para que caso aconteca algum erro, o job possa ser reinicializado a partir dali.


## Execução do JOB  
Cada JOB deve ser executado apenas uma vez, caso tente executar o mesmo JOB com os mesmos parâmetros mais de uma vez, o EXIT_CODE em BATCH_JOB_EXECUTION vai ficar como NOOP, e não será executado o JOB, para prevenir isso é preciso adicionar um incrementer logo após o start, assim vai ser criado um novo id a cada JOB, permitindo que seja executado porque o parâmetro id é diferente a cada execução.  
Porém deve ser observado que esse incrementer impede a reinicialização do JOB, no caso de algum erro, o JOB é reiniciado, por isso não é sempre indicado o incrementador, depende da necessidade de negócio.  
![](/img/Incrementer.png)  

O Projeto pode possuir mais de um job.  

## Reinicializacao do JOB  
Caso aconteça algum erro, o JOB precisa ser reinicializado, caso possua incrementer no job, não vai ser possivel executar novamente.  
Relembrando:  BATCH_STEP_EXECUTION_CONTEXT - Podem ser adicionadas informações específicas no step para entender melhor o funcionamento dele, como mapa de dados chave valor. na coluna SHORT_CONTEXT por exemplo, existe uma propriedade read.count que é um ponteiro que mostra quantos chunks foram lidos e commitados nesse step. Isso serve para que caso aconteca algum erro, o job possa ser reinicializado a partir dali. Lembrando que o uso do incrementer pode interferir na reinicialização do mesmo job, porque ele faz com que um novo id de execução seja criado. É preciso avalidar a necessidade do negócio em relação ao incrementer. O read.count considera o chunk, é reestartado à partir da quantidade de chunk definida no step.  
Se a falha ocorrer no meio do chunk, não é possível recuperar do erro.  


## Tipos de Steps  
Existem dois tipos, tasklets ou chunk, tasklets são usadas para pequenas tarefas, mais simples como limpeza de arquivos por exemplo.  
Chunks são utilizados para processamentos mais complexos, que precisam ser executados em pedaços.  
Esses pedaços são divididos em tarefas de Leitura(ItemReader), Processamento(ItemProcessor) e escrita(ItemWriter).  
Cada chunk possui a sua própria transação.  
Exemplo de fluxo do chunk.  
![](/img/FluxoChunk.png)  

O Reader le os dados em coleção.  
O Processor processa cada dado dessa coleção, um item de cada vez.  
O Writer escreve essa coleção completa processada.  


# Readers  
Le os dados baseado no tamanho definido no chunk, e devolve um a um para o processamento (Processor).  

