# App

This application provides a RESTful API with several endpoints for managing users and projects.

## How to Run

### 1. Clone the repository

```bash
git clone https://github.com/mariacasanova/app.git
cd app
```

### 2. Build the project

```bash
./gradlew build
```

### 3. Run the Cassandra database with Docker
```bash
docker-compose up cassandra --build
```

### 4. Run the application
With gradle:
```bash
./gradlew bootRun
```
Or with Docker:

```bash
docker-compose up app --build
```

### 5. Access the application

- The app will be running at http://localhost:8080

### 6. Run tests

If you use Gradle:

```bash
./gradlew test
```

## Run all containers with Docker 

```bash
docker-compose up --build
```
