# Desafio PicPay Simplificado

Bem-vindo ao repositório de resolução do **Desafio PicPay Simplificado**. Esta aplicação é uma API REST que simula um sistema de pagamentos onde usuários podem realizar transferências entre si e para lojistas.

---

## Sobre o desafio

O objetivo é implementar uma plataforma de pagamentos com as seguintes regras de negócio:

* Existência de dois tipos de usuários: **Comuns** e **Lojistas**.
* Usuários **Comuns** podem enviar e receber dinheiro.
* **Lojistas** apenas recebem transferências, não podem enviar dinheiro.
* Antes de cada transferência, um serviço autorizador externo deve ser consultado.
* Após a transferência, uma notificação deve ser enviada aos envolvidos através de um serviço de terceiros.
* A operação de transferência é transacional, garantindo que, em caso de qualquer inconsistência ou erro durante o processo, todas as alterações sejam revertidas e o saldo do remetente seja preservado.
---

## Endpoints da API

### 1. Cadastro de Usuários: `POST /usuarios`

Cria um novo usuário (Comum ou Lojista) no sistema.

```json
{
  "nomeCompleto": "João Silva",
  "cpf": "12345678900",
  "email": "joao@exemplo.com",
  "senha": "password123",
  "saldo": 500.00,
  "tipo": "COMUM"
}

```

* **Regras**: CPF e Email devem ser únicos.

### 2. Listar Usuários: `GET /usuarios`

Retorna a lista de todos os usuários cadastrados.

### 3. Realizar Transferência: `POST /transacao`

Efetua o envio de dinheiro entre carteiras.

```json
{
  "valor": 100.0,
  "remetenteId": 1,
  "destinatarioId": 2
}

```

* **Respostas**:
* `200 OK`: Transação realizada com sucesso.
* `403 Forbidden`: Tentativa de envio por Lojista ou falha na autorização externa.
* `422 Unprocessable Entity`: Saldo insuficiente.
* `404 Not Found`: Usuário remetente ou destinatário não encontrado.



---

## Boas Práticas Adotadas

### 1. Tratamento de Exceções Centralizado

A aplicação utiliza um `GlobalExceptionHandler` com `@RestControllerAdvice`. Isso garante que erros como saldo insuficiente ou violação de integridade de dados retornem status HTTP semânticos e mensagens claras.

### 2. Desacoplamento com DTOs e Mappers

O uso de **Data Transfer Objects (DTOs)** e Mappers evita a exposição direta das entidades do banco de dados na camada de controle, facilitando a manutenção e evolução da API.

### 3. Persistência e Integridade

* Uso do **Spring Data JPA** para abstração da camada de dados.
* Configuração de `@Column(unique = true)` para garantir unicidade de CPF e Email.
* Lógica de transação manual (save/update) para garantir que o dinheiro só saia de uma conta se entrar na outra.

---

## Tecnologias Utilizadas

* **Linguagem**: Java 17
* **Framework**: Spring Boot 3
* **Banco de Dados**: H2 (Em memória) / MySQL (Configurável)
* **Persistência**: Spring Data JPA / Hibernate
* **Produtividade**: Lombok
* **Comunicação**: RestTemplate para chamadas de serviços externos (Mock API)

---

## Como Executar

### Requisitos

* **JDK 17** ou superior.
* **Maven** instalado.

### Instruções

1. Clone este repositório:

```bash
git clone https://github.com/ghenriqf/desafio-backend-picpay-simplificado.git

```

2. Acesse o diretório do projeto:

```bash
cd desafio-backend-picpay-simplificado

```

3. Configure as URLs dos serviços externos no arquivo `src/main/resources/application.properties`:

```properties
authorize.service.url=https://util.devi.tools/api/v2/authorize
email.service.url=https://util.devi.tools/api/v1/notify

```

4. Compile e execute a aplicação:

```bash
./mvnw spring-boot:run

```

5. Acesse a aplicação em `http://localhost:8080`.

---

Autor: **[ghenriqf](https://github.com/ghenriqf)**

---
