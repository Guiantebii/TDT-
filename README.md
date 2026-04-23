# 📚 Sistema de Gestão de Tablets e chips

Sistema full-stack para gerenciamento de tablets e chips, desenvolvido com
**Spring Boot (backend)** e **HTML, CSS e JavaScript (frontend)**.

O sistema permite realizar operações completas de CRUD (Create, Read,
Update, Delete), com interface web integrada à API REST.

------------------------------------------------------------------------

## 🚀 Funcionalidades

-   ✔ Listar alunos
-   ✔ Cadastrar novo aluno
-   ✔ Visualizar detalhes do aluno
-   ✔ Editar informações do aluno
-   ✔ Excluir aluno

------------------------------------------------------------------------

## 🛠 Tecnologias Utilizadas

### 🔹 Backend

-   Java 21
-   Spring Boot
-   Spring Data JPA
-   Hibernate
-   PostgreSQL
-   Docker

### 🔹 Frontend

-   HTML5
-   CSS3
-   Bootstrap
-   JavaScript 

------------------------------------------------------------------------

## 📁 Estrutura do Projeto

    TDT-Backend/
    └── src/main/java/com/example/tablets_chips/
    ├── config/
    │ └── CorsConfig.java
    │
    ├── controller/
    │ └── AlunoController.java
    │
    ├── dto/
    │ ├── AlunoRequestDTO.java
    │ └── AlunoResponseDTO.java
    │
    ├── model/
    │ ├── Aluno.java
    │ ├── Chip.java
    │ ├── Tablet.java
    │ ├── Devolucao.java
    │ ├── Manutencao.java
    │ └── TabletsChips.java
    │
    ├── repository/
    │ ├── AlunoRepository.java
    │ └── ChipRepository.java
    │
    ├── service/
    │ └── AlunoService.java
    │
    └── TabletsChipsApplication.java

    TDT-Frontend/
    ├── pages/
    │   ├── alunos.html
    │   ├── aluno-create.html
    │   ├── aluno-edit.html
    │   └── aluno-view.html
    │
    ├── js/
    │   ├── alunos.js
    │   ├── aluno-create.js
    │   ├── aluno-edit.js
    │   └── aluno-view.js
    │
    └── css/
        └── style.css

------------------------------------------------------------------------

## ⚙️ Como Executar o Projeto

### 🔹 1. Subir o banco de dados com Docker

``` bash
docker run --name postgres-spring \
-e POSTGRES_DB=tablets_chips \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-p 5432:5432 \
-d postgres
```

------------------------------------------------------------------------

### 🔹 2. Executar o Backend

``` bash
./mvnw spring-boot:run
```

Ou rodar diretamente pela IDE (IntelliJ).

------------------------------------------------------------------------

### 🔹 3. Executar o Frontend

Abrir o arquivo:

    TDT-Frontend/pages/alunos.html

Recomendado usar a extensão **Live Server** no VS Code.

------------------------------------------------------------------------

## 🔗 API

Base URL:

    http://localhost:8080/alunos

### Endpoints principais

 | Método | Endpoint        | Descrição            |
|--------|----------------|----------------------|
| GET    | /alunos        | Listar todos alunos |
| GET    | /alunos/{id}   | Buscar por ID       |
| POST   | /alunos        | Criar aluno         |
| PUT    | /alunos/{id}   | Atualizar aluno     |
| DELETE | /alunos/{id}   | Deletar aluno       |

------------------------------------------------------------------------

## 🧠 Arquitetura

O projeto segue uma arquitetura em camadas:

-   Controller → expõe endpoints REST\
-   Service → lógica de negócio\
-   Repository → acesso ao banco\
-   DTOs → comunicação com frontend
