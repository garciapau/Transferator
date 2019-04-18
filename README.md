# Transferator
REST API for a money-transferring application

## Main Features:
- An endpoint to get the current balance of a given user.
- An endpoint to make a transfer of money between two users.
- Dockerized
 
## Design decisions and assumptions:
- A specific user can only have one account in the system
- I'm leaving out the scope of this exercise the currencies, therefore there is a unique (implicit) currency

## Structure and design
The solution is modularized (gradle) in the following components:

`application`: Contains the business logic that implements the application use cases. 
I've chosen Cucumber to define the tests for validating the use cases.

`infra`: Contains the inbound and outbound components required by the application. Inside this element, we can find:

`infra-rest`: Provides the Rest HTTP endpoint to access the service. It is based in SpringBoot and provides the contract
definition using Swagger

