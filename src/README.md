# Rule Engine Application Documentation for src Folder
The Rule Engine application is designed to create and evaluate logical rules in the form of Abstract Syntax Trees (ASTs). It allows users to define rules using logical operators and then evaluate them based on user attributes.

Features
Rule Creation: Converts logical expressions into an AST structure.
Rule Evaluation: Evaluates rules against user-provided data.
Rule Combination: Supports combining multiple rules using logical operators.
Validation: Provides syntax validation for rules to ensure accuracy.


Technologies Used
Java 17
Spring Boot 3.0.0
Maven
JUnit for testing


Setup Instructions
Prerequisites
Ensure you have Java 17 and Maven installed on your machine.

Build and Run
mvn clean install
mvn spring-boot:run

API Endpoints
Base URL
http://localhost:8080/api/rules

Endpoints

1.Create a Rule
  Endpoint: /create
  Method: POST
  Params: rule (string)

2.Evaluate a Rule
  Endpoint: /evaluate
  Method: POST
  Params: rule (string)
  Body: JSON object with attributes to evaluate against the rule.

3.Combine Rules
  Endpoint: /combine
  Method: POST
  Body: JSON array of rules.

  Project Structure

  rule-engine/
├── src/
│   ├── main/java/com/zeotap/ruleengine/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── RuleEngineApplication.java
└── README.md


