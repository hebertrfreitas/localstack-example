### Projeto de exemplo localstack

Este projeto é um exemplo prático de comunicação com alguns dos principais serviços da aws.

Projeto feito usando spring boot e Kotlin.


**Requisitos para execução**
 - java 11.
 - docker
 

O projeto contem classes de teste que simulam operações nos serviços da AWS.

Para que seja possível executar os testes é necessário que o localstack esteja sendo executado.

Você pode fazer isso usando os seguintes comandos

```sh
docker-compose up -d localstack
./gradlew test
```

Ou se preferir execute o script disponível na raiz do projeto

```
chmod +x run-tests.sh
./run-tests.sh
```

