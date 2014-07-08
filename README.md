
# Image Wavelet


Trabalho de mestrado apresentado a matéria de Processamento Digital de Sinal.

Desenvolvido por:
    
        Guilherme José Henrique
        Pedro Sena Tanaka
        

Tecnologias/bibliotecas usadas:
        
        Maven
        [jOOQ](https://github.com/jOOQ/jOOQ)
        [ImageJ](https://github.com/imagej/imagej)
        [JWave](https://github.com/pedro-stanaka/JWave)
        [Guice](https://github.com/google/guice)
        



## Comunicação com o banco
Para se comunicar com o banco utilize um arquivo de properties chamado database.properties
e o coloque na pasta de resources do MVN.

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

        ```bash
        ant code-generation
        ```