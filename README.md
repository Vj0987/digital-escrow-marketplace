# Digital Escrow Marketplace

A **microservices-based Digital Escrow Marketplace** that enables secure transactions between clients and service providers. The platform securely holds client payments in escrow and releases funds only after successful completion and approval of the requested services.

This project is designed following enterprise-level microservices architecture using **Spring Boot**, **Spring Cloud**, **Spring Security**, and **JWT Authentication**.

---

# Architecture

## Current Microservices

* **Eureka Discovery Server** – Service Registry
* **API Gateway** – Centralized Routing & Authentication
* **User Service** – User Management & Authentication
* **Services Service** – Service Catalog Management
* **Booking Service** – Service Booking Management

---

# Planned Microservices

* Escrow Service
* Payment Service
* Notification Service
* Review & Rating Service
* Admin Service

---

# Tech Stack

## Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* JWT Authentication
* Bean Validation
* OpenAPI (Swagger)

## Microservices

* Spring Cloud Gateway
* Eureka Service Discovery
* REST APIs

## Database

* MySQL

## Build Tool

* Maven

---

# Implemented Features

## User Service

* User Registration
* User Login
* JWT Token Generation
* Role-Based Authentication
* Forgot Password using OTP
* OTP Verification
* Password Reset
* Update User Profile
* Get User by ID
* Get User by Email
* Get All Users
* Global Exception Handling
* Swagger API Documentation

---

## Services Service

* Create Service
* Update Service
* Delete Service (Soft Delete)
* Get Service by ID
* Get All Active Services
* Get Services by Provider
* Category Validation
* Provider Authorization
* Swagger API Documentation

---

## Booking Service

* Create Booking
* Booking Validation
* Booking Status Management
* Service Availability Validation
* Booking APIs
* Swagger API Documentation

---

# Security Features

* Spring Security Integration
* JWT-Based Authentication
* Stateless Session Management
* Password Encryption using BCrypt
* Role-Based Authorization
* Protected REST APIs

---

# Microservices Infrastructure

* Eureka Discovery Server
* API Gateway
* Service Registration & Discovery
* Centralized Routing
* Inter-Service Communication Ready

---

# Project Structure

```text
digital-escrow-marketplace/
│
├── cloud/
│   ├── eureka/
│   └── api-gateway/
│
├── users/
│
├── services/
│
└── booking-service/
```

---

# REST APIs

## User Service

| Method | Endpoint                     |
| ------ | ---------------------------- |
| POST   | `/users/register`            |
| POST   | `/users/login`               |
| POST   | `/users/forgotPassword`      |
| POST   | `/users/verifyOtp`           |
| POST   | `/users/resetPassword`       |
| PUT    | `/users/updateProfile`       |
| GET    | `/users/userById/{id}`       |
| GET    | `/users/userByEmail/{email}` |
| GET    | `/users/getAllUsers`         |

---

## Services Service

| Method | Endpoint                                       |
| ------ | ---------------------------------------------- |
| POST   | `/services/create_services`                    |
| PUT    | `/services/update_services`                    |
| DELETE | `/services/delete_service/{serviceId}`         |
| GET    | `/services/getServiceById/{serviceId}`         |
| GET    | `/services/getAllActiveServices`               |
| GET    | `/services/getServicesByProvider/{providerId}` |

---

## Booking Service

| Method | Endpoint                                      |
| ------ | --------------------------------------------- |
| POST   | `/booking/createBooking`                      |
| GET    | `/booking/getBookingById/{bookingId}`         |
| GET    | `/booking/getBookingsByClient/{clientId}`     |
| GET    | `/booking/getBookingsByProvider/{providerId}` |
| PUT    | `/booking/updateBookingStatus/{bookingId}`    |

---

# API Documentation

Swagger UI is available for each microservice.

```
http://localhost:<PORT>/swagger-ui/index.html
```

Replace `<PORT>` with the respective service port.

---

# Future Enhancements

* Escrow Wallet Management
* Payment Gateway Integration
* Milestone-Based Payments
* Refund Management
* Email Notifications
* SMS Notifications
* RabbitMQ Event-Driven Communication
* Distributed Logging
* Docker & Docker Compose
* Oracle Cloud Deployment
* CI/CD Pipeline using GitHub Actions
* Monitoring with Spring Boot Actuator

---

# Learning Objectives

This project is being developed to gain hands-on experience with:

* Enterprise Microservices Architecture
* Spring Boot
* Spring Security
* JWT Authentication
* API Gateway
* Eureka Service Discovery
* Distributed Systems
* REST API Design
* Secure Payment Workflows
* Clean Architecture
* Production-Ready Backend Development

---

# Contributors

* **Vinit Joshi**
* **Sahil Nigam**
* **Aditya Sonawane**
* **Rahul Kumar Pal**

---

# License

This project is developed for educational purposes as part of the **CDAC PG-DAC Final Project**.
