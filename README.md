# NucleaJ Backend

Enterprise-grade backend framework built with Java 21 and Spring Boot 3.3 - A comprehensive, production-ready foundation for building scalable enterprise applications.

## 🚀 Overview

NucleaJ.Backend is a modern, feature-rich backend framework that provides everything you need to build robust enterprise applications. Built from the ground up with **Java 21** and **Spring Boot 3.3**, it incorporates industry best practices and leverages the full power of the Spring ecosystem.

## ✨ Key Features

### Core Capabilities
- ✅ **Multi-Module Maven Architecture** - Clean separation of concerns across 15 specialized modules
- ✅ **JWT Authentication** - Secure token-based authentication with automatic refresh token rotation
- ✅ **Spring Security Integration** - Enterprise-grade security with custom filters and interceptors
- ✅ **Flexible Caching** - Support for both in-memory (Caffeine) and distributed (Redis) caching
- ✅ **Internationalization (i18n)** - Built-in multi-language support (Turkish & English, easily extensible)
- ✅ **Email Service** - Complete email solution with templates, SMTP, and queue management
- ✅ **File Management** - Robust file upload/download with FTP/CDN integration
- ✅ **Soft Delete Pattern** - Logical deletion across all entities with automatic filtering
- ✅ **Audit Trail** - Automatic timestamp tracking using JPA Auditing
- ✅ **API Documentation** - Interactive API docs with OpenAPI/Swagger
- ✅ **CORS Support** - Flexible cross-origin resource sharing configuration
- ✅ **Rate Limiting** - Built-in request throttling capabilities
- ✅ **Job Scheduling** - Background job processing with Spring Scheduler
- ✅ **Messaging Integration** - Full support for Kafka and RabbitMQ

### Technology Stack

| Category | Technology | Version |
|----------|-----------|---------|
| **Language** | Java | 21 |
| **Framework** | Spring Boot | 3.3.0 |
| **Build Tool** | Maven | 3.9+ |
| **Database** | Microsoft SQL Server | 2019+ |
| **Security** | Spring Security | 6 |
| **JWT** | jjwt | 0.12.5 |
| **Cache** | Caffeine / Redis | 3.1.8 / Latest |
| **Messaging** | Kafka / RabbitMQ | Latest |
| **API Docs** | SpringDoc OpenAPI | 2.5.0 |
| **Mapping** | MapStruct | 1.5.5 |
| **Email** | Jakarta Mail | 2.0.1 |
| **Templates** | FreeMarker | 2.3.33 |
| **Testing** | JUnit 5 | Latest |

## 📦 Project Structure

```
nucleaj-backend/
├── nucleaj-api/                    # REST API application layer
├── nucleaj-api-business/           # Business logic implementation
├── nucleaj-data/                   # Data access layer (JPA entities & repositories)
├── nucleaj-common/                 # Common utilities and shared services
├── nucleaj-business-caching/       # Caching layer for business entities
├── nucleaj-hangfire/               # Job scheduling application
├── nucleaj-hangfire-business/      # Job scheduling business logic
├── nucleaj-kafka/                  # Kafka base interfaces and models
├── nucleaj-kafka-producer/         # Kafka message producer
├── nucleaj-kafka-consumer/         # Kafka message consumer
├── nucleaj-rabbitmq/               # RabbitMQ base interfaces and models
├── nucleaj-rabbitmq-producer/      # RabbitMQ message producer
├── nucleaj-rabbitmq-consumer/      # RabbitMQ message consumer
├── nucleaj-resources/              # Localization resources (i18n)
└── nucleaj-tests-common/           # Common testing utilities
```

## 🏗️ Architecture

### Layered Architecture
```
┌─────────────────────────────────────┐
│         API Layer (Controllers)     │
├─────────────────────────────────────┤
│     Business Service Layer          │
├─────────────────────────────────────┤
│        Caching Layer                │
├─────────────────────────────────────┤
│     Data Access Layer (JPA)         │
├─────────────────────────────────────┤
│         Database (SQL Server)       │
└─────────────────────────────────────┘
```

### Design Patterns
- **Repository Pattern** - Clean data access abstraction with Spring Data JPA
- **Service Layer Pattern** - Encapsulated business logic
- **DTO Pattern** - Request/Response models for API communication
- **Dependency Injection** - Constructor-based injection throughout
- **Template Method** - BaseService & BaseController for code reuse
- **Strategy Pattern** - Multiple cache implementation strategies
- **Soft Delete Pattern** - Non-destructive data removal

## 🚦 Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.9+
- SQL Server 2019+
- Redis (optional, for distributed caching)
- Kafka/RabbitMQ (optional, for messaging features)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/Onurryilmazz/nucleaj-backend.git
cd nucleaj-backend
```

2. **Configure environment variables**

Create an `application-local.properties` file or set environment variables:

```bash
# Database
export DB_USERNAME=your_db_user
export DB_PASSWORD=your_db_password

# JWT Secret (minimum 32 characters)
export JWT_SECRET_KEY=your-very-secure-secret-key-here

# Mail Configuration
export MAIL_HOST=smtp.gmail.com
export MAIL_USERNAME=your-email@gmail.com
export MAIL_PASSWORD=your-app-password

# API Key
export API_KEY=your-secure-api-key
```

3. **Build the project**
```bash
mvn clean install
```

4. **Run the application**
```bash
cd nucleaj-api
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080/api`

### Access Swagger UI
Interactive API documentation: `http://localhost:8080/api/swagger-ui.html`

## 📚 API Endpoints

### Authentication & Customer Management

#### Register Customer
```http
POST /api/customer/register
Content-Type: application/json
X-API-Key: your-api-key

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "SecurePassword123!"
}
```

#### Login
```http
POST /api/customer/login
Content-Type: application/json
X-API-Key: your-api-key

{
  "email": "john.doe@example.com",
  "password": "SecurePassword123!"
}
```

#### Refresh Token
```http
POST /api/auth/refresh
Content-Type: application/json
X-API-Key: your-api-key

{
  "refreshToken": "your-refresh-token"
}
```

#### Verify Email
```http
POST /api/customer/verify-email?email=john.doe@example.com&code=123456
X-API-Key: your-api-key
```

## ⚙️ Configuration

### Application Profiles
- `application.properties` - Default configuration
- `application-dev.properties` - Development environment
- `application-prod.properties` - Production environment

### Running with Different Profiles
```bash
# Development
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Production
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Cache Configuration
Switch between in-memory and Redis cache:
```properties
# In-memory cache (default)
spring.cache.type=caffeine

# Redis cache
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

## 🔐 Security

### Security Features
- JWT-based stateless authentication
- Automatic refresh token rotation
- API key validation on all endpoints
- Spring Security integration
- BCrypt password hashing
- Configurable CORS policies
- Request rate limiting
- OAuth2 support (Google & Apple)

### Security Best Practices
All sensitive configuration should be externalized:
```bash
# Never commit these values to version control
export JWT_SECRET_KEY=your-secret-key
export DB_PASSWORD=your-db-password
export API_KEY=your-api-key
```

## 📧 Email Service

### Template-based Emails
Email templates use FreeMarker. Place your templates in:
```
nucleaj-api/src/main/resources/templates/email/
```

Example template (`email-verification.ftl`):
```html
<html>
<body>
    <h1>Welcome ${firstName}!</h1>
    <p>Your verification code is: <strong>${verificationCode}</strong></p>
</body>
</html>
```

### Sending Emails
```java
Map<String, Object> variables = new HashMap<>();
variables.put("firstName", "John");
variables.put("verificationCode", "123456");

mailService.sendTemplateEmail(
    "john@example.com",
    "Email Verification",
    "email-verification",
    variables
);
```

## 🗄️ Database

### Entity Relationships
- Customer → RefreshToken (One-to-Many)
- Role → RoleClaim (One-to-Many)
- RoleClaim → SecurityClaim (Many-to-One)
- Element → ElementValue (One-to-Many)
- Country → City → District → Neighbourhood (Hierarchical)

### Soft Delete
Entities implementing `ISoftDelete` are logically deleted:
```java
customer.markAsDeleted(); // Sets deletedDate
customerRepository.findAllActive(); // Excludes soft-deleted records
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
```bash
mvn verify
```

### Example Test
```java
@Test
void registerCustomer_Success() throws Exception {
    mockMvc.perform(post("/customer/register")
            .header("X-API-Key", "test-api-key")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
}
```

## 📊 Monitoring

### Actuator Endpoints
- Health Check: `/actuator/health`
- Application Info: `/actuator/info`
- Metrics: `/actuator/metrics`

## 🚀 Deployment

### Build for Production
```bash
mvn clean package -Pprod
```

### Docker Deployment
Create a `Dockerfile`:
```dockerfile
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/nucleaj-api-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t nucleaj-backend .
docker run -p 8080:8080 \
  -e DB_PASSWORD=your-password \
  -e JWT_SECRET_KEY=your-secret \
  nucleaj-backend
```

### Docker Compose
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - DB_USERNAME=${DB_USERNAME}
      - DB_PASSWORD=${DB_PASSWORD}
      - JWT_SECRET_KEY=${JWT_SECRET_KEY}
    depends_on:
      - db
      - redis

  db:
    image: mcr.microsoft.com/mssql/server:2019-latest
    environment:
      - ACCEPT_EULA=Y
      - SA_PASSWORD=${DB_PASSWORD}
    ports:
      - "1433:1433"

  redis:
    image: redis:alpine
    ports:
      - "6379:6379"
```

## 📝 Development Best Practices

1. **Service Markers** - Use `IScopedService`, `ISingletonService`, `ITransientService` for automatic bean lifecycle management
2. **BaseService** - Extend for common functionality (cache access, localization, user context)
3. **BaseController** - Extend for consistent API responses and centralized error handling
4. **DTOs** - Always use separate request/response models, never expose entities directly
5. **Validation** - Use Jakarta Bean Validation annotations (`@NotNull`, `@Email`, etc.)
6. **Transactions** - Apply `@Transactional` on service methods that modify data
7. **Logging** - Use SLF4J with appropriate log levels (DEBUG for development, INFO for production)
8. **Null Safety** - Leverage `Optional<T>` to avoid NullPointerExceptions

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow standard Java conventions
- Use Lombok to reduce boilerplate
- Write meaningful commit messages
- Add tests for new features
- Update documentation as needed

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Author

**Onur Yılmaz**
- Enterprise Backend Architect
- Java & Spring Boot Specialist

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- All open-source contributors
- The Java community

## 📞 Support

For questions, issues, or feature requests:
- Create an issue in the repository
- Contact: [onur@avvamobile.com](mailto:onur@avvamobile.com)

**Built with ❤️ using Java 21 & Spring Boot 3.3**

*Production-ready • Scalable • Maintainable • Well-documented*
