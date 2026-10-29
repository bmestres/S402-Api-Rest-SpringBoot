
# S402 Rest API with Spring boot<br>
 Independent Spring Boot applications, with a REST API that implements a complete CRUD (Create, Read, Update, Delete) on different entities. Persistence through H2 and MySQL databases.<br>

 **Technologies**:<br>
* Backend Java<br>
* Spring Boot 4.1.1<br>
* Apache Maven 3.8.8<br>
* JUnit 5<br>
* Spring Web and Spring Boot DevTools<br>
* H2 database

**Opened at:**

- http://localhost:9000/health

**Run API with Docker**
````
docker compose up --build
````

**Stop Container**
````
docker compose down
````
### Endpoints


| Method | Path | Success |
| --- | --- | --- |
| `POST` | `/fruits` | 201 |
| `GET` | `/fruits` | 200 |
| `GET` | `/fruits/{id}` | 200 |
| `PUT` | `/fruits/{id}` | 200 |
| `DELETE` | `/fruits/{id}` | 204 |



## Fruit API H2

```
.
├── docker-compose.yaml
├── Dockerfile
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── cat
    │   │       └── itacademy
    │   │           └── s04
    │   │               └── t02
    │   │                   └── n01
    │   │                       └── fruitapih2
    │   │                           ├── controllers
    │   │                           │   ├── FruitController.java
    │   │                           │   └── HealthController.java
    │   │                           ├── domain
    │   │                           │   └── Fruit.java
    │   │                           ├── dto
    │   │                           │   ├── FruitRequestDto.java
    │   │                           │   └── FruitResponseDto.java
    │   │                           ├── exception
    │   │                           │   ├── ErrorResponse.java
    │   │                           │   ├── FruitNotFoundException.java
    │   │                           │   └── GlobalExceptionHandler.java
    │   │                           ├── FruitApiH2Application.java
    │   │                           ├── repositories
    │   │                           │   └── FruitRepository.java
    │   │                           ├── services
    │   │                           │   ├── FruitService.java
    │   │                           │   └── FruitServiceImpl.java
    │   │                           └── status
    │   │                               └── HealthStatus.java
    │   └── resources
    │       ├── application.yaml
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── cat
                └── itacademy
                    └── s04
                        └── t02
                            └── n01
                                └── fruitapih2
                                    ├── controllers
                                    │   ├── FruitControllerTest.java
                                    │   └── HealthControllerTest.java
                                    ├── domain
                                    │   └── FruitTest.java
                                    ├── FruitIntegrationTest.java
                                    └── services
                                        └── FruitServiceImplTest.java

````




## Fruit API MySQL<br>
Adds the management of fruit suppliers to the previous system. Each fruit is forcely associated with a supplier, which allows to record the origin of each product and check which fruits each company supplies.

This new project uses MySQL as a database and introduces a relationship between entities through JPA, specifically a aManyToOne type association between Fruit and Provider.

 **Technologies**:<br>
* Backend Java<br>
* Spring Boot 4.1.1<br>
* Apache Maven 3.8.8<br>
* JUnit 5<br>
* Spring Web and Spring Boot DevTools<br>
* MySQL database

**Run API with Docker**
````
docker compose up --build
````
Once the MySQL database is ready, the API container **starts the app** at:

- http://localhost:9000/health

**Stop Containers**
````
docker compose down
````

Database connection used by the containers:

| Setting | Value |
| --- | --- |
| Host inside Docker | `fruit-api-mysql-database` |
| Host from the machine | `localhost` |
| Port | `3306` |
| Database | `fruit-api-mysql-database` |
| User | `root` |
| Password | `psw` |

## Endpoints

Create a supplier before creating a fruit. The fruit body uses `supplier_id`.

### Suppliers

| Method | Path | Success |
| --- | --- | --- |
| `POST` | `/suppliers` | 201 |
| `GET` | `/suppliers` | 200 |
| `GET` | `/suppliers/{id}` | 200 |
| `PUT` | `/suppliers/{id}` | 200 |
| `DELETE` | `/suppliers/{id}` | 204 |


**Project Structure:**

```
.
├── docker-compose.yaml
├── Dockerfile
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── cat
    │   │       └── itacademy
    │   │           └── s04
    │   │               └── t02
    │   │                   └── n01
    │   │                       └── fruitapimysql
    │   │                           ├── controllers
    │   │                           │   ├── FruitController.java
    │   │                           │   ├── HealthController.java
    │   │                           │   └── SupplierController.java
    │   │                           ├── domain
    │   │                           │   ├── Fruit.java
    │   │                           │   └── Supplier.java
    │   │                           ├── dto
    │   │                           │   ├── FruitRequestDto.java
    │   │                           │   ├── FruitResponseDto.java
    │   │                           │   ├── SupplierRequestDto.java
    │   │                           │   └── SupplierResponseDto.java
    │   │                           ├── exception
    │   │                           │   ├── ErrorResponse.java
    │   │                           │   ├── FruitNotFoundException.java
    │   │                           │   ├── GlobalExceptionHandler.java
    │   │                           │   ├── RepeatedSupplierNameException.java
    │   │                           │   ├── SupplierNotFoundException.java
    │   │                           │   └── SupplierWithFruitsAssociatedException.java
    │   │                           ├── FruitApiMysqlApplication.java
    │   │                           ├── repositories
    │   │                           │   ├── FruitRepository.java
    │   │                           │   └── SupplierRepository.java
    │   │                           ├── services
    │   │                           │   ├── FruitService.java
    │   │                           │   ├── FruitServiceImpl.java
    │   │                           │   ├── SupplierService.java
    │   │                           │   └── SupplierServiceImpl.java
    │   │                           └── status
    │   │                               └── HealthStatus.java
    │   └── resources
    │       ├── application.yaml
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── cat
                └── itacademy
                    └── s04
                        └── t02
                            └── n01
                                └── fruitapimysql
                                    ├── controllers
                                    │   ├── FruitControllerTest.java
                                    │   ├── HealthControllerTest.java
                                    │   └── SupplierControllerTest.java
                                    ├── domain
                                    │   ├── FruitTest.java
                                    │   └── SupplierTest.java
                                    ├── FruitIntegrationTest.java
                                    ├── repositories
                                    │   └── RepositoryTest.java
                                    ├── services
                                    │   ├── FruitServiceImplTest.java
                                    │   └── SupplierServiceImplTest.java
                                    └── SupplierIntegrationTest.java
```
