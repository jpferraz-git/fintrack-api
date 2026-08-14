# FinTrack API

Cryptocurrency investment tracking application.

## Environment Setup

### Prerequisites

- Java 21+
- Maven
- Docker and Docker Compose
- PostgreSQL (or use via Docker)
- Node.js 18+ (for frontend)

### Backend Configuration

1. Navigate to `backend/project/`
2. Copy the environment variables template:
   ```bash
   cp .env.properties-template .env.properties
   ```
3. Fill in the values in `.env.properties` with your local credentials:
   ```properties
   DB_USER=postgres
   DB_PASSWORD=your_password_here
   DB_HOST=localhost
   DB_PORT=5433
   DB_NAME=fintrack
   DB_CONTAINER_NAME=fintrack_db
   ```
4. Start the database:
   ```bash
   docker compose up -d
   ```
5. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

> **⚠️ Important:** Never commit the `.env.properties` file — it contains real credentials and is ignored in `.gitignore`.