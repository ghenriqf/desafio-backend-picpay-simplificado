# Desafio Back-end PicPay Simplificado

Descrição do Projetote projeto é uma implementação de uma API RESTful inspirada no desafio técnico do PicPay. O objetivo é simular uma plataforma de pagamentos simplificada, onde usuários podem se cadastrar, possuir saldo em carteira e realizar transferências de dinheiro entre si, respeitando regras de negócio específicas.

O sistema foi desenvolvido com foco em **código limpo**, **boas práticas**, **separação de responsabilidades** e **clareza para uma futura entrevista técnica**.

---

## ## Tecnologias Utilizadas 17

* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* Lombok
* RestTemplate
* H2 / Banco relacional (configurável)

---

## E## Estrutura do Projetoeto segue uma organização em camadas:

* **controller** → Camada responsável pelos endpoints REST
* **service** → Regras de negócio e orquestração
* **domain** → Entidades JPA e enums de domínio
* **dto** → Objetos de entrada (Request) e saída (Response)
* **repository** → Acesso ao banco de dados
* **mapper** → Conversão entre entidades e DTOs
* **exceptions** → Exceções customizadas e handler global

--rio
O sist## Tipos de Usuáriode usuários:

* **COMUM**

  * Pode enviar e receber transferências
* **LOJISTA**

  * Pode apenas receber transferências

Essa regra é validada durante a criação da transação.

---

## Regras de uem: nome com## Regras de Negócio Implementadaspo

* CPF e e-mail são únicos no sistema
* Apenas usuários do tipo **COMUM** podem realizar transferências
* O usuário deve possuir saldo suficiente para transferir
* A transferência é validada por um **serviço autorizador externo**
* A transação é registrada com data e hora
* O saldo do remetente é debitado e o do destinatário é creditado
* Após a transação, ambos recebem uma notificação
* Exceções são tratadas de forma centralizada

---

## Integrações Externas

### Serviço Autorizador:

```
GET https://util.devi.tools/api/v2/authorize
```

* A transação só é concluída se o retorno for autorizado

### Serviço de Notificação

* Endpoint mock utilizado:

  ```
  POST https://util.devi.tools/api/v1/notify
  ```
* Em caso de indisponibilidade, uma exceção é lançada

---

## Endpoints Disponíveis

### Criar Usuário

**POST /usuarios**

Exem "nomeCompleto": "João da 2345678900",
"email": "[joao@email.com](mailto:joao@email.com)",
"senha": "123456",
"saldo": 1000,
"tipo": "COMUM"
}

````

---

### Listar Usuários
**GET /usuarios**

---

### Criar Transação
**POST /transacao**

Exemplo de request:
```0,
  "remetenteId": 1,
  "destinatarioId": 2ponse:
```json
{
  "id": 1,
  "valor": 100.0,
  "remetente": { /* dados do usuário */ },
  "destinatario": { /* dados do usuário */ },
  "dataHora": "2025-01-01T10:00:00"
}
````

---

## Tratamento de Erros

O projeto possui um **GlobalExceptionHandler** que padroniza as respostas de erro.

Exceções tratadas:

* Usuário não encontradonte (422)
* Transação não autorizada (403)
* Serviço externo indisponível (503)
* Violação de integridade (usuário já cadastrado) (400)

Todas retornam uma resposta estruturada com mensagem, status e timestamp.

---

## Como Executar o Projeto

1. Clone o repositório
2. Configure o `application.properties` com as URLs dos serviços externos
3. Execute a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```
4. A API estará disponível em:

   ```
   http://localhost:8080
   ```

---
