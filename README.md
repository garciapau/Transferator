# Transferator
REST API for a money-transferring application

## Main Features:
- An endpoint to get the current balance of a given user.
- An endpoint to make a transfer of money between two users.
- Dockerized
 
## Design decisions and assumptions:
A specific user can only have one account in the system

I'm leaving out the scope of this exercise:
- Currencies, therefore there is a unique (implicit) currency
- Operations history, although sounds mandatory to keep it in a real application, has been left out of the scope since there are not specific requirements for it.
- Environment-specific configuration
- Service cluster. I've been focusing on a single instance service
- Constants and literals are defined in the same class where they are used
- Stress Test

## Structure and design
Regarding code structure, we can use a single gradle project and base the solution's structure in the Java packages 
or use different gradle modules to encapsulate each component. Both have pros and cons, for instance, 
the former eases finding the classes and the initial understanding of the solution, but the later 
enforces the decoupling between different components (in terms of features and testing) 
while slightly coupling the solution to gradle configurations.

Since I'm currently more satisfied with the later: In this solution I've decided to use gradle modules 
to encapsulate different components, therefore the solution is divided in gradle modules as follows:

`application`: Contains the business logic that implements the application use cases. 
The goal is to keep this module as much decoupled as possible, only using Java 8 standard library.
I've chosen Cucumber to define the tests for validating the use cases.

`infra`: Contains the inbound and outbound components required by the application. Inside this element, we can find:

`infra-rest`: Provides the Rest HTTP endpoint to access the service. It is based in SpringBoot and provides the contract
definition using Swagger

`infra-repository`: The implementation of the in memory repository for the application

## How to test and execute
The service has an embedded Tomcat server available at por 8080.

### Test the service
Functional/Acceptance tests are defined in the application module, under 

`features/Account.feature`

Integration tests are defined in the infra folder at:
`com.acme.transferator.infra.IntegrationTest`

Notice that I've tried to find a profitable balance between test coverage and test usefulness. 
Main principles followed:

- Every piece of logic must incorporate a test

- Functional/Acceptance focus on the Happy Scenarios

- Integration tests focus on infrastructure behavior

- Unit tests mock the underlying layers and validate the logic of a specific method

To test all the above tests, just type:
`./gradlew clean test`

### Run the standalone Spring Boot service
`./gradlew clean bootRun`

### Run a docker image of the service
First, let's create the jar file:

`./gradlew clean build`

Then create the image:

`docker build -t transferator .`

and finally run it:

`docker run -p <PREFERED_PORT>:8080 transferator`

replace the <PREFERED_PORT> by the port number you can to publish the service to.

The service starts and you can access Swagger definition at:
`http://localhost:8080/v2/api-docs`

and Swagger UI at:
`http://localhost:8080/swagger-ui.html#/`
