# Spring Boot Tech Configurator

API REST d'apprentissage construite avec Spring Boot. Elle gère trois types d'appareils
(`PC`, `Laptop`, `Phone`), expose un CRUD complet pour chacun et stocke les données dans une
base H2 en mémoire via Spring Data JPA.

---

## Table des matières

- [Présentation](#présentation)
- [Prérequis](#prérequis)
- [Stack technique](#stack-technique)
- [Structure du projet](#structure-du-projet)
- [Démarrage](#démarrage)
- [Configuration](#configuration)
- [Modèle de données](#modèle-de-données)
- [Endpoints API](#endpoints-api)
- [Exemples d'utilisation](#exemples-dutilisation)
- [Base de données H2](#base-de-données-h2)
- [Architecture](#architecture)
- [Docker](#docker)
- [Tests](#tests)
- [Dépannage](#dépannage)
- [Licence](#licence)

---

## Présentation

Ce projet est une application Spring Boot simple destinée à illustrer l'architecture en
couches d'une API REST :

```
Controller  ->  Service  ->  Repository  ->  Base de données
```

Chaque type d'appareil possède son propre contrôleur, service et repository :

| Appareil | Endpoint de base |
| --- | --- |
| Ordinateur de bureau | `/pc` |
| Ordinateur portable | `/laptop` |
| Téléphone | `/phone` |

Un endpoint supplémentaire, `/configurator`, permet de créer un appareil en envoyant un seul
JSON contenant un champ `type` qui détermine quel service appeler.

Les trois entités implémentent une interface commune `TechInterface` exposant une méthode
`demarrer()`.

---

## Prérequis

| Outil | Version minimale | Remarque |
| --- | --- | --- |
| JDK | 17 | Obligatoire (le projet cible Java 17) |
| Maven | Non requis | Le wrapper `mvnw` est fourni |
| Docker | 20.10 | Optionnel, uniquement pour le déploiement |
| Postman ou curl | - | Pour tester l'API |

Vérifier la version de Java :

```bash
java -version
```

---

## Stack technique

| Composant | Technologie |
| --- | --- |
| Langage | Java 17 |
| Framework | Spring Boot 4.1.1 |
| Build | Maven (wrapper `mvnw`) |
| Persistance | Spring Data JPA / Hibernate 7 |
| Base de données | H2 (en mémoire) |
| Conteneurisation | Docker (build multi-stage) |

---

## Structure du projet

```
Spring Boot Clean/
├── .mvn/                              # Configuration du wrapper Maven
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java   # Point d'entrée de l'application
│   │   │   ├── controller/            # Points d'entrée HTTP
│   │   │   │   ├── PcController.java
│   │   │   │   ├── LaptopController.java
│   │   │   │   ├── PhoneController.java
│   │   │   │   └── ConfiguratorController.java
│   │   │   ├── service/               # Logique métier
│   │   │   │   ├── PcService.java
│   │   │   │   ├── LaptopService.java
│   │   │   │   ├── PhoneService.java
│   │   │   │   └── ConfiguratorService.java
│   │   │   ├── repository/            # Accès aux données (Spring Data JPA)
│   │   │   │   ├── PcRepository.java
│   │   │   │   ├── LaptopRepository.java
│   │   │   │   └── PhoneRepository.java
│   │   │   └── model/                 # Objets et entités
│   │   │       ├── PC.java
│   │   │       ├── Laptop.java
│   │   │       ├── Phone.java
│   │   │       ├── ConfiguratorRequest.java
│   │   │       └── TechInterface.java
│   │   └── resources/
│   │       └── application.properties # Configuration de l'application
│   └── test/
│       └── java/com/example/demo/
│           └── DemoApplicationTests.java
├── .env                               # Variables d'environnement locales
├── docker-compose.yaml                # Orchestration des conteneurs
├── Dockerfile                         # Construction de l'image
├── mvnw / mvnw.cmd                    # Wrapper Maven
├── pom.xml                            # Dépendances Maven
└── README.md
```

---

## Démarrage

### Compiler et lancer

```bash
# Compilation (sans exécuter les tests)
./mvnw clean package -DskipTests

# Lancement de l'application
./mvnw spring-boot:run
```

Sur Windows, utiliser `mvnw.cmd` à la place de `./mvnw`.

L'application démarre par défaut sur le port **8080**.
Vérification rapide :

```bash
curl http://localhost:8080/pc/hello
```

Réponse attendue : `Hello World!`

---

## Configuration

Le fichier `src/main/resources/application.properties` contient la configuration :

```properties
spring.application.name=demo

# Source de données H2 (en mémoire)
spring.datasource.url=jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

# Console web H2 (développement)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Le fichier `.env` contient des variables utilisées par Docker Compose :

```env
POSTGRES_USER:postgres
POSTGRES_PASSWORD:exemple
POSTGRES_DB_NAME:nomdeladb
```

---

## Modèle de données

Chaque entité correspond à une table dans la base H2.

### PC (table `pc`)

| Champ | Type | Description |
| --- | --- | --- |
| `id` | Long | Clé primaire, auto-générée |
| `brand` | String | Marque |
| `ram` | int | Mémoire vive en Go |
| `gpu` | String | Carte graphique |

### Laptop (table `laptop`)

| Champ | Type | Description |
| --- | --- | --- |
| `id` | Long | Clé primaire, auto-générée |
| `brand` | String | Marque |
| `ram` | int | Mémoire vive en Go |
| `batteryLife` | int | Autonomie en heures |

### Phone (table `phone`)

| Champ | Type | Description |
| --- | --- | --- |
| `id` | Long | Clé primaire, auto-générée |
| `brand` | String | Marque |
| `ram` | int | Mémoire vive en Go |
| `network` | String | Réseau (4G, 5G, ...) |

L'identifiant `id` est généré automatiquement : il ne faut jamais l'envoyer dans un POST.

---

## Endpoints API

Base URL : `http://localhost:8080`

### Vérification

| Méthode | URL | Description | Réponse |
| --- | --- | --- | --- |
| GET | `/pc/hello` | Test du contrôleur PC | `Hello World!` |
| GET | `/laptop/hello` | Test du contrôleur Laptop | `Hello World!` |
| GET | `/phone/hello` | Test du contrôleur Phone | `Hello World!` |

### PC

| Méthode | URL | Description |
| --- | --- | --- |
| GET | `/pc` | Liste tous les PC |
| GET | `/pc/{id}` | Récupère un PC par son identifiant |
| POST | `/pc` | Crée un PC |
| PUT | `/pc/{id}` | Modifie un PC existant |
| DELETE | `/pc/{id}` | Supprime un PC |

Corps de requête (POST / PUT) :

```json
{
  "brand": "Alienware",
  "ram": 64,
  "gpu": "RTX 4090"
}
```

### Laptop

| Méthode | URL | Description |
| --- | --- | --- |
| GET | `/laptop` | Liste tous les laptops |
| GET | `/laptop/{id}` | Récupère un laptop par son identifiant |
| POST | `/laptop` | Crée un laptop |
| PUT | `/laptop/{id}` | Modifie un laptop existant |
| DELETE | `/laptop/{id}` | Supprime un laptop |

Corps de requête (POST / PUT) :

```json
{
  "brand": "MacBook Pro",
  "ram": 32,
  "batteryLife": 18
}
```

### Phone

| Méthode | URL | Description |
| --- | --- | --- |
| GET | `/phone` | Liste tous les téléphones |
| GET | `/phone/{id}` | Récupère un téléphone par son identifiant |
| POST | `/phone` | Crée un téléphone |
| PUT | `/phone/{id}` | Modifie un téléphone existant |
| DELETE | `/phone/{id}` | Supprime un téléphone |

Corps de requête (POST / PUT) :

```json
{
  "brand": "iPhone",
  "ram": 6,
  "network": "5G"
}
```

### Configurator

Un seul point d'entrée pour créer n'importe quel appareil. Le champ `type` détermine le service
utilisé.

| Méthode | URL | Description |
| --- | --- | --- |
| POST | `/configurator` | Crée un appareil selon le champ `type` |

Valeurs acceptées pour `type` : `PC`, `LAPTOP`, `PHONE` (insensible à la casse).

Exemples de corps :

```json
{ "type": "PC",     "brand": "MSI",      "ram": 16, "gpu": "RTX 4060" }
```

```json
{ "type": "LAPTOP", "brand": "Dell XPS", "ram": 32, "batteryLife": 10 }
```

```json
{ "type": "PHONE",  "brand": "Samsung",  "ram": 8,  "network": "5G" }
```

### Codes de réponse

Cette version simplifiée renvoie toujours le code `200` en cas de succès.

| Situation | Comportement |
| --- | --- |
| Création réussie | `200` avec l'objet créé (id inclus) |
| Lecture d'un id inexistant | `200` avec un corps vide |
| Suppression d'un id inexistant | `200` (aucune erreur) |
| Champ `type` manquant ou inconnu (`/configurator`) | `500` avec un message d'erreur |

---

## Exemples d'utilisation

### Créer un PC

```bash
curl -X POST http://localhost:8080/pc \
  -H "Content-Type: application/json" \
  -d '{"brand":"Alienware","ram":64,"gpu":"RTX 4090"}'
```

### Lister tous les PC

```bash
curl http://localhost:8080/pc
```

### Récupérer un PC par son identifiant

```bash
curl http://localhost:8080/pc/1
```

### Modifier un PC

```bash
curl -X PUT http://localhost:8080/pc/1 \
  -H "Content-Type: application/json" \
  -d '{"brand":"Alienware","ram":128,"gpu":"RTX 5090"}'
```

### Supprimer un PC

```bash
curl -X DELETE http://localhost:8080/pc/1
```

### Créer un laptop via le configurator

```bash
curl -X POST http://localhost:8080/configurator \
  -H "Content-Type: application/json" \
  -d '{"type":"LAPTOP","brand":"Dell XPS","ram":32,"batteryLife":10}'
```

### Tester avec Postman

1. Créer une nouvelle requête HTTP.
2. Choisir la méthode (`GET`, `POST`, `PUT`, `DELETE`) et saisir l'URL.
3. Pour `POST` et `PUT`, ouvrir l'onglet **Body**, sélectionner **raw** puis **JSON** et coller le corps.
4. Envoyer la requête.

---

## Base de données H2

La base est en mémoire : les données sont perdues à l'arrêt de l'application.

Une console web est disponible pour consulter les tables :

- URL : `http://localhost:8080/h2-console`

Paramètres de connexion :

| Champ | Valeur |
| --- | --- |
| JDBC URL | `jdbc:h2:mem:demo` |
| User Name | `sa` |
| Password | (laisser vide) |

Exemple de requête :

```sql
SELECT * FROM PC;
```

---

## Architecture

### Rôle de chaque couche

| Couche | Dossier | Rôle |
| --- | --- | --- |
| Controller | `controller/` | Reçoit les requêtes HTTP et renvoie du JSON |
| Service | `service/` | Contient la logique métier |
| Repository | `repository/` | Accès à la base de données |
| Model | `model/` | Objets et entités manipulés |

### Cycle d'une requête

Exemple avec `POST /pc` :

```
Client (Postman / curl)
        |
        v
PcController        -> recoit le JSON et le convertit en objet PC
        |
        v
PcService           -> applique la logique metier
        |
        v
PcRepository        -> save() fourni par Spring Data
        |
        v
Hibernate / H2      -> INSERT INTO pc (...)
```

### Repository

Les repositories sont de simples interfaces. Spring Data JPA génère automatiquement le code SQL.

```java
public interface PcRepository extends JpaRepository<PC, Long> {
    // save(), findAll(), findById(), deleteById() sont fournis par defaut
}
```

### Interface TechInterface

Les trois entités partagent un contrat commun :

```java
public interface TechInterface {
    void demarrer();
}
```

`ConfiguratorService` renvoie un `TechInterface`, ce qui permet de manipuler n'importe quel
appareil de la même manière.

---

## Docker

### Docker Compose

```bash
docker-compose up -d --build
docker-compose logs -f
docker-compose down
```

Avertissement : le fichier `docker-compose.yaml` fourni référence un service PostgreSQL
(`db`), alors que l'application utilise H2 en mémoire par défaut. Il faut adapter la
configuration si l'on souhaite réellement utiliser PostgreSQL.

### Docker CLI

```bash
docker build -t springboot-tech-configurator .
docker run -p 8080:8080 springboot-tech-configurator
```

Le `Dockerfile` utilise un build multi-stage :

1. Étape de build : image `maven:3.9.6-eclipse-temurin-17` pour compiler et produire le JAR.
2. Étape d'exécution : image `eclipse-temurin:17-jre-alpine`, légère, pour lancer le JAR.

---

## Tests

```bash
./mvnw test
```

Le test `DemoApplicationTests#contextLoads()` vérifie que le contexte Spring démarre sans
erreur, donc que toutes les couches sont correctement câblées.

---

## Dépannage

### "No compiler is provided in this environment"

Le projet nécessite un **JDK 17 ou supérieur**, pas seulement un JRE. Vérifier que
`JAVA_HOME` pointe vers un JDK :

```bash
echo $JAVA_HOME
javac -version
```

Sous Windows, dans l'invite de commandes :

```bat
echo %JAVA_HOME%
```

Si `javac` est introuvable, installer un JDK (Temurin, Oracle, etc.) et définir `JAVA_HOME`
dessus.

### Le port 8080 est déjà utilisé

Modifier le port dans `application.properties` :

```properties
server.port=8081
```

---

## Licence

Ce projet est distribué sous licence MIT. Voir le fichier [LICENSE](LICENSE).
