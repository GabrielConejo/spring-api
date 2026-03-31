# Spring Boot API - Task Management

Este é um projeto de API Spring Boot para gerenciamento de tarefas e projetos, desenvolvido como parte de um desafio técnico.

## 🚀 Instruções para rodar o projeto

### Pré-requisitos
- **Java 17** (ou superior). *Nota: Se estiver usando o JDK 26, veja a seção de Decisões Técnicas sobre o Lombok.*
- **Maven 3.8+**
- Porta **8080** disponível.

### Passos
1. **Clonar o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd spring-api
   ```

2. **Compilar o projeto:**
   Utilize o Maven Wrapper (se disponível) ou o Maven instalado no sistema:
   ```bash
   mvn clean compile
   ```

3. **Executar a aplicação:**
   ```bash
   mvn spring-boot:run
   ```
   A API estará disponível em `http://localhost:8080`.

4. **Banco de Dados (H2):**
   O projeto utiliza um banco de dados H2 em memória para fins de teste. O console do H2 pode ser acessado em `http://localhost:8080/h2-console` com as credenciais padrão:
   - **JDBC URL:** `jdbc:h2:mem:testdb`
   - **User:** `sa`
   - **Password:** (vazio)

---

## 🛠 Decisões Técnicas e Tradeoffs

### 1. Java Version (JDK 17 vs JDK 26)
- **Decisão:** O projeto foi configurado inicialmente para Java 17 (LTS) no `pom.xml`.
- **Tradeoff:** Durante o desenvolvimento, identificamos que o ambiente de execução utilizava o **JDK 26**, que é extremamente recente. Isso causou incompatibilidades críticas com o **Lombok** (`TypeTag :: UNKNOWN`). 
- **Solução:** Tentamos mitigar atualizando o Lombok para `1.18.34` e o `maven-compiler-plugin` para `3.13.0`. Manter uma versão LTS (17 ou 21) é a recomendação para maior estabilidade de bibliotecas de terceiros que dependem de internals do compilador.

### 2. Spring Data JPA com H2
- **Decisão:** Uso do Spring Data JPA para abstração da camada de dados e H2 como banco em memória.
- **Tradeoff:** Acelera o desenvolvimento e facilita a execução imediata do projeto sem necessidade de configurar um banco externo (PostgreSQL/MySQL). O tradeoff é que os dados são perdidos ao reiniciar a aplicação.

### 3. Lombok
- **Decisão:** Uso do Lombok para redução de boilerplate code (getters, setters, constructors).
- **Tradeoff:** Embora aumente a produtividade, ele depende fortemente de hacks no compilador `javac`, o que gera fragilidade em atualizações de JDK (como visto com o JDK 26). Em cenários de produção com JDKs não-LTS, a remoção do Lombok em favor de Java Records ou geração manual de código pode ser necessária.

### 4. JWT (JSON Web Token)
- **Decisão:** Implementação de segurança baseada em tokens JWT (`jjwt-api`).
- **Tradeoff:** Oferece escalabilidade para a API (stateless), mas adiciona complexidade na gestão de expiração e segurança das chaves secretas.

---

## 🔮 O que eu faria diferente com mais tempo

Devido a alguns compromissos que tive durante o fim de semana e ao momento delicado que está meu trabalho atual, não sobrou muito tempo para o desenvolvimento do projeto, acabei desenvolvendo em mais ou menos 3 horas.

1. **Implementação do front-end:** 
   como acabei não conseguindo me organizar para executar esse projeto melhor, não consegui criar o front-end.

2. **Dockerização:** 
   Criaria um `Dockerfile` e um `docker-compose.yaml` para subir a aplicação junto com um banco de dados real (PostgreSQL), garantindo que o ambiente seja idêntico em qualquer máquina.

3. **Testes Automatizados:** 
   Também não consegui me organizar para adicionar os testes unitarios.

4. **Documentação:** 
   Criaria uma documentação detalhada do projeto, explicando as funcionalidades e como executar o projeto com todas as chaamdas da api.

5. **Ajuste de pastas para respeitar o Clean Architecture:**
Teria ajustado minhas pastas para respeitar o Clean Architecture, dessa forma que fiz tem uma separaçao basica pois acabei desenvolvendo as pressas.
