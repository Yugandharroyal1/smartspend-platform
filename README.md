# SmartSpend

Grocery price comparison platform. Search a product and see prices across stores, with the cheapest highlighted.

## Tech stack
Java 17, Spring Boot 3, Spring Data JPA, MySQL, React

## Data
Kroger prices are live through the official Kroger API (OAuth2, hourly scheduled ingestion). Other stores currently use sample data.

## Run locally
1. Start MySQL and create database smartspend_db
2. Set the environment variable KROGER_CLIENT_SECRET
3. Run ./mvnw spring-boot:run in the project root
4. Run npm start in the frontend folder

## Roadmap
Price history, price alerts, Redis caching, tests, deployment
