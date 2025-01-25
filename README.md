# Wallet Management RESTful API

## Overview
A Spring Boot-based RESTful API for managing digital wallets with CRUD operations, transaction tracking, and balance management.

## Features
- Create wallets for users
- Deposit and withdraw funds
- Retrieve wallet details and transactions
- Update wallet information
- Delete wallet with preservation

## Endpoints

### Wallet Management
- **GET** `/api/wallets/{id}`: Retrieve wallet details
- **POST** `/api/wallets`: Create a new wallet
- **PUT** `/api/wallets/{id}`: Update wallet name
- **DELETE** `/api/wallets/{id}`: Mark wallet as deleted

### Financial Operations
- **GET** `/api/wallets/{id}/balance`: Get wallet balance
- **POST** `/api/wallets/{id}/deposit`: Deposit funds
- **POST** `/api/wallets/{id}/withdraw`: Withdraw funds
- **GET** `/api/wallets/{id}/transactions`: List wallet transactions

## Error Handling
The API provides structured error responses for:
- Invalid entity data
- Non-existent entities
- Validation errors
- General exceptions

## Validation Constraints
- **Wallet name**: Non-blank
- **Transaction amount**: Positive value
- **Balance**: Non-negative

## Transaction Types
- `DEPOSIT`
- `WITHDRAWAL`

## Status Management
Wallets can have statuses:
- `ACTIVE`
- `DELETED`

## Technologies
- IntelliJ IDEA
- Spring Boot
- Spring Framework
- Lombok
- MySQL
- Mockito

## Testing
Used JUnit and Mockito to write unit tests for the service class.

## Set Up and Testing Instructions
1. **Add Dependencies**: 
   - Add the necessary dependencies (or use the provided `build.gradle` file) to install and manage them using Gradle.
2. **Database Setup**:
   - Ensure you have MySQL and a connection with permissions to create a database, or create a database named `user_wallet` (you can modify this in the `application.properties` file).
   - Configure the username and password for your MySQL connection in `application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/user_wallet
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```
3. **Start the Application**:
   - Use IntelliJ IDEA to run the application.
4. **API Testing**:
   - Use Postman to test CRUD operations by sending HTTP requests to the API endpoints.
   - Provide a JSON body with fields matching the DTO classes in the source code for the requests you make.
5. **Unit Testing**:
   - Run unit tests directly from IntelliJ IDEA by navigating to the test classes and executing them.
