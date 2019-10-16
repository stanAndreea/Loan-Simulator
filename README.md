# loanSimulator
Loan Simulator written in Java using Spring Boot framework

## How to run the application:
- mvn clean package
- mvn spring-boot:run


## Environment:
-	Maven
-	Java 1.8
-	Spring Boot 2.1.9.RELEASE
- H2 in memory database - sample screenshot (how to access the H2 in memory database instance)

![h2_database](https://user-images.githubusercontent.com/35422422/66910362-180e1180-f017-11e9-9d9e-0ebc20d3609d.jpg)



## Business rules:
-	Max amount allowed for a loan is retrieved from application.properties (for this demo) and is 10000.
-	If a client applies for a loan with an amount > maximum => rejection
-	If a client applies for a loan in the interval 0:00 – 6:00 AM (with maximum amount => rejection)
-	If a client applies for a loan 3 times per day on same IP => rejection
-	If the json request (body) does not respect the field names (ex: instead of ‘firstName’ we set ‘fn’) => rejection
-	If we do not respect the constraints defined in the jpa entities (Model/Loan) for example if we want to set a loan for an amout <=0 and > maximum amount => rejection
-	If no risk is involved on applying for a loan => client saved in db (if it does not already exists) + loan saved in db.


## Invoke REST WS – apply for a loan
- Since the boot application is started and configured we are ready to invoke a REST API to apply for a loan.


## Used Postman Chrome plugin for invoking the REST API:

- URL: localhost:8080/apply/loan
- HTTP method: POST
- Content-Type: application/json
- Body (example): {"firstName":"Andreea", "surName":"Stan","identityCardNo":12345678,"amount":1000,"term":36}

![loanRequest](https://user-images.githubusercontent.com/35422422/66910483-4e4b9100-f017-11e9-81fd-592f8dc0d0e5.jpg)
