# PERSONAL-TRAINER-REST

## Description

An application that allows users, depending on physical parameters and goals, to create a training program, as well as select the necessary sports nutrition, you can also find out in which stores you can buy this nutrition.
## Dependencies stack:

- Java 11
- Spring 5
- Spring Boot
- Spring Data JPA
- Database (MySQL and H2)
- Bean Validation
- SLF4j
- Lombok
- Maven
- Spring Security (JWT)
- Mapper (MupStruct)
- Swagger
- Docker
 
  Description Docker:
    1. Clone project: https://github.com/Flatron31/c51-diplom-personal-trainer-rest.git
    2. Create jar file - mvn package
    3. Create docker image - docker build -t personal-trainer-rest:tms .
    4. Run docker image - docker run -p 8080:8080 -d personal-trainer-rest:tms
    5. View container - docker ps
    
    
