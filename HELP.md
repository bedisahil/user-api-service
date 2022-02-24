# User Api Service

### Project Description
This application exposes 2 APIs to get & update the user information present in the system. It connects to an in memory H2 database to store the user details.
As part of running the application, 100 users will be created in the in memory HSQL DB with user ids 1 to 100.

2 exposed end points are as below:
- http://localhost:8080/api/userdetails/{id} (e.g. http://localhost:8080/api/userdetails/37) - This is HTTP GET request to get user information
- http://localhost:8080/api/userdetails/{id} - This is HTTP POST request to update user information. Below is the request json that should be passed in Body  with content type as application/json

  {
  "title": "Mr",
  "firstname": "Sean151",
  "lastname": "Penn37",
  "gender": "Male",
  "address": {
      "street": "390 George St",
      "city": "Melbourne",
      "state": "NSW",
      "postcode": "2000"
    }
  }


### How to Run

* To compile the application, open command prompt, go to user-api-service folder & run "mvn clean install".
* To run the application, open command prompt, go to user-api-service/target folder & run below command:

    "java -jar user-api-service-1.0.0-SNAPSHOT.jar"
