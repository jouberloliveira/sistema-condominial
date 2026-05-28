# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run the application
./mvnw spring-boot:run

# Build (skip tests)
./mvnw clean package -DskipTests

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Run a single test method
./mvnw test -Dtest=ClassName#methodName
```

The app starts on http://localhost:8080. H2 console is at http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:condominialdb`).

## Architecture

Standard Spring MVC layered architecture with Thymeleaf server-side rendering.

**Base package:** `br.com.condominial`

**Layers:**
- `domain/` — JPA entities (Lombok `@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor`)
- `enums/` — all enum types used by entities
- `validation/` — custom Bean Validation: `@ValidCPF` annotation + `CPFValidator` (implements `ConstraintValidator`)
- `repository/` — Spring Data JPA interfaces (not yet created — add as needed)
- `service/` — business logic and cross-entity validation rules (not yet created)
- `controller/` — Spring MVC `@Controller` classes (not yet created)
- `templates/` — Thymeleaf templates using `thymeleaf-layout-dialect` for layout fragments

**Key entity relationships:**
- `Morador` → `Unidade` (ManyToOne, LAZY)
- `Visitante` → `Unidade` (ManyToOne, LAZY), `Visitante.autorizadoPor` → `Morador` (ManyToOne, LAZY, optional)
- `Reserva` → `Unidade` + `Morador` as solicitante (to be implemented)
- `Ocorrencia` → `Unidade` (optional) + `Morador` as aberto_por (optional) (to be implemented)

**Business rules enforced in the service layer (not annotations):**
- Max 1 `responsavel=SIM` per `Unidade` among `Morador` records
- `Visitante.autorizadoPor` must be a `Morador` of the same `Unidade`
- `Reserva`: `fim > inicio`; no overlap for same `AreaComum` with `status=APROVADA`; solicitante must belong to the unidade
- `Ocorrencia`: `data_fechamento` required when status is `RESOLVIDA` or `CANCELADA`; `data_fechamento >= data_abertura`

**Database:** H2 in-memory (`create-drop`) — data resets on every restart. `spring.mvc.hiddenmethod.filter.enabled=true` is set, enabling `_method` override for PUT/DELETE from HTML forms.

**Tailwind CSS** is loaded via CDN — no build step for styles.

**Enums** are stored as `STRING` in the DB. All enum types live in `br.com.condominial.enums`.
