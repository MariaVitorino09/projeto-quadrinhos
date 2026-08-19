# ⚡ Gestão de Personagens (HQ API)

Sistema web completo para cadastro e gerenciamento de personagens de quadrinhos, composto por uma API REST desenvolvida em Java com Spring Boot e uma interface web interativa construída com HTML5, CSS3 e JavaScript (Vanilla).

---

## 🛠️ Tecnologias Utilizadas

- **Back-end:** Java 17+, Spring Boot, Spring Data JPA, Maven
- **Front-end:** HTML5, CSS3, JavaScript (Fetch API)
- **Banco de Dados:** H2 Database / MySQL / PostgreSQL

---

## 📜 Regras de Negócio Implementadas

1. **Codinome Único:** Bloqueia o cadastro ou atualização de dois personagens com o mesmo codinome na base de dados.
2. **Nível de Poder:** O Nível de Ameaça/Poder deve estar obrigatoriamente entre **1 e 100**.
3. **Poder Mínimo para Vilões:** Personagens com alinhamento `VILAO` não podem ter um Nível de Poder/Ameaça inferior a **50**.

---

## 📌 Endpoints da API

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/personagens` | Retorna a lista completa de personagens |
| `GET` | `/personagens/{id}` | Busca os detalhes de um personagem específico por ID |
| `POST` | `/personagens` | Cadastra um novo personagem validando as regras de negócio |
| `PUT` | `/personagens/{id}` | Atualiza as informações de um personagem existente |
| `DELETE` | `/personagens/{id}` | Remove um personagem do banco de dados |

---

## 🚀 Como Executar o Projeto

### 1. Pré-requisitos
- Java JDK 17 ou superior
- Maven instalado (ou utilizar o wrapper `./mvnw`)
- Extensão **Live Server** no VS Code (ou navegador web para executar o front-end)

### 2. Rodando o Back-end (Spring Boot)
```bash
# Clone o repositório
git clone [https://github.com/SeuUsuario/seu-repositorio.git](https://github.com/SeuUsuario/seu-repositorio.git)

# Acesse a pasta do projeto
cd seu-repositorio

# Execute a aplicação
./mvnw spring-boot:run
