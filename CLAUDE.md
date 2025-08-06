# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.


# AI Guide

- 항상 한글로 대답할 것
- DRY,YAGNI,KISS,SOLID 원칙을 준수하여 코드를 생성할 것
- 가독성을 고려하여 함수 및 단락마다 주석을 달 것
- 너무 많은 코드나 함수를 생성하는건 지양할 것
- html을 구현할 땐 px고정값을 사용하는걸 지양하고 %와 vh사용으로 동적 화면 레이아웃을 구성할 것

# Development Environment
- Mariadb
  - 129.154.53.217:32001
  - Database: soron
  - soronprfbs / 801eheh2

- Redis
  - 129.154.53.217:32002

## Project Overview

This is a Spring Boot 3.5.4 web application built with Java 21 and Gradle. The project uses:

- **Framework**: Spring Boot with Spring Web MVC
- **Database**: MariaDB with MyBatis for data access
- **Caching**: Redis integration via Spring Data Redis
- **Build Tool**: Gradle with Gradle Wrapper
- **Deployment**: WAR packaging for traditional servlet containers
- **Development**: Spring Boot DevTools, Lombok for code generation
- **Monitoring**: Spring Boot Actuator for health checks and metrics

## Key Commands

### Build & Run
```bash
# Build the project
./gradlew build

# Run the application (development mode)
./gradlew bootRun

# Build WAR file for deployment
./gradlew bootWar
```

### Testing
```bash
# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests "com.soron.SoronApplicationTests"

# Run tests with continuous build
./gradlew test --continuous
```

### Development
```bash
# Clean build
./gradlew clean build

# Check for dependency updates
./gradlew dependencyUpdates

# Generate dependency report
./gradlew dependencies
```

## Architecture

### Package Structure
- `com.soron` - Root package containing main application class
- `SoronApplication.java` - Main Spring Boot application entry point
- `ServletInitializer.java` - WAR deployment configuration for servlet containers

### Key Dependencies
- **Spring Boot Starter Web** - REST API and web layer
- **Spring Boot Starter Data Redis** - Redis caching support
- **MyBatis Spring Boot Starter** - SQL mapping framework
- **MariaDB Driver** - Database connectivity
- **Spring Boot Starter Validation** - Bean validation
- **Spring Boot Starter Actuator** - Production monitoring endpoints

### Configuration
- `application.properties` - Main configuration file (currently minimal with just app name)
- Application supports both embedded Tomcat (JAR) and external servlet container (WAR) deployment

## Development Notes

- Java 21 language features are available
- Lombok is configured for reducing boilerplate code
- MyBatis is used for database operations rather than JPA/Hibernate
- Redis integration is available for caching and session management
- Spring Boot DevTools provides hot reloading during development
- Actuator endpoints are available for monitoring and health checks

When adding new features, follow the existing package structure under `com.soron` and leverage the configured frameworks (MyBatis for data access, Redis for caching, Spring Web for REST endpoints).