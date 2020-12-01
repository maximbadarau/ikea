# Ikea Test Assignment

 ## Intro: 
 This is a distributed architecture environment playground.
 
 ## Build with:
 * [Spring](https://spring.io/): The Spring Framework is an application framework and inversion of control container for the Java platform. The framework's core features can be used by any Java application, but there are extensions for building web applications on top of the Java EE platform.
 * [Lombok](https://projectlombok.org/): Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
 * [Guava](https://github.com/google/guava): Guava is a set of core libraries that includes new collection types (such as multimap and multiset), immutable collections, a graph library, functional types, an in-memory cache, and APIs/utilities for concurrency, I/O, hashing, primitives, reflection, string processing, and much more!
 * [Spring Boot](https://spring.io/projects/spring-boot): Spring Boot aims to make it easy to create Spring-powered, production-grade applications and services with minimum fuss. It takes an opinionated view of the Spring platform so that new and existing users can quickly get to the bits they need
 * [Maven](https://maven.apache.org/) - Apache Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.
 

### Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

#### Prerequisites

In order to run project your need:
* [MongoDB](https://www.mongodb.com/) - Preferably latest. 
* [Docker](https://www.docker.com/get-started) - Engine: 19.03.2
* [Maven](https://maven.apache.org/) - Maven 3.6.1
* [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java (JDK 8, preferably update 211) 
 

#### Dependencies
* [QueryDSL](http://www.querydsl.com/) - Querydsl is a framework which enables the construction of type-safe SQL-like queries for multiple backends including JPA, MongoDB and SQL in Java.
* [Hibernate](http://hibernate.org/) - Domain model persistence for relational databases; and not only.
* [Swagger](https://swagger.io/docs/) - Interactive API documentation
* [Lombok](https://projectlombok.org/) - Java library that automatically plugs into your editor and build tools
* [Guava](https://github.com/google/guava) - Google Core Libraries for Java
* [HATEOAS](https://restfulapi.net/hateoas/) - Hypermedia as the Engine of Application State
* [Spring Data](https://spring.io/projects/spring-data) - Spring Data’s mission is to provide a familiar and consistent, Spring-based programming model for data access while still retaining the special traits of the underlying data store.
* And others

#### Installing

Listed commands bellow are executed through terminal.
 * Navigate to root folder of price project
 ```
 cd ikea/
```
 * Execute in root folder of assignment project:
 ```
 docker-compose up -d
 ```
 * Execute in root folder of assignment project:
 ```
 mvn spring-boot:run
 ```
 * REST API documentation available at:
 ```
 http://localhost:8080/swagger-ui.html
```
### Guide
* For testing purpose might be used ```http://localhost:8080/swagger-ui.html``` with self-explanatory examples and notes.
* For rendering use: ```http://localhost:8080/inventories/render``` and ```http://localhost:8080/products/render```
* Generated examples should be enough.
### Deployment
 * Current state: Manual

### Authors
* **Maxim B.** 

### Nota Bene
* MongoDb have been chosen in order to be capable supporting multiple formats, missing or malformed or corrupted data. Although right now implementation is coupled to schema definition, there is a room for improvements and further development.
* I have tried to provide as more than possible mature code, with decent test coverage and diverse functionality. I do believe that is a good manner to show problem solving skills, design analyses besides the implementation of the clean code
* For cards type there two distinct controllers although they are sharing same repository with factory method for service layer selection.
* There are divers validation rules and groups in order to provide mature API definition.
* CORS it's not implemented, but there is a room for.  
* HAL have been also implemented.
* Removed collection key in order to exclude wrapper POJOs for uploading from JSON files.
* There is a fancy banner with: Ikea :) 
* For any questions ask for my contact details.
* Also, would like to thank for this opportunity, not sure if I would have another chance (in case of system generated unfortunately letter)
