# 📚 Sistema de Gestão de Tablets, Chips e Alunos

Sistema full-stack para gerenciamento de **alunos, tablets e chips**, desenvolvido com
**Spring Boot (backend)** e **HTML, CSS e JavaScript (frontend)**.

O sistema permite operações completas de CRUD e o **vínculo entre dispositivos e alunos**, simulando um ambiente real de gestão de equipamentos.

---

## 🚀 Funcionalidades

### 👤 Alunos

* ✔ Listar alunos
* ✔ Cadastrar novo aluno
* ✔ Visualizar detalhes do aluno
* ✔ Editar informações do aluno
* ✔ Excluir aluno
* ✔ Associar aluno a um tablet

### 📱 Tablets

* ✔ Listar tablets
* ✔ Cadastrar tablet (IMEI e número de série)
* ✔ Editar tablet
* ✔ Excluir tablet
* ✔ Vincular chip ao tablet

### 📶 Chips

* ✔ Listar chips
* ✔ Cadastrar chip (ICCID, status, PIN, PUK)
* ✔ Editar chip
* ✔ Visualizar detalhes do chip
* ✔ Excluir chip

---

## 🔗 Relacionamentos

* Um **aluno** possui um **tablet**
* Um **tablet** pode possuir um **chip**
* Relacionamento intermediário via `tablets_chips`

---

## 🛠 Tecnologias Utilizadas

### 🔹 Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Docker

### 🔹 Frontend

* HTML5
* CSS3
* Bootstrap 5
* JavaScript (ES Modules)

---

## 📁 Estrutura do Projeto

```
TDT-Backend/
└── src/main/java/com/example/tablets_chips/
    ├── config/
    │ └── CorsConfig.java
    │
    ├── controller/
    │ ├── AlunoController.java
    │ ├── TabletController.java
    │ └── ChipController.java
    │
    ├── dto/
    │ ├── AlunoRequestDTO.java
    │ ├── AlunoResponseDTO.java
    │ ├── TabletRequestDTO.java
    │ ├── TabletResponseDTO.java
    │ ├── ChipRequestDTO.java
    │ ├── ChipResponseDTO.java
    │ └── VincularChipDTO.java
    │
    ├── model/
    │ ├── Aluno.java
    │ ├── Tablet.java
    │ ├── Chip.java
    │ ├── TabletsChips.java
    │ ├── Devolucao.java
    │ └── Manutencao.java
    │
    ├── repository/
    │ ├── AlunoRepository.java
    │ ├── TabletRepository.java
    │ ├── ChipRepository.java
    │ └── TabletsChipsRepository.java
    │
    ├── service/
    │ ├── AlunoService.java
    │ ├── TabletService.java
    │ └── ChipService.java
    │
    └── TabletsChipsApplication.java

TDT-Frontend/
├── pages/
│   ├── alunos.html
│   ├── aluno-create.html
│   ├── aluno-edit.html
│   ├── aluno-view.html
│   ├── tablets.html
│   ├── tablet-create.html
│   ├── tablet-edit.html
│   ├── chips.html
│   ├── chip-create.html
│   ├── chip-edit.html
│   └── chip-view.html
│
├── js/
│   ├── alunos.js
│   ├── aluno-create.js
│   ├── aluno-edit.js
│   ├── aluno-view.js
│   ├── tablets.js
│   ├── tablet-create.js
│   ├── tablet-edit.js
│   ├── chips.js
│   ├── chip-create.js
│   ├── chip-edit.js
│   └── chip-view.js
│
└── css/
    └── style.css
```

---

## ⚙️ Como Executar o Projeto

### 🔹 1. Subir o banco de dados com Docker

```bash
docker run --name postgres-spring \
-e POSTGRES_DB=tablets_chips \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-p 5432:5432 \
-d postgres
```

---

### 🔹 2. Executar o Backend

```bash
./mvnw spring-boot:run
```

Ou rodar diretamente pela IDE (IntelliJ).

---

### 🔹 3. Executar o Frontend

Abrir o arquivo:

```
TDT-Frontend/pages/alunos.html
```

Recomendado usar a extensão **Live Server** no VS Code.

---

## 🔗 API

### Base URLs

```
/alunos
/tablets
/chips
```

### Exemplos de endpoints

| Método | Endpoint                    | Descrição               |
| ------ | --------------------------- | ----------------------- |
| GET    | /alunos                     | Listar alunos           |
| POST   | /alunos                     | Criar aluno             |
| PUT    | /alunos/{id}                | Atualizar aluno         |
| DELETE | /alunos/{id}                | Deletar aluno           |
| POST   | /tablets/{id}/vincular-chip | Vincular chip ao tablet |

---

## 🧠 Arquitetura

O projeto segue arquitetura em camadas:

* **Controller** → expõe endpoints REST
* **Service** → lógica de negócio
* **Repository** → acesso ao banco
* **DTOs** → comunicação com frontend

---


