<div align="center">

# 🖥️ Spring Boot Tech Configurator

**Une API REST pédagogique qui modélise des appareils (PC, Laptop, Phone) et les persiste en base via Spring Data JPA / Hibernate.**

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/17/)
[![H2](https://img.shields.io/badge/H2-In--Memory-4479A1?style=for-the-badge&logoColor=white)](https://www.h2database.com)
[![Docker](https://img.shields.io/badge/Docker-Multi--stage-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

[Présentation](#-présentation) • [Stack](#-stack-technique) • [Démarrage](#-démarrage-rapide) • [Docker](#-déploiement-docker) • [API](#-endpoints-api) • [Architecture](#-architecture)

</div>

---

## 📌 Présentation

Ce projet est une **API REST Spring Boot** qui met en pratique l'architecture en couches (Controller → Service → Repository → Model) avec **Spring Data JPA** et une base **H2 in-memory**.

L'idée : un endpoint unique `/configurator` reçoit un JSON décrivant un appareil (`type` + champs) et route vers le bon service pour créer et persister l'entité correspondante (PC, Laptop ou Phone). Tous les appareils implémentent une interface commune `TechInterface` avec une méthode `demarrer()`.

> ⚠️ **Ce dépôt est un projet d'apprentissage.** Pas de JWT, pas de Spring Security, pas de PostgreSQL — uniquement le strict nécessaire pour comprendre le cycle de vie d'une requête Spring Boot jusqu'à la base.

---

## ✨ Fonctionnalités

* 🧩 **Architecture en couches** : `model` / `repository` / `service` / `controller`
* 💾 **Persistance JPA** : trois entités (`PC`, `Laptop`, `Phone`) mappées sur trois tables H2
* 🔌 **Endpoint unique `/configurator`** : un seul POST qui route selon le champ `type`
* 🧪 **Test smoke Spring Boot** : `DemoApplicationTests.contextLoads()` valide le contexte au démarrage
* 🐳 **Docker multi-stage** : image finale basée sur `eclipse-temurin:17-jre-alpine`
* 🛢️ **Console H2** activée en dev pour explorer la base depuis le navigateur

---

## 🛠️ Stack Technique

| Composant | Technologie |
| :--- | :--- |
| **Langage** | Java 17 (LTS) |
| **Framework** | Spring Boot 4.1.1 |
| **Build** | Maven (via `./mvnw`) |
| **ORM** | Spring Data JPA / Hibernate 7 |
| **Base de données** | H2 in-memory (`jdbc:h2:mem:demo`) |
| **Conteneurisation** | Docker (multi-stage) & Docker Compose |

---

## 📂 Structure du Projet

```text
Spring Boot Clean/
├── .mvn/                              # Wrapper Maven
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java   # Point d'entrée (@SpringBootApplication)
│   │   │   ├── controller/            # Endpoints HTTP
│   │   │   │   ├── PcController.java
│   │   │   │   ├── LaptopController.java
│   │   │   │   ├── PhoneController.java
│   │   │   │   └── ConfiguratorController.java
│   │   │   ├── service/               # Logique métier
│   │   │   │   ├── PcService.java
│   │   │   │   ├── LaptopService.java
│   │   │   │   ├── PhoneService.java
│   │   │   │   └── ConfiguratorService.java
│   │   │   ├── repository/            # Spring Data JPA
│   │   │   │   ├── PcRepository.java
│   │   │   │   ├── LaptopRepository.java
│   │   │   │   └── PhoneRepository.java
│   │   │   └── model/                 # Entités JPA + TechInterface
│   │   │       ├── PC.java
│   │   │       ├── Laptop.java
│   │   │       ├── Phone.java
│   │   │       ├── ConfiguratorRequest.java
│   │   │       └── TechInterface.java
│   │   └── resources/
│   │       └── application.properties # Config Spring / H2 / JPA
│   └── test/
│       └── java/com/example/demo/
│           └── DemoApplicationTests.java
├── .env                               # Variables d'environnement (local)
├── docker-compose.yaml                # Stack API + Postgres (compose prêt)
├── Dockerfile                         # Build multi-stage
├── mvnw / mvnw.cmd                    # Wrapper Maven
└── pom.xml                            # Dépendances Maven
```

---

## ⚙️ Configuration

### `application.properties` (extrait)

```properties
spring.application.name=demo

# --- H2 in-memory datasource ---
spring.datasource.url=jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=

# --- JPA / Hibernate ---
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true

# --- Console H2 (dev only) ---
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### `.env` (variables d'environnement locales)

```env
POSTGRES_USER=postgres
POSTGRES_PASSWORD=exemple
POSTGRES_DB_NAME=nomdeladb
```

---

## 🚀 Démarrage Rapide

### Pré-requis

| Outil | Version |
| :--- | :--- |
| JDK | 17+ |
| Maven | via `./mvnw` (aucune installation requise) |
| Docker (optionnel) | 20.10+ |

### 1. Lancer en local

```bash
# Compiler
./mvnw clean package -DskipTests

# Démarrer
./mvnw spring-boot:run
```

> Sur Windows, utilise `./mvnw.cmd` à la place de `./mvnw`.

L'application démarre sur **`http://localhost:8080`**.

### 2. Vérifier que ça tourne

```bash
curl http://localhost:8080/pc/hello
curl http://localhost:8080/laptop/hello
curl http://localhost:8080/phone/hello
```

Tu dois obtenir `Hello World!` pour chaque appel.

---

## 📡 Endpoints API

### Smoke tests (GET)

| Méthode | URL | Réponse |
| :--- | :--- | :--- |
| `GET` | `/pc/hello` | `"Hello World!"` |
| `GET` | `/laptop/hello` | `"Hello World!"` |
| `GET` | `/phone/hello` | `"Hello World!"` |

### Création directe (POST)

| Méthode | URL | Body JSON |
| :--- | :--- | :--- |
| `POST` | `/pc` | `{ "brand": "Alienware", "ram": 64, "gpu": "RTX 4090" }` |
| `POST` | `/laptop` | `{ "brand": "MacBook Pro", "ram": 32, "batteryLife": 18 }` |
| `POST` | `/phone` | `{ "brand": "iPhone", "ram": 6, "network": "5G" }` |

### Endpoint unifié `/configurator`

Un seul POST, le champ `type` (`PC`, `LAPTOP` ou `PHONE`) route vers le bon service.

| Méthode | URL | Body JSON |
| :--- | :--- | :--- |
| `POST` | `/configurator` | `{ "type": "PC", "brand": "...", "ram": ..., "gpu": "..." }` |
| `POST` | `/configurator` | `{ "type": "LAPTOP", "brand": "...", "ram": ..., "batteryLife": ... }` |
| `POST` | `/configurator` | `{ "type": "PHONE", "brand": "...", "ram": ..., "network": "..." }` |

> 💡 Ne jamais envoyer `id` dans le JSON : il est auto-généré par Hibernate.

### Exemples avec `curl`

```bash
# PC
curl -X POST http://localhost:8080/configurator \
  -H "Content-Type: application/json" \
  -d '{"type":"PC","brand":"Alienware","ram":64,"gpu":"RTX 4090"}'

# Laptop
curl -X POST http://localhost:8080/configurator \
  -H "Content-Type: application/json" \
  -d '{"type":"LAPTOP","brand":"MacBook Pro","ram":32,"batteryLife":18}'

# Phone
curl -X POST http://localhost:8080/configurator \
  -H "Content-Type: application/json" \
  -d '{"type":"PHONE","brand":"iPhone","ram":6,"network":"5G"}'
```

### Tester avec Postman

1. **New → HTTP Request**.
2. Méthode = `POST`, URL = `http://localhost:8080/configurator`.
3. Onglet **Body → raw → JSON**.
4. Coller le JSON ci-dessus.
5. **Send**.

### Explorer la base H2

Ouvre `http://localhost:8080/h2-console` :

| Champ | Valeur |
| :--- | :--- |
| JDBC URL | `jdbc:h2:mem:demo` |
| User Name | `sa` |
| Password | *(vide)* |

Tu peux ensuite faire `SELECT * FROM PC;` (et `LAPTOP`, `PHONE`) pour voir les données créées.

---

## 🐳 Déploiement Docker

### Option 1 — Docker Compose

```bash
docker-compose up -d --build
docker-compose logs -f
docker-compose down
```

> ⚠️ Le `docker-compose.yaml` fourni référence Postgres mais l'application utilise H2 in-memory par défaut. Adapte le `compose` (ou les variables d'env Spring) si tu veux switcher vers Postgres.

### Option 2 — Docker CLI

```bash
docker build -t springboot-tech-configurator .
docker run -p 8080:8080 springboot-tech-configurator
```

L'image finale est basée sur `eclipse-temurin:17-jre-alpine` (~200 Mo).

---

## 🏗️ Architecture

### Cycle d'une requête `POST /configurator`

```text
Client (Postman / curl)
  │
  ▼
ConfiguratorController       ←  Reçoit le JSON, le valide
  │
  ▼
ConfiguratorService          ←  Route selon `type` (PC / LAPTOP / PHONE)
  │
  ▼
PcService / LaptopService / PhoneService
  │                            ←  Construit l'entité + applique la logique métier
  ▼
PcRepository / LaptopRepository / PhoneRepository
  │                            ←  Délègue à Spring Data JPA
  ▼
Hibernate / JDBC
  │
  ▼
Base H2 (en mémoire)
```

### Pourquoi `TechInterface` ?

```java
public interface TechInterface {
    void demarrer();
}

public class PC    implements TechInterface { ... }
public class Laptop implements TechInterface { ... }
public class Phone  implements TechInterface { ... }
```

Toutes les entités partagent un contrat commun. `ConfiguratorService` retourne un `TechInterface` côté API, ce qui permet de manipuler n'importe quel appareil de manière polymorphe côté client.

---

## 🧪 Tests

```bash
./mvnw test
```

Lance `DemoApplicationTests#contextLoads()` qui valide que le contexte Spring Boot démarre sans erreur (donc que toutes les couches sont correctement câblées : repositories JPA, scan des composants, injection de dépendances).

---

## 📌 Roadmap

- [ ] `GET /pc`, `GET /laptop`, `GET /phone` (lister tous)
- [ ] `GET /pc/{id}` (lecture unitaire)
- [ ] `PUT` / `DELETE` pour les trois types
- [ ] Vraie intégration Postgres via Spring profiles (`dev` / `prod`)
- [ ] Validation Jakarta (`@Valid`, `@NotNull`) sur `ConfiguratorRequest`
- [ ] Tests d'intégration MockMvc

---

## 📄 Licence

MIT — voir [LICENSE](LICENSE).

---

<div align="center">

</div>