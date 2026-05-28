# Sistema de Administração Condominial

Sistema web completo para gerenciamento de condomínios residenciais, desenvolvido com Spring Boot e Thymeleaf. Oferece controle integrado de unidades, moradores, visitantes, reservas de áreas comuns e registro de ocorrências.

## Módulos Disponíveis

- **Unidades** — cadastro e gestão de apartamentos/casas do condomínio
- **Moradores** — registro de moradores por unidade, com validação de CPF e controle de responsável
- **Visitantes** — controle de entrada/saída de visitantes com autorização por morador
- **Reservas de Área Comum** — agendamento de churrasqueiras, salões de festa, quadras, etc.
- **Ocorrências** — abertura, acompanhamento e resolução de problemas reportados

## Pré-requisitos

- Java 21 ou superior
- Maven 3.9+ (o projeto inclui Maven Wrapper)

## Como Executar em Desenvolvimento

Clone o repositório e execute o servidor de desenvolvimento:

```bash
./mvnw spring-boot:run
```

O servidor inicia em **http://localhost:8080**.

### Console H2 (Banco de Dados)

Para inspecionar dados em tempo de execução, acesse:

- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:condominialdb`
- **User:** `sa`
- **Password:** *(deixe em branco)*

> **Nota:** O banco H2 roda em memória com estratégia `create-drop` — todos os dados são resetados a cada reinicialização.

## Como Gerar o Executável (JAR)

Para produzir o artefato standalone:

```bash
./mvnw clean package -DskipTests
```

O JAR será gerado em `target/sistema-condominial-0.0.1-SNAPSHOT.jar`.

Para executar o JAR gerado:

```bash
java -jar target/sistema-condominial-0.0.1-SNAPSHOT.jar
```

## Stack Tecnológica

- **Java 21**
- **Spring Boot 3.4.5**
  - Spring Web (MVC)
  - Spring Data JPA
  - Spring Boot Validation
- **Thymeleaf** (server-side rendering)
  - thymeleaf-layout-dialect
  - thymeleaf-extras-springsecurity6
- **H2 Database** (in-memory)
- **Lombok** (boilerplate reduction)
- **Tailwind CSS** (via CDN)
- **Maven** (build automation)

## Arquitetura

Camadas padrão Spring MVC:

- `br.com.condominial.domain` — entidades JPA
- `br.com.condominial.enums` — enumerações (TipoUnidade, StatusVisitante, StatusReserva, etc.)
- `br.com.condominial.validation` — validações customizadas (`@ValidCPF`)
- `br.com.condominial.repository` — Spring Data JPA repositories
- `br.com.condominial.service` — regras de negócio
- `br.com.condominial.controller` — controllers MVC
- `src/main/resources/templates` — templates Thymeleaf

## Comandos Úteis

```bash
# Executar testes
./mvnw test

# Executar um teste específico
./mvnw test -Dtest=NomeDaClasse

# Executar um método de teste específico
./mvnw test -Dtest=NomeDaClasse#nomeDoMetodo

# Gerar JAR sem testes
./mvnw clean package -DskipTests
```

## Licença

Projeto proprietário — uso interno.
