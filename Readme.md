# User Service

This repository contains the **User Service** for a financial microservices platform. The service is built using **Spring Boot**, containerized with **Docker**, and deployed using **Kubernetes** in a microservices environment.

---

# Features

* RESTful User Management APIs
* Spring Boot microservice architecture
* PostgreSQL integration
* Docker containerization
* Kubernetes deployment manifests
* Jenkins CI and ArgoCD 
* Centralized logging support
* Kubernetes-ready configuration

---

# Technology Stack

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* Docker
* Kubernetes
* Jenkins

---

# Project Structure

```bash
User-Service-main/
│
├── Dockerfile
├── Jenkinsfile
├── pom.xml
│
├── k8s/

│   ├── deployment.yaml
│   └── postgres.yaml
│
└── src/
    ├── main/
    │   ├── java/com/financial/userservice/
    │   │   ├── controller/  UserController.java
    │   │   ├── entity/ User.java
    │   │   ├── repository/ UserRepository.java
    │   │   └── UserServiceApplication.java
    │   │
    │   └── resources/
    │       ├── application.yaml
    │       └── logback-spring.xml
    │
    └── test/
        └── java/com/financial/userservice/controller/ UserControllerTest.java
```

---

# Application Overview

The User Service manages user-related operations within the financial platform.

Core responsibilities include:

* Creating users
* Fetching user details
* Updating user information
* Persisting user data in PostgreSQL
* Exposing REST APIs for integration with other microservices

---

# Spring Boot Configuration

Application configuration is located in:

```bash
src/main/resources/application.yaml
```

Typical configurations include:

* Database connection
* Server port
* Logging configuration
* Spring profiles
* Actuator settings

---


# Running the Application Locally

## Prerequisites

Install:

* Java 21+
* Maven
* Docker
* PostgreSQL

---

## Build the Application

```bash
mvn clean install
```

---

## Run the Application


Application starts on:

```bash
http://localhost:8080
```

---

# Docker Setup

The repository contains a Dockerfile for containerization.

## Build Docker Image

```bash
docker build -t user-service .
```

## Run Docker Container

```bash
docker run -p 8080:8080 user-service
```

---

# Kubernetes Deployment

Kubernetes manifests are located in:

```bash
k8s/
```

## Deploy Application

```bash
kubectl apply -f k8s/deployment.yaml
```

## Deploy PostgreSQL

```bash
kubectl apply -f k8s/postgres.yaml
```

---

---

# Jenkins CI/CD

The repository includes a Jenkins pipeline:

```bash
Jenkinsfile
```

Typical pipeline stages:

1. Checkout Code
2. Build Application
3. Run Tests
4. Build Docker Image
5. Push Docker Image
6. Deploy to Kubernetes

---

# Logging

Logging configuration:

```bash
src/main/resources/logback-spring.xml
```

Features:

* Structured logging
* Console logging
* Kubernetes-compatible logs
* ELK stack integration support

---

# Testing

Unit tests are located in:

```bash
src/test/java/
```

Run tests:

```bash
mvn test
```

---

# API Endpoints

The User Service exposes REST APIs for managing users and related operations.
You can verify all available endpoints after running the application using:

```bash
http://localhost:8080/swagger-ui.html
```

or

```bash
http://localhost:8080/v3/api-docs
```

---

# Future Enhancements

Potential improvements:

* JWT Authentication
* OAuth2 integration
* API Gateway integration
* Distributed tracing
* Prometheus integration
* Grafana dashboards
* Kafka event streaming
* Redis caching

---

# Troubleshooting

## Check Pods

```bash
kubectl get pods
```

## View Logs

```bash
kubectl logs <pod-name>
```

## Check Services

```bash
kubectl get svc
```

## Verify Helm Releases

```bash
helm list
```
