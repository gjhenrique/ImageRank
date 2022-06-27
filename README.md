# ImageRank

Classifica os melhores extratores de características e funções de distância baseado no seu conjunto de dados.

Trabalho de mestrado da matéria de Processamento Digital de Sinais.

Desenvolvido por:
    
        Guilherme Henrique
        Pedro Sena Tanaka
        
## Etapas

#### 1. Persistência das Imagens

Armazena as imagens do caminho junto com sua classe e o conjunto de dados (dataset).
Por padrão, a classe é retirada do nome do arquivo e o dataset é o nome da pasta que contém esses arquivos.
Por exemplo, se você tiver a seguinte estrutura no sistema de arquivos:

    	Pulmão
    	|-- Enfisema1.dcm
    	|-- Enfisema2.dcm
    	|-- Espessamento1.dcm
    	|-- Espessamento2.dcm
    	|-- Favo de Mel1.dcm
    	|-- Favo de Mel2.dcm

O dataset seria "Pulmão" e a classe (doenças) dos arquivos seriam Enfisema para o primeiro e segundo arquivo, Espessamento para o terceiro e quarto arquivo e assim por diante.

#### 2. **Extração de Características**

Extrai as características das imagens persistidas no passo anterior.
Mais de [30](https://github.com/gjhenrique/image-wavelet/blob/0ee87bb4a81f732ba0d25aa5a55a2157f0cce6d8/sql/seeds.sql) extratores podem ser utilizados 
e o programa, com a ajuda da biblioteca Guice, permite a injeção de novos extratores de maneira fácil.

#### 3. **Consultas k-NNs**

Roda diversas consultas k-NN entre as extrações previamente armazenadas no banco.
Várias [funções de distância](https://github.com/gjhenrique/image-wavelet/blob/master/sql/seeds.sql) foram implementadas.
É possível determinar o número máximo do k e o intervalo entre as consultas.

#### 4. **Visualização dos Resultados**

Mostra os resultados das consultas k-NN através de gráficos do tipo [Precision-Recall]([https://en.wikipedia.org/wiki/Precision_and_recall).
Os gráficos são gerados pelo programa GNUPlot com o uso da biblioteca JavaPlot.

## Ferramentas
* [Maven](https://maven.apache.org/)
* [jOOQ](http://www.jooq.org)
* [ImageJ](https://imagej.nih.gov/ij)
* [JWave](https://github.com/pedro-stanaka/JWave)
* [Guice](https://github.com/google/guice)
* [Lire](http://www.lire-project.net/)
* [JavaPlot](http://javaplot.panayotis.com/)

## Comunicação com o banco
Para se comunicar com o banco utilize um arquivo de properties chamado database.properties
e o coloque na pasta src/main/resources.

Exemplo:
        
        host=localhost
        port=5432
        database=banco
        user=usuario
        password=senha


## Geração automática de classes do jOOQ

Para configurar a geração automática das classes com o jOOQ veja a documentação em: [Generator Config](http://www.jooq.org/doc/3.3/manual/code-generation/codegen-configuration).
Um exemplo de arquivo de configuração se encontra em: jooq-config.xml.example.

Depois de configurado a comunicação com o banco e configuração do jOOQ basta rodar a task do ant:

		ant code-generation

## New Readme

Add build instructions
Add run instructions
Add test instructions

New pictures about

java8 is required

gnuplot installation is required

This work was used to identify X-rays from lungs of different diseases.

EQP
