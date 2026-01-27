# Ecommerce API — Java + Spring Boot

![Java](https://img.shields.io/badge/Java-21+-red?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Container-gray?logo=docker)
![Lombok](https://img.shields.io/badge/Lombok-Reduce%20Boilerplate-pink?logo=lombok)

> API REST para gerenciamento de um e-commerce minimalista, construída com Java 21 e Spring Boot.

---

Sumário
- Visão geral
- Tecnologias e arquitetura
- Recursos implementados
- Como rodar localmente (passo a passo)
- Variáveis de ambiente e configuração
- Endpoints principais com exemplos (request/response)
- Modelagem e decisões de design
- Segurança e validação
- Testes
- Como avaliar o projeto
- Deploy e Docker
- Contribuição, licença e contato

---

Visão geral

Este projeto implementa uma API REST de um e-commerce com as operações essenciais: gerenciamento de produtos, movimentação de estoque, autenticação via JWT, criação de pedidos (Order) e histórico de movimentos de estoque. O objetivo é demonstrar boas práticas de arquitetura em camadas, validação, tratamento centralizado de erros, segurança e persistência com Spring Data JPA.


---

Principais tecnologias
- Java 21
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- PostgreSQL (suporte a Docker)
- Maven
- Lombok
- Swagger/OpenAPI

---

Recursos implementados
- Autenticação e autorização com JWT
- Endpoints para: registro/login, CRUD de produtos, movimentações de estoque, criação de pedidos
- DTOs para entrada/saída (camada de apresentação desacoplada da entidade)
- Validação com Jakarta Validation (JSR-380)
- Tratamento global de exceções (mensagens padronizadas)
- Mapeadores (mappers) para converter entidades ↔ DTOs
- Padrão de serviços e repositórios (camadas separadas)
- Respostas JSON customizadas para 401/403 (AuthenticationEntryPoint e AccessDeniedHandler)

---

Estrutura do projeto
```
src/
├── main/
│   ├── java/com/swetonyancelmo/ecommerce/
│   │   ├── config/           # Configurações (Security, Swagger)
│   │   ├── controllers/      # Endpoints REST
│   │   ├── dtos/             # Data Transfer Objects
│   │   ├── exceptions/       # Exceções customizadas e handler global
│   │   ├── mapper/           # Conversores entity <-> dto
│   │   ├── models/           # Entidades JPA
│   │   ├── repository/       # Repositórios Spring Data
│   │   ├── security/         # Token service, filters, handlers
│   │   ├── services/         # Lógica de negócio
│   │   └── EcommerceDevApplication.java
│   └── resources/
│       └── application.properties
└── test/                    # Testes unitários e integração
```

---

Como rodar o projeto (modo rápido)

Pré-requisitos
- JDK 21 instalado e com JAVA_HOME configurado
- Maven 3.6+
- Docker (opcional para PostgreSQL)

1) Clone o repositório
```bash
git clone https://github.com/swetonyancelmo/ecommerce-api.git
cd ecommerce-api
```

2) Configurar variáveis de ambiente (exemplo local .env ou `application.properties`)
- Abaixo há uma seção com as propriedades essenciais a configurar.

3) Rodar com Maven (modo sem Docker)
```bash
mvn clean install
mvn spring-boot:run
```
A aplicação estará disponível em: http://localhost:8080

4) Rodar com Docker (PostgreSQL)
- Configurar `docker-compose.yml` (existe um arquivo no projeto) e executar:
```bash
docker compose up -d
mvn spring-boot:run
```

Variáveis de ambiente / application.properties (exemplos)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=postgres
spring.datasource.password=ecommerce
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
jwt.secret=uma-chave-secreta-exemplo-para-dev
jwt.expiration-ms=3600000
server.port=8080
```
Ajuste valores em ambientes de produção e utilize secrets managers.

---

Endpoints principais (resumo rápido)

Autenticação
- POST /auth/register — registra um usuário
- POST /auth/login — retorna token JWT

Produtos
- GET /api/products — lista produtos
- POST /api/products — cria produto (autenticado)
- GET /api/products/{id} — busca produto por id
- PUT /api/products/{id} — atualiza produto (autenticado)
- DELETE /api/products/{id} — remove produto (autenticado)

Movimentação de Estoque
- POST /api/stock-movements — registra compra/venda (ajusta estoque)

Pedidos
- POST /api/orders — cria pedido a partir de `OrderCreateDTO` (lista de items: productId + quantity)
- GET /api/orders/{id} — consulta pedido (retorna `OrderResponseDTO`)

Documentação interativa
- Swagger UI: /swagger-ui/index.html (se habilitado) — acessível sem autenticação por configuração atual

Exemplos de payload
- OrderCreateDTO (request)
```json
{
  "items": [
    {"productId": 1, "quantity": 2},
    {"productId": 5, "quantity": 1}
  ]
}
```

- OrderResponseDTO (response)
```json
{
  "id": 10,
  "items": [
    {"productId":1, "productName":"Produto A", "unitPrice": 50.00, "quantity": 2, "subtotal": 100.00}
  ],
  "totalPrice": 100.00
}
```

---

Design e decisões arquiteturais
- Arquitetura em camadas (Controller → Service → Repository): facilita testes e manutenção.
- DTOs para separar contrato HTTP das entidades: evita vazamento de dados e facilita evolução de API.
- Mapeadores manuais (ou MapStruct): mantêm ponto único de conversão e documentam campos retornados.
- Uso de transactions nas operações críticas (criação de pedido e ajuste de estoque) para manter consistência ACID.
- Preço do produto considerado fonte de verdade no servidor (não aceita preço do cliente) — evita fraude.

Tratamento de erros
- Exceções customizadas: `ResourceNotFoundException`, `BusinessException` (retornam 404 e 400 respectivamente)
- Handler global padroniza resposta:
```json
{
  "timestamp": "2026-01-01T...",
  "status": 400,
  "message": "Validation failed",
  "errors": ["O nome do produto é obrigatório"]
}
```
- 401 e 403 são tratados por `AuthenticationEntryPoint` e `AccessDeniedHandler` que retornam JSON legível.

Segurança
- Autenticação com JWT (stateless)
- Endpoints públicos: /auth/register, /auth/login, Swagger docs
- Endpoints protegidos: demais rotas (role-based via @PreAuthorize quando necessário)
- Respostas JSON customizadas para 401/403 (úteis para APIs públicas e front-end)

Validações
- DTOs anotados com Jakarta Validation (ex.: @NotNull, @NotEmpty, @Positive)
- Entidades também têm anotações de validação essenciais
- Serviço revalida regras críticas (ex.: quantidade > 0, estoque suficiente)

---

Testes
- Estrutura de testes em `src/test/java` (unitários e de integração).
- Recomendo rodar:
```bash
mvn test
```
- Exemplos a adicionar para avaliação técnica:
  - Teste unitário de `OrderService#create` cobrindo: criação correta, estoque insuficiente, quantidade inválida.
  - Teste de integração com H2 ou PostgreSQL em container para fluxo end-to-end.

---

Como avaliar rapidamente
- Código limpo e legível: abrir `services/OrderService.java` e `controllers/OrderController.java` para ver separação de responsabilidades.
- Segurança: checar `config/SecurityConfig.java` e `security` para token handling e handlers de 401/403.
- Mapeamento e DTOs: verificar `mapper/` e `dtos/` para consistência de contratos.
- Banco: checar `application.properties` e `docker-compose.yml` para ver estratégia de persistência.
- Rodar o projeto localmente e executar um fluxo simples (registrar usuário → login → criar produto → criar pedido) para validar comportamento.

---

Deploy
- Opções:
  - Docker Compose (db + app)
  - Build com `mvn package` e deploy em qualquer container Java (JAR)

Exemplo Docker (simplificado):
```yaml
version: '3.8'
services:
  db:
    image: postgres:15
    environment:
      POSTGRES_DB: ecommerce_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: ecommerce
    ports:
      - "5432:5432"
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/ecommerce_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: ecommerce
    depends_on:
      - db
```

---

Contribuindo
1. Fork the project
2. Crie uma branch com a sua feature: `git checkout -b feature/{sua-feature}`
3. Faça commits claros e atômicos
4. Abra um PR com descrição e passos para testar

---

Licença
- MIT License — ver arquivo LICENSE no repositório.

---

Autor & Contato
- Swetony Ancelmo — https://github.com/swetonyancelmo
- LinkedIn: https://www.linkedin.com/in/swetony-ancelmo

---

Notas finais
- Este projeto foi pensado para demonstrar domínio em backend Java/Spring: DDD/lightweight layering, segurança JWT, validações, tratamento de exceções, mapeamento DTO/entidade e boas práticas de versionamento/CI.
- Se quiser uma demo ao vivo, posso fornecer passos rápidos para executar e validar os endpoints mais importantes.

---
