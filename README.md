# Sensors Project

## Prerequisites

In order to run the application you need to have following sofware/packages
installed in your system:

- Docker & docker-compose
- JDK 11 



## Running the database

Before running the application you have to run the database:
```
cd ./docker
docker-compose up -d
```
Above command will bootstrap MongoDB instance & Mongo Express (administration panel)

## Running the application
In the project's root directory run"
```
./mvnw spring-boot:run
```

## Running tests
```
./mvnw clean test
```

## Accessing MongoDB administration panel
Open http://localhost:8081/