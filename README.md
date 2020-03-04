# Product API
A simple REST API to manage products.

## Table of contents
- [ Requirements ](#requirements)
- [ Solution ](#solution)
    - [ Architecture ](#architecture)
    - [ Technologies ](#technologies)
    - [ Prerequisites ](#prerequisites)
    - [ Build and Deployment ](#build-and-deployment)
    - [ API Documentation ](#api-documentation)
    - [ API URL ](#api-url)
    

## Requirements
Design a REST PRODUCT API that can manage CRUD operations. More details are provided by Sitoo documentations.

## Solution

### Architecture
Per the requirement, I have designed a Java based REST API /api/products

### Technologies
- Java 11
- SpringBoot 2.2.4
- MySQL DB

### Prerequisites
- MySQL DB

### Build and Deployment
**Configurations**

```shell
  Update src/main/resources/application.properties file with DB configurations

  spring.datasource.url=jdbc:mysql://localhost:32771/sitoo_test_assignment
  spring.datasource.username=root
  spring.datasource.password=pass
```

**Maven**

```shell
  cd product-api
  mvn clean package
  mvn spring-boot:run
```
### API Documentation

```shell
  http://localhost:9090/swagger-ui.html
```
### API URL

```shell
  http://localhost:9090/api/products
```