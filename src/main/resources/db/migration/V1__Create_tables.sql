-- Criação das tabelas principais do sistema de restaurante

-- Tabela de usuários
<<<<<<< HEAD:src/main/resources/db/migration/V1__Create_tables.sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
=======
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c:src/main/resources/db/migration/V1__init.sql
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de roles/perfis
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Associação entre usuários e roles (muitos-para-muitos)
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tabela de clientes
<<<<<<< HEAD:src/main/resources/db/migration/V1__Create_tables.sql
CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
=======
CREATE TABLE IF NOT EXISTS clients (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c:src/main/resources/db/migration/V1__init.sql
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(255)
);

<<<<<<< HEAD:src/main/resources/db/migration/V1__Create_tables.sql
-- Tabela de pratos (dishes)
CREATE TABLE dishes (
    id BIGSERIAL PRIMARY KEY,
=======
-- Tabela de pratos
CREATE TABLE IF NOT EXISTS dishes (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c:src/main/resources/db/migration/V1__init.sql
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price NUMERIC(10, 2) NOT NULL,
    category VARCHAR(100)
);

<<<<<<< HEAD:src/main/resources/db/migration/V1__Create_tables.sql
-- Tabela de pedidos (orders)
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
=======
-- Tabela de pedidos
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c:src/main/resources/db/migration/V1__init.sql
    client_id BIGINT NOT NULL,
    total NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_orders_client FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

<<<<<<< HEAD:src/main/resources/db/migration/V1__Create_tables.sql
-- Tabela associativa entre pedidos e pratos (many-to-many)
CREATE TABLE order_dishes (
=======
-- Tabela associativa entre pedidos e pratos
CREATE TABLE IF NOT EXISTS order_dishes (
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c:src/main/resources/db/migration/V1__init.sql
    order_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, dish_id),
    CONSTRAINT fk_order_dishes_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_dishes_dish FOREIGN KEY (dish_id) REFERENCES dishes(id) ON DELETE CASCADE
);

<<<<<<< HEAD:src/main/resources/db/migration/V1__Create_tables.sql
-- Tabela de produtos (caso tenha separação de produtos e pratos)
CREATE TABLE produtos (
    id BIGSERIAL PRIMARY KEY,
=======
-- Tabela de produtos (opcional, caso use no futuro)
CREATE TABLE IF NOT EXISTS produtos (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c:src/main/resources/db/migration/V1__init.sql
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    preco NUMERIC(10, 2) NOT NULL,
    categoria VARCHAR(100),
    disponivel BOOLEAN NOT NULL DEFAULT TRUE
);

<<<<<<< HEAD:src/main/resources/db/migration/V1__Create_tables.sql
-- Índices para otimização
CREATE INDEX idx_clients_email ON clients(email);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_dishes_category ON dishes(category);
CREATE INDEX idx_orders_status ON orders(status);
=======
-- Índices para performance
CREATE INDEX IF NOT EXISTS idx_clients_email ON clients(email);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_dishes_category ON dishes(category);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status);

-- Inserção de roles padrão
INSERT INTO roles (name)
SELECT 'ADMIN' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');

INSERT INTO roles (name)
SELECT 'USER' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'USER');
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c:src/main/resources/db/migration/V1__init.sql
