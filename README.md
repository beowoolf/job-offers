# Job Offers

[![Java](https://img.shields.io/badge/Java-25-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.16-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8.6-blue)](https://maven.apache.org/)
[![Version](https://img.shields.io/badge/version-0.0.1-lightgrey)](./pom.xml)

Web API that aggregates job listings from external HTTP sources, stores them in MongoDB, caches reads in Redis, and exposes a JWT-secured REST API for browsing and adding offers.

## Table of contents

- [Project description](#project-description)
- [Tech stack](#tech-stack)
- [Getting started locally](#getting-started-locally)
- [Available scripts](#available-scripts)
- [Project scope](#project-scope)
- [Project status](#project-status)
- [License](#license)

## Project description

**job** (`pl.offers:job`, version `0.0.1`) is a Spring Boot Web API for collecting and serving job offers. It pulls listings from remote services on a schedule, persists only new unique URLs, and lets authenticated clients list, look up, and create records.

The backend covers two related collections:

| Resource | MongoDB collection | External fetch | Cache name |
| --- | --- | --- | --- |
| Offers | `offers` | `GET {offer.http.client.config.uri}:{port}/offers` | `jobOffers` |
| Jobs | `jobs` | `GET {job.http.client.config.uri}:{port}/jobs/jjit/?refresh=1` | `jobs` |

Default remote endpoints (overridable in configuration):

- Offers: `http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com:5057`
- Jobs: `https://api.mojezapiski.pl:443`

Offers store company name, position, salary, and a unique offer URL. Jobs store a richer listing (location, skills, employment types, remote flags, company metadata, unique job URL). Duplicate URLs are rejected with HTTP `409 Conflict`. Missing IDs return HTTP `404`.

Authentication is JWT (Auth0 `java-jwt`), issued after `POST /register` and `POST /token`. Protected routes expect `Authorization: Bearer <token>`. Passwords are stored with BCrypt. JWT lifetime is 30 days; issuer is `job-offers-backend`. The signing secret is read from `JWT_SECRET`.

Read-all endpoints for offers and jobs are cached in Redis (default TTL `PT60M`). HTTP clients use Spring `RestTemplate` with 15s connect and read timeouts. Schedulers run every 3 hours when `scheduling.enabled=true`.

Interactive API docs: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) (Springdoc OpenAPI).

## Tech stack

| Area | Choice |
| --- | --- |
| Language | Java 25 (`maven.compiler.source` / `target` in `pom.xml`) |
| Runtime (Docker image) | Eclipse Temurin 21 (JDK for build, JRE for run, Alpine) |
| Framework | Spring Boot **3.4.16** (`spring-boot-starter-parent`) |
| Packaging | WAR (`job` artifact), run as an executable Spring Boot archive |
| Web / API | Spring Web (`RestController`), Bean Validation, Springdoc OpenAPI UI 1.7.0 |
| Security | Spring Security (stateless), Auth0 Java JWT 4.0.0, BCrypt |
| Persistence | Spring Data MongoDB |
| Cache | Spring Data Redis 2.4.5, Jedis 3.3.0 |
| HTTP client | Spring `RestTemplate` |
| Scheduling | Spring `@Scheduled` (`fixedDelayString`) |
| Logging | Logback (`logback.xml`); domain/infra loggers use `@Log4j2` |
| Build | Maven Wrapper **3.8.6** (`./mvnw`, `mvnw.cmd`) |
| Containers | Docker, Docker Compose (`mongo:4.0.10`, Redis, Mongo Express, Redis Commander) |
| Tests | JUnit 5, AssertJ 3.23.1, Mockito (via `spring-boot-starter-test`), MockMvc, Spring Security Test, Testcontainers 1.20.0 (MongoDB), WireMock 2.35.1, Awaitility 4.2.0 |
| Utilities | Lombok 1.18.34 |

Application layout follows a domain / infrastructure split (`pl.offers.job.domain.*` facades and `pl.offers.job.infrastructure.*` controllers, HTTP, JWT, cache, schedulers).

## Getting started locally

### Prerequisites

- JDK **25** for Maven builds matching `pom.xml` (the Docker multi-stage build currently uses Temurin **21**)
- Docker and Docker Compose (MongoDB, Redis, optional admin UIs, or the full stack)
- Git

### Configuration

Copy or export these variables before starting the app. Defaults in `src/main/resources/application.properties` apply when a variable is omitted (except `JWT_SECRET`, which has no default).

| Variable | Default | Purpose |
| --- | --- | --- |
| `JWT_SECRET` | *(required)* | HMAC secret for JWT signing |
| `MONGO_USER` | `jobOffersUser` | MongoDB application user |
| `MONGO_PASSWORD` | `jobOffersPassword` | MongoDB application password |
| `MONGO_HOST` | `localhost` | MongoDB host |
| `MONGO_PORT` | `27017` | MongoDB port |
| `MONGO_DB_NAME` | `jobOffersDataBase` | Database name |
| `REDIS_HOST` | `localhost` | Redis host |
| `REDIS_PORT` | `63792` | Redis port (host mapping; in Compose the app talks to Redis on `6379`) |

Other notable properties:

| Property | Default | Purpose |
| --- | --- | --- |
| `auth.jwt.expirationDays` | `30` | Token lifetime |
| `auth.jwt.issuer` | `job-offers-backend` | JWT issuer claim |
| `scheduling.enabled` | `true` | Enables offer and job fetch schedulers |
| `http.offers.scheduler.request.delay` | `PT3H` | Offer fetch interval |
| `http.jobs.scheduler.request.delay` | `PT3H` | Job fetch interval |
| `http.client.config.connectionTimeout` | `15000` | HTTP connect timeout (ms) |
| `http.client.config.readTimeout` | `15000` | HTTP read timeout (ms) |
| `spring.cache.type` | `redis` | Cache backend (`none` in the `integration` test profile) |
| `spring.cache.redis.time-to-live` | `PT60M` | Cache TTL |
| `spring.data.mongodb.auto-index-creation` | `true` | Creates unique indexes on URLs |

On Windows PowerShell:

```powershell
$env:JWT_SECRET = "change-me-to-a-long-random-secret"
```

On Unix shells:

```bash
export JWT_SECRET="change-me-to-a-long-random-secret"
```

### Option A — Docker Compose (recommended)

Starts MongoDB (init user via `mongo-init.js`), Mongo Express, Redis, Redis Commander, and the application.

```bash
docker compose up -d --build
```

Set `JWT_SECRET` on the `job-offers` service if you need working login; the checked-in `docker-compose.yml` does not define it yet.

| Service | Host port | Notes |
| --- | --- | --- |
| Application | `8080` | REST API + Swagger UI |
| MongoDB | `27017` | Root user `root` / `toor`; app user `jobOffersUser` / `jobOffersPassword` on `jobOffersDataBase` |
| Mongo Express | `8081` | Browser admin for MongoDB |
| Redis | `63792` → container `6379` | Cache |
| Redis Commander | `8082` | Browser admin for Redis |

The `job-offers` container is configured with `MONGO_HOST=mongo`, `REDIS_HOST=redis`, and `REDIS_PORT=6379`.

### Option B — infrastructure in Docker, app on the host

```bash
docker compose up -d mongo redis
```

Then from the repository root (Unix):

```bash
export JWT_SECRET="change-me-to-a-long-random-secret"
./mvnw spring-boot:run
```

Windows:

```powershell
$env:JWT_SECRET = "change-me-to-a-long-random-secret"
.\mvnw.cmd spring-boot:run
```

### Option C — application image only

Infrastructure (MongoDB and Redis) must already be reachable with the env vars above.

```bash
docker build -f Dockerfile -t job-offers .
docker run -d -p 8080:8080 ^
  -e JWT_SECRET=change-me-to-a-long-random-secret ^
  -e MONGO_HOST=host.docker.internal ^
  -e REDIS_HOST=host.docker.internal ^
  -e REDIS_PORT=63792 ^
  job-offers
```

On Unix, use `\` line continuations instead of `^`.

### Quick API check

1. Register:

```bash
curl -s -X POST http://localhost:8080/register -H "Content-Type: application/json" -d "{\"username\":\"demo\",\"password\":\"demo-pass\"}"
```

2. Obtain a token:

```bash
curl -s -X POST http://localhost:8080/token -H "Content-Type: application/json" -d "{\"username\":\"demo\",\"password\":\"demo-pass\"}"
```

3. Call a protected endpoint with `Authorization: Bearer <token>`:

```bash
curl -s http://localhost:8080/offers -H "Authorization: Bearer <token>"
```

Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Available scripts

There is no `package.json`. Use the Maven Wrapper and Docker:

| Command | Description |
| --- | --- |
| `./mvnw spring-boot:run` / `.\mvnw.cmd spring-boot:run` | Run the API locally |
| `./mvnw test` | Unit and integration tests (`integration` profile, Testcontainers MongoDB, WireMock) |
| `./mvnw clean package` | Compile and build `target/*.war` |
| `./mvnw clean package -DskipTests` | Package without tests (same as the Docker build stage) |
| `docker compose up -d --build` | Build and start the full stack |
| `docker compose down` | Stop Compose services |
| `docker build -f Dockerfile -t job-offers .` | Multi-stage image (`./mvnw clean package -DskipTests`, then `java -jar app.war`) |
| `docker run -d -p 8080:8080 job-offers` | Run the image (still needs MongoDB, Redis, and `JWT_SECRET`) |

Integration tests disable scheduling (`scheduling.enabled=false`), set cache type to `none` by default, and override HTTP client URIs to a WireMock server. Redis cache tests exist under `src/test/java/pl/offers/job/integration/cache/`.

## Project scope

### REST API

Public (no JWT):

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/register` | Create a user (`username`, `password`); password is BCrypt-hashed; `201 Created` |
| `POST` | `/token` | Authenticate and return `{ "username", "token" }`; invalid credentials → `401` |
| `GET` | `/swagger-ui/**`, `/v3/api-docs`, `/v3/api-docs/swagger-config`, `/webjars/**`, `/swagger-resources/**` | OpenAPI UI and docs |

Authenticated (JWT Bearer):

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/offers` | List offers (cached as `jobOffers`) |
| `GET` | `/offers/{id}` | Offer by id |
| `POST` | `/offers` | Create offer; body: `companyName`, `position`, `salary`, `offerUrl` (all required) |
| `GET` | `/jobs` | List jobs (cached as `jobs`) |
| `GET` | `/jobs/{id}` | Job by id |
| `POST` | `/jobs` | Create job listing |

### Behaviour

- **Fetch and upsert:** schedulers call external HTTP APIs and save records whose URL is not already stored.
- **Uniqueness:** MongoDB unique indexes on offer `url` and job `url`.
- **Validation:** Jakarta Validation plus `validationMessages.properties`.
- **Errors:** not found (`404`), duplicate URL (`409`), bean-validation failures (API validation handler), bad credentials on login.
- **Security:** CSRF disabled, HTTP Basic disabled, stateless sessions, JWT filter before username/password authentication.

### Out of scope

- No frontend UI (API and Swagger only)
- No GitHub Actions / CI config in this repository
- No custom Maven profiles beyond Spring’s `integration` test profile
- Docker Compose does not currently pass `JWT_SECRET` into the `job-offers` container

## Project status

Version **0.0.1**. The API, JWT auth, dual offer/job ingestion, Redis cache, Docker Compose stack, unit tests, and Spring Boot integration tests are in place.

Known gaps to keep in mind:

- `pom.xml` targets **Java 25** while `Dockerfile` builds and runs on **Temurin 21**.
- Logging is configured with **Logback**, not a Log4j2 `log4j2.xml` (older README text was outdated).
- Production secrets (`JWT_SECRET`, Mongo root password in Compose) should be replaced before any real deployment.
- No automated CI badges or pipelines are defined in-repo.

## License

No license file is included in this repository, and `pom.xml` does not declare a `<licenses>` section. All rights remain with the project authors until a license is added. If you intend to reuse this code, ask the maintainers or add an explicit license (for example MIT or Apache-2.0) before distributing it.
