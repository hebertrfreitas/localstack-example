# LocalStack

O projeto [localstack](https://github.com/localstack/localstack) fornece um mock dos principais servicos da AWS como SQS, SNS, S3 entre outros.

Trata-se de um recurso extremamente útil quando estamos desenvolvendo uma aplicação que precisa se comunicar com tais serviços.

A forma mais conveniente de executar o projeto é através de uma imagem docker oficial disponível neste link:  [dockerhub localstack](https://hub.docker.com/r/localstack/localstack)

## Executando o projeto via docker-compose

No arquivo [docker-compose](./springboot_localstack/docker-compose.yaml) você encontrará um exemplo de como subir o localstack via docker.

Para subir o serviço basta executar

```sh
docker-compose up localstack
```


## Interagindo com os serviços da aws no localstack

Uma das formas oficiais de se comunicar com os serviços da aws é o [AWS CLI](https://hub.docker.com/r/localstack/localstack)

Você pode ter o awscli instalado na sua máquina e apontar para os serviços da aws mockados no localstack.
Para fazer isso basta inserir em todos os comando o seguinte parâmetro: `--endpoint-url=http://localstack:4566`

Exemplo de criação de uma queue no SQS: 

```sh
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name teste
```

No entanto, caso você não queria ou não precise ter o `awscli` instalado no seu computador, o localstack já disponibiliza um wrapper do `awscli` chamado `awslocal` que já vem pré-configurado para interagir localmente com os serviços mockados da aws.

Considerando que você esteja executando o projeto via docker é possível fazer o mesmo exemplo citado acima da seguinte maneira:

```sh
#para entrar no container (considerando que o nome do seu container é localstack)
docker exec -it localstack sh
#comando equivalente ao apresentado acima para criar uma fila 
awslocal sqs create-queue --queue-name teste 
```

Observe que não precisamos adicionar o parâmetro `--endpoint-url` pois o `awslocal` já está configurado assumindo que desejo me comunicar com os serviços localmente.


> **⚠ Porque localhost:4566 ?**  
> O projeto localstack disponibiliza o mock dos serviços da aws na porta 4566.
>
> Considerando que você está executando o projeto via docker é importante que você mapeie a porta 4566 do container para a 4566(ou outra a seu critério) da sua máquina local. 




## Projeto de exemplo

Na pasta [springboot_localstack](./springboot_localstack) existe um projeto de exemplo construído em spring boot usando a linguagem Kotlin feito para exemplificar o uso básico de alguns serviços da aws usando o localstack.

Observe que o código não mudaria em nada para se comunicar com serviços reais da aws a não ser as configurações de credenciais.


## Comandos comuns do aws cli

A seguir alguns comandos básicos do awscli para se comunicar com os serviços da aws.

*OBS: Todos os comando a seguir usam o awslocal e assumem que estão sendo executados dentro do container do localstack, no entanto, é perfeitamente possível executar os mesmo usando o awscli.
Para uma referência completa consulte a documentação da AWS.*

### s3 

Serviço de storage da AWS

**Criando um bucket**
```sh
awslocal s3api create-bucket --bucket test-bucket
```

**Listando buckets**
```sh
awslocal s3api list-buckets
```

**Inserindo um arquivo no bucket**
```sh
awslocal s3api put-object --bucket test-bucket --key test_dir/item_1 --body /tmp/some-text-file.txt
```

**Listando arquivos no bucket**
```sh
awslocal s3api list-objects --bucket test-bucket
```

