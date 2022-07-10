School registration system - [Metadata.io](https://metadata.io/) challenges
---
Design and implement simple school registration system
- Assuming you already have a list of students
- Assuming you already have a list of courses
- A student can register to multiple courses
- A course can have multiple students enrolled in it.
- A course has 50 students maximum
- A student can register to 5 course maximum

Provide the following REST API:
- Create students CRUD
- Create courses CRUD
- Create API for students to register to courses
- Create abilities for user to view all relationships between students and courses
+ Filter all students with a specific course
+ Filter all courses for a specific student
+ Filter all courses without any students
+ Filter all students without any courses

### Pre-requirements
* Java 11
* Docker

## Develop

  1) Run the docker-compose image to get MySQL and Adminer up.

    docker-compose -f src/main/docker/mysql.yml up

  2) Use the following access:

    url: http://localhost:8083/
    server: school-registration-mysql
    user: root
    pass: root
    db: schooldb

  3) Use flyway to restore the last version of DB domain

    mvn flyway:clean flyway:migrate -Dflyway.schemas=schooldb -Dflyway.user=root -Dflyway.password=root

  4) Run app

    mvn spring-boot:run

## Run  💻

## Deploy 📦

## Doc 📖️

## Test ✏️


### Frameworks/Tools
* [Spring boot](https://spring.io/projects/spring-boot) - Framework de java
* [Spock](http://spockframework.org/) - Tests
* [Docker](https://www.docker.com/get-started/) - Containerized
* [Flyway](https://flywaydb.org/) - Version control for your database
* [Lombok](https://projectlombok.org/) 


## Notes 📋
(1) _It's recommended to use [IntelliJ Community](https://www.jetbrains.com/idea/download/) or [Eclipse IDE for Enterprise Java Developers](https://www.eclipse.org/downloads/packages/)_
