<p align="center">
  <img src="https://img.shields.io/badge/Stream-Forge-ff6b6b?style=for-the-badge&logo=youtube&logoColor=white&labelColor=ee5a24" alt="StreamForge"/>
</p>

<h1 align="center">StreamForge</h1>
<h3 align="center">Video Streaming Microservices Platform</h3>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.2.3-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Spring_Cloud-2023.0.0-6DB33F?style=flat-square&logo=spring&logoColor=white" alt="Spring Cloud"/>
  <img src="https://img.shields.io/badge/Maven-3.9+-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker&logoColor=white" alt="Docker"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=flat-square" alt="License"/>
</p>

<p align="center">
  <i>A fully backend video streaming platform built with Spring Boot microservices, featuring REST APIs for managing video content, users, watchlists, and watch history.</i>
</p>

---

## <img src="https://img.shields.io/badge/-Architecture-blueviolet?style=flat-square" alt="Architecture"/> Overview

```
                    ┌──────────────────┐
                    │   Client/Postman │
                    └────────┬─────────┘
                             │
                    ┌────────▼─────────┐
                    │  Gateway Service │ :8090
                    │ (Spring Cloud GW)│
                    └──┬───────────┬───┘
                       │           │
              ┌────────▼──┐  ┌────▼────────┐
              │Video Svc  │  │ User Svc    │
              │  :8081    │  │  :8082      │
              └─────┬─────┘  └──┬──────┬───┘
                    │           │      │
              ┌─────▼─────┐    │   ┌──▼──────────┐
              │ Video DB  │    │   │  User DB    │
              │  (MySQL)  │    │   │  (MySQL)    │
              └───────────┘    │   └─────────────┘
                               │ OpenFeign
                    ┌──────────▼──────────┐
                    │  Video Service      │
                    └─────────────────────┘

    ┌─────────────────┐    ┌──────────────────┐
    │ Discovery Svc   │    │  Config Service  │
    │ (Eureka) :8761  │    │  :8888           │
    └─────────────────┘    └──────────────────┘
```

---

## <img src="https://img.shields.io/badge/-Microservices-ff6348?style=flat-square" alt="Services"/> Services

<table>
  <thead>
    <tr>
      <th>Service</th>
      <th>Port</th>
      <th>Description</th>
      <th>Tech</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><img src="https://img.shields.io/badge/discovery--service-8761-1abc9c?style=flat-square" alt="discovery"/></td>
      <td><code>8761</code></td>
      <td>Eureka Service Discovery Server</td>
      <td><img src="https://img.shields.io/badge/-Eureka-6DB33F?style=flat-square&logo=spring&logoColor=white" alt="Eureka"/></td>
    </tr>
    <tr>
      <td><img src="https://img.shields.io/badge/config--service-8888-3498db?style=flat-square" alt="config"/></td>
      <td><code>8888</code></td>
      <td>Spring Cloud Config Server (Git-backed)</td>
      <td><img src="https://img.shields.io/badge/-Config-6DB33F?style=flat-square&logo=spring&logoColor=white" alt="Config"/></td>
    </tr>
    <tr>
      <td><img src="https://img.shields.io/badge/gateway--service-8090-9b59b6?style=flat-square" alt="gateway"/></td>
      <td><code>8090</code></td>
      <td>API Gateway — single entry point</td>
      <td><img src="https://img.shields.io/badge/-Gateway-6DB33F?style=flat-square&logo=spring&logoColor=white" alt="Gateway"/></td>
    </tr>
    <tr>
      <td><img src="https://img.shields.io/badge/video--service-8081-e74c3c?style=flat-square" alt="video"/></td>
      <td><code>8081</code></td>
      <td>Video content CRUD & search</td>
      <td><img src="https://img.shields.io/badge/-JPA-59666C?style=flat-square&logo=hibernate&logoColor=white" alt="JPA"/></td>
    </tr>
    <tr>
      <td><img src="https://img.shields.io/badge/user--service-8082-e67e22?style=flat-square" alt="user"/></td>
      <td><code>8082</code></td>
      <td>Users, Watchlist, History & Stats</td>
      <td><img src="https://img.shields.io/badge/-OpenFeign-6DB33F?style=flat-square&logo=spring&logoColor=white" alt="Feign"/></td>
    </tr>
  </tbody>
</table>

---

## <img src="https://img.shields.io/badge/-Tech_Stack-2ecc71?style=flat-square" alt="Tech"/> Technologies

<p>
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.2.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="JPA"/>
  <img src="https://img.shields.io/badge/Eureka-Discovery-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Eureka"/>
  <img src="https://img.shields.io/badge/OpenFeign-Client-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Feign"/>
  <img src="https://img.shields.io/badge/H2-Dev_DB-0000BB?style=for-the-badge" alt="H2"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker"/>
  <img src="https://img.shields.io/badge/Lombok-red?style=for-the-badge" alt="Lombok"/>
  <img src="https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit"/>
  <img src="https://img.shields.io/badge/Mockito-grey?style=for-the-badge" alt="Mockito"/>
</p>

---

## <img src="https://img.shields.io/badge/-Getting_Started-3498db?style=flat-square" alt="Start"/> Getting Started

### Prerequisites

<p>
  <img src="https://img.shields.io/badge/Required-Java_17+-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Required-Maven_3.8+-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/Optional-Docker-2496ED?style=flat-square&logo=docker&logoColor=white" alt="Docker"/>
</p>

### Run Locally (Development)

Each service uses **H2 in-memory database** in dev mode — no database setup needed.

```bash
# 1. Setup config repository
./setup-config-repo.bat        # Windows
# or: bash setup-config-repo.sh  # Linux/Mac

# 2. Start services in order
cd discovery-service && mvn spring-boot:run    # Terminal 1
cd config-service && mvn spring-boot:run       # Terminal 2
cd gateway-service && mvn spring-boot:run      # Terminal 3
cd video-service && mvn spring-boot:run        # Terminal 4
cd user-service && mvn spring-boot:run         # Terminal 5
```

### Run with Docker Compose

```bash
docker-compose up --build      # Start all services
docker-compose down            # Stop all services
```

---

## <img src="https://img.shields.io/badge/-API_Endpoints-e74c3c?style=flat-square" alt="API"/> REST API Reference

> All endpoints are accessible through the Gateway at `http://localhost:8090`

### <img src="https://img.shields.io/badge/Video_Service-/api/videos-e74c3c?style=flat-square" alt="Video"/>

| Method | Endpoint | Description |
|:------:|----------|-------------|
| ![POST](https://img.shields.io/badge/POST-49cc90?style=flat-square) | `/api/videos` | Create a new video |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/videos` | Get all videos |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/videos/{id}` | Get video by ID |
| ![PUT](https://img.shields.io/badge/PUT-fca130?style=flat-square) | `/api/videos/{id}` | Update a video |
| ![DELETE](https://img.shields.io/badge/DELETE-f93e3e?style=flat-square) | `/api/videos/{id}` | Delete a video |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/videos/type/{type}` | Filter by type (FILM/SERIE) |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/videos/category/{category}` | Filter by category |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/videos/search?title=` | Search by title |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/videos/director?name=` | Filter by director |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/videos/rating?min=` | Filter by minimum rating |

### <img src="https://img.shields.io/badge/User_Service-/api/users-e67e22?style=flat-square" alt="User"/>

| Method | Endpoint | Description |
|:------:|----------|-------------|
| ![POST](https://img.shields.io/badge/POST-49cc90?style=flat-square) | `/api/users` | Create a new user |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/users` | Get all users |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/users/{id}` | Get user by ID |
| ![PUT](https://img.shields.io/badge/PUT-fca130?style=flat-square) | `/api/users/{id}` | Update a user |
| ![DELETE](https://img.shields.io/badge/DELETE-f93e3e?style=flat-square) | `/api/users/{id}` | Delete a user |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/users/{id}/stats` | Get viewing statistics |

### <img src="https://img.shields.io/badge/Watchlist-/api/watchlist-9b59b6?style=flat-square" alt="Watchlist"/>

| Method | Endpoint | Description |
|:------:|----------|-------------|
| ![POST](https://img.shields.io/badge/POST-49cc90?style=flat-square) | `/api/watchlist/{userId}/videos/{videoId}` | Add to watchlist |
| ![DELETE](https://img.shields.io/badge/DELETE-f93e3e?style=flat-square) | `/api/watchlist/{userId}/videos/{videoId}` | Remove from watchlist |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/watchlist/{userId}` | Get user's watchlist |

### <img src="https://img.shields.io/badge/Watch_History-/api/history-1abc9c?style=flat-square" alt="History"/>

| Method | Endpoint | Description |
|:------:|----------|-------------|
| ![POST](https://img.shields.io/badge/POST-49cc90?style=flat-square) | `/api/history/{userId}` | Record watch history |
| ![GET](https://img.shields.io/badge/GET-61affe?style=flat-square) | `/api/history/{userId}` | Get user's watch history |

---

## <img src="https://img.shields.io/badge/-Sample_Requests-f39c12?style=flat-square" alt="Samples"/> Sample API Requests

<details>
<summary><img src="https://img.shields.io/badge/POST-49cc90?style=flat-square" alt="POST"/> <b>Create a Video</b></summary>

```json
POST http://localhost:8090/api/videos
Content-Type: application/json

{
    "title": "Inception",
    "description": "A thief who steals corporate secrets through dream-sharing technology",
    "thumbnailUrl": "https://example.com/inception.jpg",
    "trailerUrl": "https://www.youtube.com/embed/YoHD9XEInc0",
    "duration": 148,
    "releaseYear": 2010,
    "type": "FILM",
    "category": "SCIENCE_FICTION",
    "rating": 8.8,
    "director": "Christopher Nolan",
    "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page"
}
```
</details>

<details>
<summary><img src="https://img.shields.io/badge/POST-49cc90?style=flat-square" alt="POST"/> <b>Create a User</b></summary>

```json
POST http://localhost:8090/api/users
Content-Type: application/json

{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "securePass123"
}
```
</details>

<details>
<summary><img src="https://img.shields.io/badge/POST-49cc90?style=flat-square" alt="POST"/> <b>Add to Watchlist</b></summary>

```
POST http://localhost:8090/api/watchlist/1/videos/1
```
</details>

<details>
<summary><img src="https://img.shields.io/badge/POST-49cc90?style=flat-square" alt="POST"/> <b>Record Watch History</b></summary>

```json
POST http://localhost:8090/api/history/1
Content-Type: application/json

{
    "videoId": 1,
    "progressTime": 45,
    "completed": false
}
```
</details>

<details>
<summary><img src="https://img.shields.io/badge/GET-61affe?style=flat-square" alt="GET"/> <b>Get User Stats</b> — Example Response</summary>

```json
{
    "userId": 1,
    "username": "john_doe",
    "totalVideosWatched": 5,
    "totalWatchTimeMinutes": 620,
    "completedVideos": 3,
    "watchlistSize": 8,
    "completionRate": 60.0
}
```
</details>

---

## <img src="https://img.shields.io/badge/-Testing-25A162?style=flat-square" alt="Testing"/> Running Tests

```bash
# Run all tests (34 tests)
mvn test

# Run tests for a specific service
cd video-service && mvn test     # 23 tests
cd user-service && mvn test      # 11 tests
```

<p>
  <img src="https://img.shields.io/badge/tests-34_passed-brightgreen?style=flat-square" alt="Tests"/>
  <img src="https://img.shields.io/badge/video--service-23_tests-blue?style=flat-square" alt="Video Tests"/>
  <img src="https://img.shields.io/badge/user--service-11_tests-orange?style=flat-square" alt="User Tests"/>
</p>

> A Postman collection (`StreamForge.postman_collection.json`) with **33 pre-configured requests** is included in the project root. Import it into Postman for instant API testing.

---

## <img src="https://img.shields.io/badge/-Project_Structure-8e44ad?style=flat-square" alt="Structure"/> Project Structure

```
StreamForge/
├── discovery-service/          # Eureka Server
├── config-service/             # Spring Cloud Config
├── gateway-service/            # API Gateway
├── video-service/              # Video Management
│   └── src/main/java/.../
│       ├── entity/             # JPA entities & enums
│       ├── dto/                # Request/Response DTOs
│       ├── mapper/             # Entity-DTO mappers
│       ├── repository/         # Spring Data repositories
│       ├── service/            # Business logic
│       ├── controller/         # REST controllers
│       └── exception/          # Error handling
├── user-service/               # User Management
│   └── src/main/java/.../
│       ├── entity/
│       ├── dto/
│       ├── mapper/
│       ├── repository/
│       ├── service/
│       ├── controller/
│       ├── client/             # OpenFeign clients
│       └── exception/
├── docker-compose.yml
├── CLASS_DIAGRAM.md
├── StreamForge.postman_collection.json
└── pom.xml                     # Parent POM
```

---

## <img src="https://img.shields.io/badge/-Design_Patterns-2c3e50?style=flat-square" alt="Patterns"/> Design Patterns

<table>
  <tr>
    <td><img src="https://img.shields.io/badge/-Repository-1abc9c?style=flat-square" alt="Repository"/></td>
    <td>Data access abstraction via Spring Data JPA</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/-DTO-3498db?style=flat-square" alt="DTO"/></td>
    <td>Decoupled API layer from persistence layer</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/-Mapper-9b59b6?style=flat-square" alt="Mapper"/></td>
    <td>Clean entity to DTO conversion</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/-Service_Layer-e67e22?style=flat-square" alt="Service"/></td>
    <td>Business logic separation</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/-API_Gateway-e74c3c?style=flat-square" alt="Gateway"/></td>
    <td>Single entry point for all clients</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/-Service_Discovery-2ecc71?style=flat-square" alt="Discovery"/></td>
    <td>Dynamic service registration and discovery</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/-Circuit_Breaker-f39c12?style=flat-square" alt="Fallback"/></td>
    <td>Graceful degradation via Feign fallback</td>
  </tr>
</table>

---

<p align="center">
  <img src="https://img.shields.io/badge/Made_with-Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Built_with-Love-ff6b6b?style=for-the-badge&logo=heart&logoColor=white" alt="Love"/>
</p>
