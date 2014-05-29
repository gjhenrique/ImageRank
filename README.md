
# Image Wavelet


Trabalho de mestrado apresentado a matéria de Processamento Digital de Sinal.

Desenvolvido por:
    
        Guilherme José Henrique
        Pedro Sena Tanaka
        

Tecnologias/bibliotecas usadas:
        
        Maven
        jOOQ
        ImageJ
        JWave
        



## Comunicação com o banco
Para se comunicar com o banco utilize um arquivo de properties chamado database.properties
e o coloque na pasta de resources do MVN.

Exemplo:
        
        host=localhost
        port=5432
        database=banco
        user=usuario
        password=senha

Para configurar a criação das classes com o jOOQ veja a documentação em: [Generator Config](http://www.jooq.org/doc/3.3/manual/code-generation/codegen-configuration).
Um exemplo de arquivo de configuração se encontra em: jooq-config.xml.example.