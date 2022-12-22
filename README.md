# Sample Quarkus-Postgres using Reactive pattern

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

Start database in a container
```shell script
docker run -it --rm=true --name quarkus_test -e POSTGRES_USER=quarkus -e POSTGRES_PASSWORD=MyPassword -e POSTGRES_DB=mydatabase -p 5432:5432 postgres:15.1-alpine
```
or start db and cache (cache implementation TODO) using Docker Compose
```shell script
docker compose up
```

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw clean compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw clean package
```
