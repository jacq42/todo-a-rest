# TODO a REST

Sample Spring boot application with a REST Controller and REACT frontend

* Lists TODOs and can delete one item by ID
* TODOs can not be created with the UI (but with Postman)
* under src/test/tools is a Postman collection with sample requests

## Start

`backend/gradlew bootRun` processes the frontend to the public folder of the backend and starts the application

## Build

`backend/gradlew clean build` builds frontend and backend into 1 jar