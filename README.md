# Country Route Finder

## Requirements

For building and running the application you need:

- [OpenJDK 17](https://openjdk.java.net/projects/jdk/17)
- [Maven 3](https://maven.apache.org)

If you're behind a proxy, you can configure it in the `application.properties` file.
## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `zengin.serkan.countrydemo.CountrydemoApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Packaging the application 

Below comand creates a jar file under the target directory that can be used to run the application.

```shell
mvn package
```

## Running the application on a remote server

Copy the jar file to the remote server and execute the following command:

```shell
java -jar countrydemo-0.0.1-SNAPSHOT.jar
```