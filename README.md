# API - BigChatBrasil

Esta é uma API RESTful de CRUD para gerenciar informações de envio de mensagens. Foi desenvolvida usando o framework
Spring Boot, PostgreSQL como banco de dados e Java 17 como linguagem.

## Configuração

Certifique-se de ter a [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) instalada.

### Banco de Dados

1. O banco é inicializado via docker então você pode instalar o docker e rodar o comando abaixo para subir o banco de
   dados.

```
docker-compose up
```

2. Caso prefira, você pode instalar o PostgreSQL localmente e criar um banco de dados chamado `big_chat_db`.
3. Altere as configurações de banco de dados no arquivo `application.yml` para corresponder às suas credenciais, o
   padrão é postgres/postgres.

## Executando a aplicação

1. Abra o projeto.
2. Certifique-se de ter as extensões do Spring Boot instaladas (como `Spring Boot Tools`).
3. Execute a aplicação através da opção de build e run disponível na IDE.

A API estará disponível em `http://localhost:8080`.

## Endpoints

A API possui os seguintes endpoints:

- `POST /cliente/`: Para criar um novo cliente.
- `POST /add-saldo-credito/{id}`: Adiciona saldo de crédito a um cliente caso ele tenha uma conta com plano Pré Pago.
- `GET /cliente/{id}`: Retorna os detalhes de um cliente específico.
- `GET /cliente/saldo/{id}`: Retorna o saldo de um cliente específico.
- `PUT /cliente/`: Atualiza os dados de um cliente existente.
- `POST /destinatario/`: Para criar um novo destinatário.
- `POST /chat/`: Para criar um novo chat.
- `POST /mensagem/`: Para enviar uma mensagem.

Os dados são enviados e retornados no formato JSON.

### Exemplo de solicitação para a rota `POST /cliente`:

```json
{
  "nome": "Leon",
  "email": "Leon@leon.com",
  "telefone": "44999999999",
  "cpfResponsavel": "23238766007",
  "cnpj": "36739551000119",
  "nomeEmpresa": "Leon LTDA",
  "conta": {
    "plano": "PRE_PAGO",
    "credito": 45.4,
    "limite": 0.00
  }
}
```

### Exemplo de resposta para a rota `GET /cliente/{id}`:

```json
{
  "id": "85593ad8-05b2-415c-83e1-7847f04dc179",
  "nome": "Leon",
  "email": "Leon@leon.com",
  "telefone": "44999999999",
  "cpfResponsavel": "23238766007",
  "cnpj": "36739551000119",
  "nomeEmpresa": "Leon LTDA",
  "conta": {
    "plano": "PRE_PAGO",
    "credito": 45.40,
    "limite": 0.00,
    "saldo": 45.40
  }
}
```
