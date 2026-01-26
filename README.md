# Tinder AI
## Overview

Tinder AI is a full-stack application that provides backend and frontend services for interacting with AI-driven profiles.
Users can create profiles, like or dislike AI profiles, get matched, and chat in real time with matched AI profiles.

The project is built using Spring Boot, React, and PostgreSQL, with a focus on clean architecture, scalability, and modern backend practices.

## Features

- User authentication and authorization
- Profile creation and management
- AI profile matching system
- Like / dislike functionality
- Match creation
- Real-time chat with matched AI profiles
- Secure API access using JWT

## Tech Stack
- **Backend**: Java 25, Spring Boot 3.5.9
- **Frontend**: React
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Containerization**: Docker, Docker Compose
- **Authentication**: JWT

## Getting Started
### Prerequisites
- Java 25 or higher
- Maven 3.6.3 or higher
- PostgreSQL 14 or higher
- Docker (optional, for containerized deployment)

### Local Development
1. **Clone the Repository**
   ```bash
   git clone https://github.com/KristiyanKraev//tinder-ai-backend.git
   cd tinder-ai-backend/backend
   ```

2. **Configure the Database**
   - Create a PostgreSQL database
   - Update the database configuration in `src/main/resources/application.properties`
   - Or use environment variables

3. **Build the Application**
   ```bash
   ./mvnw clean install
   ```

4. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```

The backend will be available at `http://localhost:8080`

### Docker Deployment
1. **Build the Docker image**
   ```bash
   docker-compose build
   ```

2. **Start the containers**
   ```bash
   docker-compose up -d
   ```

## Configuration

Create a `.env` file in the root directory with the following variables:

```env
# Database Configuration
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-password

# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/tinder_ai
SPRING_DATASOURCE_USERNAME=your-username
SPRING_DATASOURCE_PASSWORD=your-password

# JWT
JWT_SECRET=your-secret-key
JWT_EXPIRATION=3600000

# Docker Database
DB_NAME=your-db-name
DB_USER=your-db-user
DB_PASSWORD=your-db-password
```

## API Documentation

The API documentation is automatically generated using Swagger.
API documentation is available at `http://localhost:8080/swagger-ui.html` when the application is running.

### API Endpoints
#### Authentication
- `POST /auth/signup` - Register a new user
- `POST /auth/login` - Authenticate user and get JWT token
- `POST /auth/verify` - Verify user's email
- `POST /auth/resend` - Resend verification code

#### Users
- `GET /users/me` - Get current user's profile
- `GET /users/` - Get all users (Admin only)

#### Profiles
- `GET /profiles/{id}` - Get profile by ID
- `GET /profiles/user` - Get current user's profile
- `POST /profiles` - Create profile
- `PUT /profiles/{id}` - Update profile
- `DELETE /profiles/{id}` - Delete profile

#### Matches
- `GET /matches` - Get all matches for current user
- `POST /matches` - Create a new match
- `DELETE /matches/{id}` - Delete a match (W.I.P)

#### Conversations
- `POST /conversations` - Create a new conversation
- `GET /conversations/{conversationId}` - Get conversation by ID
- `POST /conversations/{conversationId}` - Send a message in a conversation

## Authentication & Authorization
Authentication is handled using JWT tokens. All endpoints (except `/auth/*`) require a valid JWT token in the `Authorization` header.

