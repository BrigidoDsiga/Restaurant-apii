# 🍽️ Restaurant API

Projeto completo de uma API RESTful para gerenciamento de um restaurante. Desenvolvido com **Java 17**, **Spring Boot**, **PostgreSQL**, **JWT**, e integração com **Docker**. O sistema oferece funcionalidades de autenticação, cadastro de clientes, gerenciamento de pratos e pedidos.

---

## 📦 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Docker e Docker Compose
- Flyway (migração de banco)
- Swagger/OpenAPI
- MapStruct (mapeamento DTO <-> Entity)
- JUnit + Mockito (testes)

---

## 📁 Estrutura do Projeto

- restaurant-api/
- src/
- main/java/com/example/restaurant/
- config/ # Configurações (segurança, CORS, JWT, Swagger)
- controller/ # REST Controllers (entrada HTTP)
- dto/ # DTOs de entrada/saída
- entity/ # Entidades JPA (map. banco)
- repository/ # Repositórios Spring Data JPA
- service/ # Lógica de negócios
- mapper/ # MapStruct DTO <-> Entity
- exception/ # Tratamento global de exceções
- RestaurantApiApplication.java
- resources/
- application.yml # Configurações do projeto
- db/migration/ # Scripts do Flyway
- test/ # Testes
- java/ # Testes de unidade e integração
- resources/application-test.yml
- Dockerfile # Imagem Docker da aplicação
- docker-compose.yml # Subida do app + banco de dados
- README.md # Este arquivo


yaml

## 🚀 Como Executar o Projeto

### Pré-requisitos

- [Java 17+](https://adoptium.net/)
- [Docker](https://www.docker.com/)
- [Maven](https://maven.apache.org/)

### Subir com Docker Compose

```bash
docker-compose up --build

O serviço estará disponível em: http://localhost:8080

🔑 Autenticação
A API utiliza autenticação JWT. Após realizar login, será gerado um token que deve ser enviado no header Authorization em todas as requisições protegidas:

Authorization: Bearer SEU_TOKEN

🛠️ Endpoints Principais
Autenticação:

POST /api/auth/login

POST /api/auth/register

Clientes:

GET /api/clients

POST /api/clients

PUT /api/clients/{id}

DELETE /api/clients/{id}

Pratos:

GET /api/dishes

POST /api/dishes

PUT /api/dishes/{id}

DELETE /api/dishes/{id}

Pedidos:

GET /api/orders

POST /api/orders

GET /api/orders/{id}

🧪 Testes
Execute os testes com:

./mvnw test

📄 Scripts Flyway
Scripts de migração do banco estão em src/main/resources/db/migration. Exemplo:

V1__init.sql: Criação inicial das tabelas

V2__add_roles.sql: Adição da tabela de roles e permissões


📚 Swagger (Documentação da API)
Acesse a documentação interativa da API via Swagger em:

http://localhost:8080/swagger-ui.html

👨‍💻 Autor
Brígido - Desenvolvedor Java Back-end

