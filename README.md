# Digital Escrow Marketplace

A microservices-based Digital Escrow Marketplace that enables secure transactions between clients and service providers. The platform holds payments in escrow and releases funds only after successful completion and approval of services.

## Architecture

Current Microservices:

* Eureka Discovery Server
* API Gateway
* User Service

Future Microservices:

* Service Catalog Service
* Order Service
* Escrow Service
* Payment Service
* Notification Service

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate

### Microservices

* Eureka Service Discovery
* Spring Cloud Gateway
* REST APIs

### Database

* MySQL

### Build Tool

* Maven

## Implemented Features

### User Service

* User Registration
* User Authentication
* JWT Token Generation
* Get User By ID
* Get User By Email
* Get All Users

### Security

* Spring Security Integration
* JWT Based Authentication
* Stateless Session Management

### Microservices Infrastructure

* Eureka Server Configuration
* API Gateway Configuration
* Service Registration and Discovery

## Project Structure

digital-escrow-marketplace/

├── cloud/                 # Eureka Discovery Server

├── api-gateway/           # API Gateway

└── users/                 # User Service

## APIs Implemented

### User APIs

POST /users/register

POST /users/getToken

GET /users/authenticate/userById/{id}

GET /users/authenticate/userByEmail/{email}

GET /users/authenticate/allUsers

## Future Enhancements

### Service Catalog Service

* Service Creation
* Service Categories
* Service Search

### Escrow Management

* Create Escrow Transactions
* Hold Payments
* Release Payments
* Refund Handling

### Notifications

* Email Notifications
* Payment Updates
* Service Status Updates

### Messaging

* RabbitMQ Integration
* Event-Driven Communication

## Learning Objectives

This project is being developed to gain hands-on experience with:

* Microservices Architecture
* Spring Security
* JWT Authentication
* Service Discovery
* API Gateway
* Distributed Systems
* Secure Payment Workflows
* Enterprise Java Development

## Author

Vinit Joshi |
Sahil Nigam |
Aditya Sonawane |
Rahul Kumar Pal

Java Backend Developer | Spring Boot | Microservices | REST APIs
