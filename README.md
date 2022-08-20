
# MEDISCREEN PROJECT

Application d'évaluation du diabète

## Version
version v0.5


## Technologies
- java 11
- maven 2.6.8
- spring data
- spring cloud
- thymeleaf
- html
- css
- javascript
- mysql
- H2 database
- mongodb
## Autor

- Eric Maximan
## ARCHITECTURE OVERVIEW

![architecture-ms](https://user-images.githubusercontent.com/34154288/185735482-9266fea9-c1f5-4431-b5e0-83aa2133bf22.png)

## Running App

Import the code into an IDE of your choice.
To deploy, go to the folder that contains
the docker-compose.yml file and execute
the below command.

- Build application
  `mvn package -DskipTests`

- Deploy application
  `docker-compose up`
## Testing

To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

`mvn test`
## Reporting
To generate report, go to the main folder
and execute the below command.

`mvn clean package`