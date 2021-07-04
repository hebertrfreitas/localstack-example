### Projeto de exemplo localstack

Este projeto é um exemplo prático de comunicação com alguns dos principais serviços da aws.



>OBS: Propositalmente neste projeto está sendo usado o SDK da AWS diretamente ao invês de facilidades como o spring cloud aws.
>
>O objetivo aqui era aprender a manipular o sdk e suas funcionalidades.



Projeto feito usando spring boot e Kotlin.


**Requisitos para execução**
 - java 11.
 - docker
 

O projeto contém classes de teste que simulam operações nos serviços da AWS.

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

