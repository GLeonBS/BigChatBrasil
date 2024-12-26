# Sistema de Mensagens: BCB – Big Chat Brasil

## Descrição Geral

Um sistema para envio de mensagens que opera com base em diferentes planos de pagamento.

## Fluxo de Envio de Mensagens

- **Verificação do plano de pagamento do cliente:**
    - **Pré-pago:** Verificar os créditos disponíveis do cliente. Cada mensagem custa **R$ 0,25**.
    - **Pós-pago:** Registrar o consumo na conta até o limite máximo autorizado.

---

## Estrutura de Dados

### Cliente

- **Atributos:**
    - `Id`
    - `Nome`
    - `E-mail`
    - `Telefone`
    - `CPF responsável`
    - `CNPJ`
    - `Nome da empresa`
    - **Conta (VO):**
        - `Plano`
        - `Créditos`
        - `Limite`

### Destinatário

- **Atributos:**
    - `Id`
    - `Nome`
    - `Telefone`

### Mensagem

- **Atributos:**
    - `Id`
    - `Número de telefone`
    - `É WhatsApp?`
    - `Texto`
    - `Chat`
    - `Data/Hora do envio`

### Mensagem Histórico

- **Atributos:**
    - `Cliente`
    - `Plano`
    - `Consumido`
    - `Data/Hora`
    - `Mensagem`

### Chat

- **Atributos:**
    - `Id`
    - `Remetente`
    - `Destinatários` (pode ser uma lista de números)
    - `Mensagens`

---

## Requisitos do Sistema

1. **Cadastro de Clientes:** Todos os clientes devem ser previamente cadastrados.
2. **Envio para múltiplos usuários finais:**
    - Deve ser possível enviar mensagens para vários usuários finais.
3. **Web e Mobile:** O sistema deve permitir envio de mensagens via web e dispositivos móveis.
4. **Mensagens não em tempo real:** Não será um chat em tempo real, mas mensagens que podem ser enviadas, por exemplo,
   via WhatsApp.
5. **Usuários finais:**
    - **Cadastrados:** Possivelmente sim, para identificação dos destinatários, mas também pode funcionar apenas com
      números de telefone.
    - **Respostas:** Não há necessidade de os usuários finais responderem às mensagens.

---

## Perguntas e Fluxo de Pensamento

1. **Um cliente enviará para vários usuários finais?**
    - Acredito que sim, com base na frase:  
      _"Precisamos que seja possível via web ou mobile que os clientes enviem mensagens para seus usuários finais."_

2. **É via WebSocket?**
    - Acredito que não, considerando que as mensagens podem ser enviadas via WhatsApp. Não é um chat em tempo real, mas
      mensagens destinadas a usuários finais.

3. **Os usuários finais são cadastrados?**
    - Acredito que sim, pois precisamos saber quem são os destinatários das mensagens.
    - **Alternativa:** E se apenas informarmos os números de telefone sem cadastro prévio?

4. **O usuário final pode responder à mensagem?**
    - Acredito que não, pois não foi mencionado que os usuários finais devem responder às mensagens.

---

## Funcionalidades do Financeiro

- Incluir créditos para um cliente.
- Consultar saldo de um cliente.
- Alterar o limite de um cliente.
- Alterar o plano de um cliente.
- Visualizar dados de um cliente.

---

## Domínios Principais

1. **Cadastro de Cliente.**
2. **Cadastro de Mensagens.**
3. **Cadastro de Usuários Finais (Destinatários).**
4. **Gerenciamento de Chats.**
