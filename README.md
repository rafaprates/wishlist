# Wishlist
Implementa um pequeno conjunto de funcinalidades de uma Lista de Desejos de um e-commence utilizando os princípos de Arquitetura Limpa.

## Testes
A aplicação possui um total de 18 testes que têm como objetivo testar e descrever as funcionalidades do sistema.

Esses testes foram nomeados de forma descritiva para facilitar a compreensão das funcionalidades que estão sendo testadas.

Por meio desses testes, é possível ter uma visão geral do comportamento da aplicação.
## Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST   | `/wishlist/{userId}/products`| Adiciona um produto na Wishlist do cliente |
| DELETE | `/wishlist/{userId}/products/{productId}`| Remove um produto da Wishlist do cliente |
| GET | `/wishlist/{userId}/products` | Consulta todos os producots da Wishlist do cliente |
| GET | `/wishlist/{userId}/products?productId={critério}` | Filtra a lista de producots do cliente em busca de um produto |

## Pré-requisitos

Antes de rodar a aplicação, é necessário ter as seguintes ferramentas instaladas:

- Docker
- Java Development Kit (JDK) 17+
- Maven

## Rodando a aplicação

1. Clone o repositório 
```bash 
git clone https://github.com/rafaprates/wishlist.git
```

2. Navegue até a pasta criada
```bash
cd wishlist
```

3. Incialize os containers
```bash
docker compose up -d
```

4. Compile a aplicação utilizando o Maven
```bash
mvn clean install
```

5. Inicialize a aplicação
```bash
mvn spring-boot:run
```
