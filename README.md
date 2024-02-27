# Car Rental (REST API)


## Table of Contents
* [General Info](#general-info)
* [Tech Stack](#tech-stack)
* [Run Localy](#run-locally)
* [Environment Variables](#environment-variables)
* [Features](#features)
* [Used Libraries with Examples](#used-libraries-with-examples)
* [Spring Technology Implementation with Examples](#spring-technology-implementation-with-examples)
* [Licenses](#licenses)


## General Info
This project is a simple REST API designed for a car rental service. An unauthorized client, by sending a request to a specific endpoint, receives information about available cars. A client who verifies their identity gains the ability to reserve a car, while an authenticated employee oversees the rentals. The administrator is responsible for updating the list of cars and employees.


## Tech Stack
**Backend:** Java 21 with Spring Boot 3.2.2

**Build tool:** Maven 3.9.6 

**Database:** MySQL 8.0.35, H2 2.2.224

**Authentication Server:** Keycloak 23.0.3 


## Run Locally
Before running this project locally, make sure you have the following installed:
- JDK (Java Development Kit) - version 21
- Maven 3.9.6
- A local MySQL database running on port 3306 with the following authentication data: username: root, password: password.
- A local instance of the Keycloak server, which should be pre-configured by importing the “realm-export.json” configuration file.

Clone the project  
```bash
  git clone https://github.com/WojciechK92/car-rental-rest_api.git
```
Go to the project directory
```bash
  cd car-rental-rest_api
```
Build the project with Maven
```bash
  mvn clean install
```
Start the server
```bash
  mvn spring-boot:run
```


## Environment Variables
To run this project locally, you don’t need any additional variables.

To run this project in a production environment, you need to add the following environment variables during the execution process:
- server.port=80
- spring.profiles.active=prod
- spring.datasource.url
- spring.datasource.username
- spring.datasource.password

## Features
|  Endpoint           | Method |         Action          |  Parameter                     |             Available Parameter Values            |
|:-------------------:|:------:|:-----------------------:|:------------------------------:|:-------------------------------------------------:|
| /cars/{id}          |   GET  | Fetch one car           |          -                     |                      -                            |
| /cars               |   GET  | Fetch car list          | status (RequestParam)          | AVAILABLE, RESERVED, RENTAL, IN_SERVICE, INACTIVE |
| /cars               |   POST | Create a new car        | Car details (RequestBody)      | make, model, productionYear, status, pricePerDay  |
| /cars/{id}          |   PUT  | Update car              | Car details (RequestBody)      | make, model, productionYear, status, pricePerDay  |
| /cars/{id}          | PATCH  | Update car status       | status (RequestParam)          | AVAILABLE, RESERVED, RENTAL, IN_SERVICE, INACTIVE |
| /clients/{id}       |   GET  | Fetch one client        |          -                     |                      -                            |
| /clients            |   GET  | Fetch client   list     |          -                     |                      -                            |
| /clients            |   POST | Create a new client     | Client details (RequestBody)   | firstName, lastName, email, tel, status           |
| /clients/{id}       |   PUT  | Update client           | Client details (RequestBody)   | firstName, lastName, email, tel, status           |
| /clients/{id}       | PATCH  | Update client   status  | status (RequestParam)          | ACTIVE, INACTIVE, BLOCKED                         |
| /employees/{id}     |   GET  | Fetch one employee      |          -                     |                      -                            |
| /employees          |   GET  | Fetch employee list     |          -                     |                      -                            |
| /employees          |   POST | Create a new employee   | Employee details (RequestBody) | firstName, lastName, email, tel, status           |
| /employees/{id}     |   PUT  | Update employee         | Employee details (RequestBody) | firstName, lastName, email, tel, status           |
| /employees/{id}     | PATCH  | Update employee status  | status (RequestParam)          | EMPLOYED, ON_LEAVE, DISMISSED                     |
| /rentals/{id}       |   GET  | Fetch one rental        |          -                     |                      -                            |
| /rentals            |   GET  | Fetch rental   list     |          -                     |                      -                            |
| /rentals            |   POST | Create a new rental     | Rental details (RequestBody)   | firstName, lastName, email, tel, status           |
| /rentals/{id}       |   PUT  | Update rental           | Rental details (RequestBody)   | firstName, lastName, email, tel, status           |
| /rentals/{id}/close | PATCH  | Close rental            |             -                  |                     -                             |
| /rentals/{id}/cancel| PATCH  | Cancel rental           |             -                  |                     -                             |
| /login              | POST   | Login user              | User details (RequestBody)     | username, password                                |

Endpoints used for fetching a list of items have built-in pagination. As @RequestParam, you should provide size, page, sort.

You can login as:
- client (username: "client", password: "client")
- employee (username: "employee", password: "employee")
- admin (username: "admin", password: "admin")


## Used Libraries with Examples
1. Spring Web 
- [src/main/java/com/github/wojciechk92/carrental/car/CarController.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/java/com/github/wojciechk92/carrental/car/CarController.java)
- [src/main/java/com/github/wojciechk92/carrental/client/ClientController.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/java/com/github/wojciechk92/carrental/client/ClientController.java)

2. Spring Data JPA 
- [src/main/java/com/github/wojciechk92/carrental/car/CarRepositorySqlAdapter.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/java/com/github/wojciechk92/carrental/car/CarRepositorySqlAdapter.java)
- [src/main/java/com/github/wojciechk92/carrental/client/ClientRepositorySqlAdapter.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/java/com/github/wojciechk92/carrental/client/ClientRepositorySqlAdapter.java)

3. FlyWay Migration
- [src/main/resources/db/migration/V1__init_tables_cars_clients_employees_rentals.sql](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/resources/db/migration/V1__init_tables_cars_clients_employees_rentals.sql)
- [src/main/resources/db/migration/V2__update_car_status_and_add_statuses_for_other_tables.sql](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/resources/db/migration/V2__update_car_status_and_add_statuses_for_other_tables.sql)

4. H2 Database
- [src/main/resources/application-test.yml](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/resources/application-test.yml)

5. MySQL Driver
- [src/main/resources/application-local.yml](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/resources/application-local.yml)

6. Validation
- [src/main/java/com/github/wojciechk92/carrental/car/Car.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/car/Car.java)

7. Application.properites and application.yml
- [src/main/resources/application.properties](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/resources/application.properties)
- [src/main/resources/application-local.yml](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/resources/application-local.yml)

8.  Spring Security
- [src/main/java/com/github/wojciechk92/carrental/security/SecurityConfig.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/security/SecurityConfig.java)

9.  OAuth2
- [src/main/java/com/github/wojciechk92/carrental/security/SecurityConfig.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/security/SecurityConfig.java)

10.  Keycloak
- [src/main/resources/application-local.yml](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/resources/application-local.yml)

11.  JUnit, Mockito, AssertJ 
- [src/test/java/com/github/wojciechk92/carrental/car/CarServiceImplTest.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/test/java/com/github/wojciechk92/carrental/car/CarServiceImplTest.java)
- [src/test/java/com/github/wojciechk92/carrental/car/CarControllerIntegrationTest.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/test/java/com/github/wojciechk92/carrental/car/CarControllerIntegrationTest.java)
- [src/test/java/com/github/wojciechk92/carrental/car/CarControllerE2ETest.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/test/java/com/github/wojciechk92/carrental/car/CarControllerE2ETest.java)


## Spring Technology Implementation with Examples
1. Controllers
- [src/main/java/com/github/wojciechk92/carrental/car/CarController.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/car/CarController.java)
- [src/main/java/com/github/wojciechk92/carrental/client/ClientController.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/client/ClientController.java)

2. Interfaces
- [src/main/java/com/github/wojciechk92/carrental/car/CarService.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/car/CarService.java)
- [src/main/java/com/github/wojciechk92/carrental/client/ClientService.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/client/ClientService.java)

3. Services 
- [src/main/java/com/github/wojciechk92/carrental/car/CarServiceImpl.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/car/CarServiceImpl.java)
- [src/main/java/com/github/wojciechk92/carrental/client/ClientServiceImpl.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/client/ClientServiceImpl.java)

4. Enumes
- [src/main/java/com/github/wojciechk92/carrental/car/CarStatus.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/car/CarStatus.java)
- [src/main/java/com/github/wojciechk92/carrental/client/ClientStatus.java](https://github.com/WojciechK92/car-rental-rest_api/blob/cec49cfc66b94bd9883bb51365229eaa0cc59b9a/src/main/java/com/github/wojciechk92/carrental/client/ClientStatus.java)

5. Data Transfer Object (DTO)
- [src/main/java/com/github/wojciechk92/carrental/car/dto/CarReadModel.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/java/com/github/wojciechk92/carrental/car/dto/CarReadModel.java)
- [src/main/java/com/github/wojciechk92/carrental/client/dto/ClientWriteModel.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/main/java/com/github/wojciechk92/carrental/client/dto/ClientWriteModel.java)

6.  Unit tests 
- [src/test/java/com/github/wojciechk92/carrental/car/CarServiceImplTest.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/test/java/com/github/wojciechk92/carrental/car/CarServiceImplTest.java)

7.  Integration tests 
- [src/test/java/com/github/wojciechk92/carrental/car/CarControllerIntegrationTest.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/test/java/com/github/wojciechk92/carrental/car/CarControllerIntegrationTest.java)

8.  E2E tests 
- [src/test/java/com/github/wojciechk92/carrental/car/CarControllerE2ETest.java](https://github.com/WojciechK92/car-rental-rest_api/blob/b29413f9282762ab094574bb378fad5dd0b4ffb7/src/test/java/com/github/wojciechk92/carrental/car/CarControllerE2ETest.java)


## Licenses
1. Access to the data  
I would like to provide recruiters with free access to data in this project. Please remember that any information contained in this project is intended for recruitment purposes only and should not be used in any other way without my express consent.
