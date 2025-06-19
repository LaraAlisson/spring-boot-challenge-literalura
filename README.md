# Alisson Lara

![Image of Alisson Lara's GitHub Profile Picture](https://avatars.githubusercontent.com/u/149639259?v=4&size=64)

## Sobre Mim

- 🤔 Explorando novas tecnologias e desenvolvendo soluções de software.
- 🎓 Estudando lógica de programação no grupo 8 da Alura/ONE.
- 💼 Trabalhando como projetista de sistemas de automação industrial na Videplast.
- 🌱 Aprendendo mais sobre C/C++, HTML, CSS e JavaScript.

## Minhas Skills

**Aplicações e Dados**

![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat-square&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white)
![Jackson](https://img.shields.io/badge/Jackson-0152D0?style=flat-square&logo=jackson&logoColor=white)

**Utilidades**

![Postman](https://img.shields.io/badge/-Postman-FF6C37?style=flat-square&logo=postman&logoColor=white)

**DevOps**

![Git](https://img.shields.io/badge/-Git-F05032?style=flat-square&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/-GitHub-181717?style=flat-square&logo=github&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=flat-square&logo=apache-maven&logoColor=white)


**Ferramentas de Desenvolvimento**

![Visual Studio Code](https://img.shields.io/badge/-Visual_Studio_Code-007ACC?style=flat-square&logo=visual-studio-code&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=flat-square&logo=intellij-idea&logoColor=white)
![DBeaver](https://img.shields.io/badge/DBeaver-3D8DFF?style=flat-square&logo=dbeaver&logoColor=white)


<br/>

## Projetos em Destaque

### Desafio Literalura (Spring Boot Console Application)

Curso: Alura/ONE - Backend com Java

**Descrição do Projeto:**

O projeto "Desafio Literalura" é uma aplicação Java desenvolvida com Spring Boot, projetada para interagir com a API Gutendex, buscar dados de livros e autores, e persistir essas informações em um banco de dados PostgreSQL. A aplicação opera via console, oferecendo um menu interativo para diversas funcionalidades.

**Funcionalidades:**

-   **1 - Buscar livro por título na API e salvar no DB:** Permite ao usuário pesquisar um livro na API Gutendex pelo título e persistir os dados (livro e autor) no banco de dados. Autores duplicados são tratados.
-   **2 - Buscar todos os livros no banco de dados:** Lista todos os livros que foram previamente salvos no banco de dados, exibindo-os de forma formatada.
-   **3 - Listar autores armazenados com os livros:** Exibe todos os autores presentes no banco de dados, junto com a lista de livros que cada um deles escreveu.
-   **4 - Listar autores vivos em determinado ano:** Permite ao usuário informar um ano e a aplicação lista todos os autores que estavam vivos naquele período.
-   **5 - Buscar livros pelo idioma:** Apresenta um menu de exemplos de idiomas e busca livros no banco de dados com base no idioma selecionado pelo usuário.
-   **6 - Top 10 livros mais baixados:** Exibe uma lista dos 10 livros com o maior número de downloads registrados no banco de dados.
-   **0 - Sair:** Encerra a aplicação.

**Tecnologias Utilizadas:**

-   **Java 17+:** Linguagem de programação principal.
-   **Spring Boot:** Framework para desenvolvimento rápido de aplicações Java.
-   **Spring Data JPA:** Simplifica a implementação de repositórios para acesso a dados.
-   **Hibernate:** Implementação JPA para mapeamento objeto-relacional.
-   **PostgreSQL:** Banco de dados relacional para persistência dos dados.
-   **Jackson:** Biblioteca para serialização/desserialização de JSON (mapeamento da API para objetos Java).
-   **Maven:** Ferramenta de gerenciamento de dependências e build do projeto.

**Como Funciona:**

1.  Ao iniciar a aplicação, um menu interativo é exibido no console.
2.  O usuário escolhe uma opção digitando o número correspondente.
3.  Para buscar e salvar livros, a aplicação faz uma requisição HTTP à API Gutendex.
4.  A resposta JSON da API é convertida em objetos Java (`records`) usando a biblioteca Jackson.
5.  Os objetos são então persistidos no banco de dados PostgreSQL via Spring Data JPA.
6.  As opções de listagem e consulta interagem diretamente com o banco de dados para exibir as informações solicitadas.

**Objetivo do Desafio:**

Este projeto foi desenvolvido como parte de um desafio de backend, visando consolidar conhecimentos em:

-   Consumo de APIs externas.
-   Mapeamento de dados JSON para objetos Java (DTOs/records).
-   Persistência de dados em banco relacional com Spring Data JPA e Hibernate.
-   Criação de menus interativos em aplicações de console.
-   Tratamento de exceções e boas práticas de desenvolvimento Java com Spring Boot.

**Como Executar:**

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/LaraAlisson/spring-boot-challenge-literalura.git
    ```
2.  **Navegue até o diretório do projeto:**
    ```bash
    cd spring-boot-challenge-literalura
    ```
3.  **Configure o banco de dados PostgreSQL:**
    * Certifique-se de ter um banco de dados PostgreSQL rodando.
    * Crie um banco de dados chamado `livros`.
    * Atualize as configurações de conexão no arquivo `src/main/resources/application.properties` com suas credenciais:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost/livros
        spring.datasource.username=postgres
        spring.datasource.password=sua_senha_do_postgres
        spring.datasource.driver-class-name=org.postgresql.Driver
        hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
        spring.jpa.hibernate.ddl-auto=update # ou create para recriar as tabelas
        ```
4.  **Execute a aplicação Spring Boot (via Maven):**
    ```bash
    ./mvnw spring-boot:run # No Linux/macOS
    # ou
    mvn spring-boot:run   # No Windows
    ```
    Ou, se estiver usando uma IDE como IntelliJ IDEA, você pode executar a classe `Application` diretamente.

5.  **Interaja com o menu no console:**
    Após a inicialização, o menu será exibido, permitindo que você utilize as funcionalidades.
