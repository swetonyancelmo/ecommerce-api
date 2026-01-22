# 🎯 Ecommerce API - Java + Spring Boot

![Java](https://img.shields.io/badge/Java-21+-red?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/%20Docker-gray?logo=docker)
![Lombok](https://img.shields.io/badge/Lombok-Reduce%20Boilerplate-pink?logo=lombok)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

> **Uma API REST para Gerenciamento de um Ecommerce**

### 🛠️ **Tecnologias & Arquitetura**
- **Java 21** – Linguagem moderna com recursos avançados e alto desempenho
- **Spring Boot** – Framework robusto e produtivo para criação de APIs REST
- **Spring Data JPA** – Persistência de dados simplificada e integrada ao banco
- **Spring Validation** – Validação automática e padronizada dos dados de entrada
- **Spring Security** – Camada de segurança para autenticação e autorização
- **JWT (JSON Web Token)** – Autenticação stateless baseada em tokens, garantindo segurança e escalabilidade
- **Swagger / OpenAPI** – Documentação interativa da API, facilitando testes e integração com o back-end
- **Lombok** – Redução significativa de código boilerplate, aumentando a produtividade
- **PostgreSQL** – Banco de dados relacional principal para ambiente de produção
- **Maven** – Gerenciamento de dependências e automação do build do projeto

### 🏗️ **Arquitetura Limpa**
- **Controller Layer** - Endpoints REST bem estruturados
- **Service Layer** - Lógica de negócio centralizada
- **Repository Layer** - Acesso a dados abstraído
- **DTO Pattern** - Transferência de dados tipada e segura
- **Exception Handling** - Tratamento global de erros
- **Validation** - Validação automática de entrada

## 🔌 Endpoints da API

### 📖 **POST** `/auth/register`
Faz o cadastro de um novo usuário.

**Resposta:**
```json
  {
    "Usuário registrado com sucesso."
  }
```
<hr/>

### 🔍 **POST** `/auth/login`
Realiza o login do usuário e retorna Token.

**Resposta:**
```json
{
  "token": "..."
}
```
<hr/>

### ➕ **POST** `/api/products`
Cadastra um novo Produto

**Body:**
```json
{
  "nameProduct": "Malbec",
  "description": "Perfume Masculino",
  "price": 200.00,
  "quantity": 10
}
```
<hr/>

### ✏️ **GET** `/api/products`
Retorna todos os Produtos cadastrados.

**Body:**
```json
[  
  {
    "id": "1",
    "nameProduct": "Malbec",
    "price": 200.00,
    "quantity": 10
  }
]
```

## 🛠️ Configuração e Instalação

### Pré-requisitos
- ☕ **Java 21+**
- 🍃 **Maven 3.6+**
- 🐘 **Docker** 

### 🚀 Executando a Aplicação

1. **Clone o repositório**
```bash
git clone https://github.com/swetonyancelmo/ecommerce-api
cd ecommerce-api
```

2. **Instale as dependências**
```bash
mvn clean install
```

3. **Execute a aplicação**
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 🗄️ Banco de Dados

#### Docker
Para usar o Docker, configure o docker-compose.yml e as propriedades:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=postgres
spring.datasource.password=ecommerce
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### Usando Postman/Insomnia
Importe a collection disponível em `/docs/postman-collection.json`

## 📊 Tratamento de Erros

A API possui tratamento global de erros com respostas padronizadas:

### Erro 404 - Recurso Não Encontrado
```json
{
  "message": "Recurso não encontrado",
  "errors": ["Recurso com o ID 999 não encontrada"],
  "status": 404
}
```

### Erro 400 - Validação
```json
{
  "message": "Validation failed",
  "errors": ["O nome do produto é obrigatório."],
  "status": 400
}
```

## 🏆 Pontos Fortes da API

### 🎯 **Experiência do Desenvolvedor**
- **Documentação Clara** - README detalhado e exemplos práticos
- **Validação Automática** - Validação de entrada com mensagens claras
- **Tratamento de Erros** - Respostas padronizadas e informativas
- **Código Limpo** - Arquitetura bem estruturada e fácil manutenção

### ⚡ **Performance & Escalabilidade**
- **Spring Boot** - Framework otimizado para produção
- **JPA/Hibernate** - ORM eficiente com cache automático
- **Connection Pooling** - Gerenciamento otimizado de conexões
- **Lazy Loading** - Carregamento sob demanda de relacionamentos

### 🔒 **Segurança & Confiabilidade**
- **Validação Rigorosa** - Validação de entrada em múltiplas camadas
- **Exception Handling** - Tratamento global de exceções
- **Transações ACID** - Consistência de dados garantida
- **Prepared Statements** - Proteção contra SQL Injection

### 🛠️ **Manutenibilidade**
- **Arquitetura em Camadas** - Separação clara de responsabilidades
- **DTO Pattern** - Contratos bem definidos entre camadas
- **Lombok** - Redução de código boilerplate
- **Convenções Spring** - Padrões estabelecidos e reconhecidos

### 📈 **Extensibilidade**
- **Modular Design** - Fácil adição de novas funcionalidades
- **Interface-based** - Desacoplamento para testes e extensões
- **Configuration Properties** - Configuração flexível por ambiente
- **Plugin Architecture** - Suporte a extensões via Spring Boot Starters

## 🗂️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/jobtrack/api/
│   │   ├── config/              # Configurações gerais
│   │   ├── controllers/         # Controladores REST
│   │   ├── dtos/                # Data Transfer Objects
│   │   ├── exceptions/          # Tratamento de exceções
│   │   ├── mapper/              # Mapiador de DTO e entidade
│   │   ├── model/               # Entidades JPA
│   │   │  ├── enums/            # Enums
│   │   ├── repository/          # Repositórios de dados
│   │   ├── security/            # Camada de segurança
│   │   ├── services/             # Lógica de negócio
│   │   └── EcommerceDevApplication.java
│   └── resources/
│       ├── application.properties
│       └── static/
└── test/                        # Testes unitários e integração
```

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

**Swetony Ancelmo**
- GitHub: [Swetony Ancelmo](https://github.com/swetonyancelmo)
- LinkedIn: [Swetony Ancelmo](https://www.linkedin.com/in/swetony-ancelmo)

## 🙏 Agradecimentos

- Meus pais
- Spring Boot Team pela excelente framework
- Comunidade Java pelo ecossistema robusto
- Todos os contribuidores que ajudaram a melhorar este projeto

---

<div align="center">

**⭐ Se este projeto foi útil para você, considere dar uma estrela! ⭐**

Made with ❤️ and ☕

</div>
