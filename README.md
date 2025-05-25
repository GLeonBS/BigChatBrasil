# API - BigChatBrasil

Esta é uma API RESTful para gerenciamento de envio de mensagens, desenvolvida com **Spring Boot**, **PostgreSQL** e *
*Java 17**.

---

## ✅ Tecnologias

- Java 17
- Spring Boot
- PostgreSQL
- Liquibase
- Docker

---

## 🚀 Como rodar com Docker Compose

Certifique-se de ter o [Docker](https://www.docker.com/products/docker-desktop) e
o [Docker Compose](https://docs.docker.com/compose/) instalados.

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/bigchatbrasil.git
cd bigchatbrasil
```

### 2. Rode a aplicação

```bash
docker-compose up
```

### 3. Acesse a API

- API: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/api/swagger-ui/index.html#/`

> O banco de dados estará disponível internamente para a aplicação via hostname `postgres`.

---

## 🛠️ Configuração manual (sem Docker)

> Use apenas se preferir rodar localmente sem Docker.

- Instale a [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Suba um banco PostgreSQL com:
    - `user`: `postgres`
    - `password`: `postgres`
    - `database`: `big_chat_db`
- Atualize o `application.yml` ou `application.properties` com suas configurações
- Rode a aplicação:

```bash
./mvnw spring-boot:run
```

---

## 📚 Endpoints principais

A API possui os seguintes endpoints RESTful (prefixo `/api`):

- `POST /cliente`: Cria um novo cliente.
- `GET /cliente/{id}`: Retorna os detalhes de um cliente.
- `GET /cliente`: Retorna todos os clientes cadastrados.
- `PUT /cliente/{id}`: Atualiza um cliente.
- `DELETE /cliente/{id}`: Deleta um cliente.
- `POST /cliente/auth`: Realiza o login de um Cliente.
- `POST /add-saldo-credito/{id}`: Adiciona saldo de crédito a um cliente com plano Pré-Pago.
- `GET /cliente/saldo/{id}`: Retorna o saldo atual do cliente.
- `POST /destinatario`: Cria um novo destinatário.
- `POST /chat`: Cria um novo chat.
- `GET /chat/{id}`: Retorna os detalhes de um chat.
- `GET /chat`: Retorna todos os chats cadastrados para o cliente logado.
- `GET /chat/mensagens`: Retorna todas as mensagens de um chat.
- `POST /mensagem`: Envia uma nova mensagem.
- `GET /mensagem/{id}`: Retorna os detalhes de uma mensagem.
- `GET /mensagem/{id}/status`: Retorna o status de uma mensagem.

Todos os dados são enviados e recebidos em **JSON**.

---

### 🧪 Exemplo de requisição - `POST /cliente`

```json
{
  "nome": "Leon",
  "documento": "01695216059",
  "tipoDocumento": "CPF",
  "conta": {
    "plano": "PRE_PAGO",
    "credito": 45.4,
    "limite": 0,
    "limiteConsumido": 0
  },
  "ativo": true,
  "numeroTelefone": "44999999999",
  "senha": "123Senha",
  "role": "ROLE_CLIENTE"
}
```

---

### 📥 Exemplo de resposta - `GET /cliente/{id}`

```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "nome": "Leon",
  "documento": "01695216059",
  "tipoDocumento": "CPF",
  "conta": {
    "plano": "PRE_PAGO",
    "credito": 45.4,
    "limite": 0,
    "limiteConsumido": 0
  },
  "ativo": true
}
```

---

## 📄 Licença

Este projeto é open-source e pode ser usado livremente.
