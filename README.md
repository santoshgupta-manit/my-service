# Objective
Create a standalone java application which allows users to manage their favourite recipes. It should
allow adding, updating, removing and fetching recipes. Additionally users should be able to filter
available recipes based on one or more of the following criteria:
1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.
For example, the API should be able to handle the following search requests:
• All vegetarian recipes
• Recipes that can serve 4 persons and have “potatoes” as an ingredient
• Recipes without “salmon” as an ingredient that has “oven” in the instructions.

# Recipe Service
Recipe Web Service is specifically designed and implemented as part of ABN Amro Technical Interview. Recipe Web Service contains ReST APIs in order to Create, Get, Update and Delete recipe from the database and render the requested details as JSON response to end user. The response from ReST APIs can be further integrated with front end view for better presentation.

# Architecture Design
Recipe Service is a microservice based layered architectured RESTful Web Service. This service can be deployed independently on premise or any cloud platform.
Below are the different layers in this service:
##### API Layer
o	This is the top layer available for integration and interaction with front-end or end user to consume APIs
o	Request is validated at this layer. Spring Bean Validation take cares of all the validations at this layer. 

##### Service Layer
o	This layer sits in between API layer and Data access layer with some utility functionality
o	It is mainly responsible for interacting with Data Access Layer and transferring the recipes data as required by top and below layers
o	It's just another module added to decouple business logic of recipes data transfer and mapping from/to API layer

##### Data Access Layer
o	Responsible to provide Object Relationship Mapping (ORM) between higher level recipe Java objects and persistence layer tables
o	Springboot-starter-data-JPA module is used to implement mappings between objects and tables
o	This layer contains recipe entity classes and JPA repositories which implement lower level functionality of storing/retrieving recipes data

##### Persistence Layer
o	Bottom most layer, responsible for physically storing the recipes data onto database table
o	Table RECIPE_TBL is used to store the recipes data for the service
o	For development and testing purposes, the Embedded H2 Database provided by Spring Boot framework is also utilized

# Supported Features
Feature  | Software Module Used
------------- | -------------
ReSTful API  | Springboot
Object Relationship Mapping | Spring Data JPA
Exception Handling | Controller Advice and ExceptionHandler
Logging | SLF4J Logger
Unit Tests | Junit 5 with Mockito
Integration Tests | Junit 5 with Mockito
API Documents | Swagger API Documentation
Service Monitoring | actuator

# Setup guide
----
##### Minimum Requirements
-  Java 11
-  Maven 3.x
##### Install the application
1. Make sure you have Java and Maven installed
2. Open the command line in the source code folder
3. Build project
   `$ mvn package`
 4. Run the tests
    `$ mvn test`
5. Run the project
   `$ java -jar receipe-service-0.0.1-SNAPSHOT.jar`
6. Open the swagger-ui with the link below
`http://localhost:9080/recipe-service/swagger-ui.html#/`




