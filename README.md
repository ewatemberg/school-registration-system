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

    docker-compose -f src/main/docker/mysql.yml up -d

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

For the development and execution of the tests, I used the Spock framework. In this [repo](https://github.com/ewatemberg/acceptance-test-spock) there is information about a training about its use and its virtues in the readability of the tests and understanding (mainly in the use of the Gherkin language).

### Prepare the environment

  1) Run the docker-compose image to get MySQL and Adminer up.

    docker-compose -f src/main/docker/mysql.yml up -d

  3) Create the schema **schooldb_test**

  - You can use the following access to use adminer to manage the db from browser


    url: http://localhost:8083/
    server: school-registration-mysql
    user: root
    pass: root
    db: schooldb

  - Or you can use the following statement if you prefer use command line


    docker exec -it docker exec -it docker_school-registration-mysql_1 bash -l
    mysql -uroot -proot
    CREATE SCHEMA schooldb_test; GO

  3) Use flyway to restore the last version of DB domain

    mvn flyway:clean flyway:migrate -Dflyway.schemas=schooldb_test -Dflyway.user=root -Dflyway.password=root

  4) Run Test

    mvn test

### About TDD

    

### About ATDD

In the src/test

### Frameworks/Tools
* [Spring boot](https://spring.io/projects/spring-boot) - Framework de java
* [Spock](http://spockframework.org/) - Tests
* [Docker](https://www.docker.com/get-started/) - Containerized
* [Flyway](https://flywaydb.org/) - Version control for your database
* [Lombok](https://projectlombok.org/) 


## Notes 📋
(1) _It's recommended to use [IntelliJ Community](https://www.jetbrains.com/idea/download/) or [Eclipse IDE for Enterprise Java Developers](https://www.eclipse.org/downloads/packages/)_
