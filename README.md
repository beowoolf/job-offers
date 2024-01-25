# job-offers

A web application that allows you to collect job offers from various sources for Junior Java Developers (websites, other
web applications). The main function of the application is to download current job offers from websites. Technologies
used in the project: Spring Boot: Web (RestControllers), Test, Data MongoDB, Validation, Security, JWT, Spring
Scheduler, Java 17, MongoDB + MongoExpress, Docker & Docker-Compose & Docker Desktop, Wiremock, Log4j2, Testcontainers,
RestTemplate, json, HTTP, MockMvc, Awaitility, Unit Tests (JUnit5, Mockito, AssertJ), Integration Tests SpringBootTest,
SpringSecurityTest, Lombok, Redis & Jedis & Redis-Commander, Maven, Git, Github, IntelliJ Ultimate, Swagger.

## Main commands

```bash
# image creation
$ docker build -f Dockerfile -t job-offers .

# starting the image
$ docker run -d -p 8080:8080 job-offers

# image composing
$ docker-compose up -d
```
