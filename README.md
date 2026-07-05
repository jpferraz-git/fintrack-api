# FinTrack API

Aplicação de rastreamento de investimentos em criptomoedas.

## Setup do ambiente

### Pré-requisitos

- Java 21+
- Maven
- Docker e Docker Compose
- PostgreSQL (ou usar via Docker)
- Node.js 18+ (para o frontend)

### Configuração do backend

1. Navegue até `backend/project/`
2. Copie o template de variáveis de ambiente:
   ```bash
   cp .env.properties-template .env.properties
   ```
3. Preencha os valores no `.env.properties` com suas credenciais locais:
   ```properties
   DB_USER=postgres
   DB_PASSWORD=sua_senha_aqui
   DB_HOST=localhost
   DB_PORT=5433
   DB_NAME=fintrack
   DB_CONTAINER_NAME=fintrack_db
   ```
4. Suba o banco de dados:
   ```bash
   docker compose up -d
   ```
5. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

> **⚠️ Importante:** Nunca commite o arquivo `.env.properties` — ele contém credenciais reais e está no `.gitignore`.