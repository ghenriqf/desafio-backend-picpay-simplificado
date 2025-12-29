# Desafio PicPay Simplificado

Bem-vindo ao repositório de resolução do **Desafio PicPay Simplificado**.
Esta aplicação é uma **API REST** que simula um sistema de pagamentos onde usuários podem realizar transferências entre si e para lojistas.

---

## Sobre o desafio

O objetivo é implementar uma plataforma de pagamentos com as seguintes regras de negócio:

* Existência de dois tipos de usuários: **Comuns** e **Lojistas**.
* Usuários **Comuns** podem enviar e receber dinheiro.
* **Lojistas** apenas recebem transferências, não podem enviar dinheiro.
* Antes de cada transferência, um serviço autorizador externo deve ser consultado.
* Após a transferência, uma notificação deve ser enviada aos envolvidos através de um serviço de terceiros.
* A operação de transferência é **transacional**, garantindo que, em caso de erro ou inconsistência, todas as alterações sejam revertidas e o saldo do remetente seja preservado.

---

## Endpoints da API

### 1. Cadastro de Usuários — `POST /usuarios`

Cria um novo usuário (Comum ou Lojista).

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

**Regras**:

* CPF e Email devem ser únicos.

---

### 2. Listar Usuários — `GET /usuarios`

Retorna todos os usuários cadastrados.

---

### 3. Realizar Transferência — `POST /transacao`

Efetua o envio de dinheiro entre carteiras.

```json
{
  "valor": 100.0,
  "remetenteId": 1,
  "destinatarioId": 2
}
```

**Possíveis respostas**:

* `200 OK` — Transação realizada com sucesso
* `403 Forbidden` — Tentativa de envio por Lojista ou falha na autorização externa
* `422 Unprocessable Entity` — Saldo insuficiente
* `404 Not Found` — Usuário remetente ou destinatário não encontrado

---

## Documentação da API (Swagger / OpenAPI)

A API possui documentação interativa gerada com **Swagger (Springdoc OpenAPI)**.

Após subir a aplicação, acesse:

* **Swagger UI**:
  `http://localhost:8080/swagger-ui.html`

* **OpenAPI JSON**:
  `http://localhost:8080/v3/api-docs`

A documentação permite:

* Visualizar todos os endpoints
* Testar requisições diretamente pelo navegador
* Ver modelos de request/response e códigos de erro

---

## Boas Práticas Adotadas

### 1. Tratamento de Exceções Centralizado

Uso de `@RestControllerAdvice` para padronizar respostas de erro com status HTTP semânticos.

### 2. Desacoplamento com DTOs e Mappers

As entidades não são expostas diretamente na camada de controle, facilitando manutenção e evolução da API.

### 3. Persistência e Integridade

* Spring Data JPA para abstração da camada de dados
* Restrições de unicidade (`@Column(unique = true)`) para CPF e Email
* Controle transacional garantindo consistência nas transferências

### 4. Testes Unitários

* Implementação de **testes unitários** para as regras de negócio
* Uso do **JUnit 5** para escrita e execução dos testes
* Uso do **Mockito** para mockar dependências externas (repositórios e serviços)
* Validação de cenários como:

  * Transferência com saldo insuficiente
  * Tentativa de transferência por usuário lojista
  * Falha no serviço autorizador externo
  * Transferência realizada com sucesso

Os testes garantem maior confiabilidade, facilitam refatorações e asseguram o correto funcionamento das regras de negócio.

---

## Tecnologias Utilizadas

* **Linguagem**: Java 17
* **Framework**: Spring Boot 3
* **Banco de Dados**: H2 (em memória) / MySQL (configurável)
* **Persistência**: Spring Data JPA / Hibernate
* **Documentação**: Swagger (Springdoc OpenAPI)
* **Produtividade**: Lombok
* **Comunicação Externa**: RestTemplate (Mock API)
* **Testes**: JUnit 5 e Mockito
* **Containerização**: Docker

---

## Como Executar

### Execução Local (Sem Docker)

#### Requisitos

* JDK 17+
* Maven

#### Passos

1. Clone o repositório:

```bash
git clone https://github.com/ghenriqf/desafio-backend-picpay-simplificado.git
```

2. Acesse o diretório:

```bash
cd desafio-backend-picpay-simplificado
```

3. Configure os serviços externos em `application.properties`:

```properties
authorize.service.url=https://util.devi.tools/api/v2/authorize
email.service.url=https://util.devi.tools/api/v1/notify
```

4. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

5. Acesse:

* API: `http://localhost:8080`
* Swagger: `http://localhost:8080/swagger-ui.html`

---

### Execução com Docker

O projeto possui um **Dockerfile** para facilitar a execução em ambiente containerizado.

#### Build da imagem

```bash
docker build -t picpay-simplificado .
```

#### Executar o container

```bash
docker run -p 8080:8080 picpay-simplificado
```

Após subir o container:

* API: `http://localhost:8080`
* Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## Autor

GitHub: [https://github.com/ghenriqf](https://github.com/ghenriqf)

---
