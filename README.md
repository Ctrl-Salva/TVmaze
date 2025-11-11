# 🎬 TVMaze Catalog Manager

Sistema de gerenciamento de séries desenvolvido em **Java com Spring Boot**, que consome dados reais da API pública **TVMaze**, armazena localmente, permite operações CRUD completas e demonstra o uso de **Programação Orientada a Objetos (POO)** e **estruturas de dados**.

---

## 🚀 Como executar o projeto

### ✅ Opção 1: Executar diretamente o arquivo principal
Execute o arquivo:

```bash
TvmazeApplication.java
```

### 🧩 Opção 2: Rodar com Maven
No terminal, dentro do diretório do projeto, execute:

```bash
mvn spring-boot:run
```

---

## 🌐 Endpoints principais

### 🔹 Interface Web
Acesse a aplicação em:

👉 [http://localhost:8080](http://localhost:8080)

---

### 🔹 Swagger UI (para enviar requisições JSON)
Interface interativa para testar os endpoints REST:

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

### 🔹 Banco de Dados (H2 em memória)
Console de administração do banco de dados H2:

👉 [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

**Configurações de conexão:**
```
URL: jdbc:h2:mem:testdb
Usuário: sa
Senha: (vazia)
```

---

## 🧱 Tecnologias utilizadas

- ☕ **Java 17+**
- ⚙️ **Spring Boot**
- 🌐 **Spring Web**
- 🗄️ **Spring Data JPA**
- 🧮 **H2 Database**
- 🐬 **MySQL**
- 📘 **Swagger / OpenAPI**
- 🔗 **Gson**
- 🧰 **Maven**

---

## 📚 Observações

- O banco de dados é **em memória**, portanto os dados são apagados ao encerrar a aplicação.  
- O **Swagger** é ideal para testar requisições e visualizar os modelos JSON utilizados pela API.

---

💡 *Desenvolvido com Java + Spring Boot.*
