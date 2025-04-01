# CapstoneBackendProject



# E-commerce Microservices Backend

## Overview
This project is a Microservices-based E-commerce backend built using Spring Boot, deployed on AWS Elastic Beanstalk, and integrated with MySQL, Redis, and Stripe for payments.

## Features
- User Service: Manages user authentication, registration, and profiles.
- Product Service: Handles product listings, categories, pagination, and caching with Redis.
- Payment Service: Processes transactions securely using Stripe.
- Microservices Architecture: Services communicate via REST APIs.
- AWS Cloud Deployment: Deployed using Elastic Beanstalk, EC2, RDS, and VPC.

## Technologies Used
- Spring Boot – For building RESTful microservices.
- MySQL – Database for storing users, products, and transactions.
- Redis – Caching layer to enhance performance.
- Stripe – Payment gateway integration.
- AWS (Elastic Beanstalk, EC2, RDS, VPC, Security Groups) – Cloud deployment.
- Docker – Containerized services for easy deployment.

## Architecture & Request Flow
```
User -> API Gateway -> Microservices -> Database/Cache
```
- API Gateway routes requests to the correct microservice.
- User Service authenticates users and provides tokens.
- Product Service retrieves product data, utilizing Redis caching.
- Payment Service handles payment processing securely via Stripe.

## Installation & Setup
### Prerequisites
- JDK 17+
- MySQL Server
- Redis Server
- AWS Account (for deployment)
- Stripe Account

### Steps to Run Locally
1. Clone the repository:
2. Configure environment variables in `.env` or `application.properties`.
3. Start MySQL and Redis servers.
4. Build and run each microservice

## Deployment on AWS
1. Build JAR files for each service:
   ```sh
   mvn clean package
   ```
2. Upload JARs.
3. Deploy using AWS Elastic Beanstalk.

## API Documentation
- Postman Collection: Add requests to test endpoints.


