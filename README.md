# CMA - Company Management App

# Overview #
This is a very simple web app created using AngularJS, Java 8, Spring 4 MVC , JPA2, Hibernate, Maven, Git, GitHub and Heroku. It provides RESTful services to create, update and view a singular Company and its respective owner(s) as well as services to retrieve and list all companies and owners stored in the underline repository. When viewed from a web browser, a client is able to place AJAX calls to all these services and visualize the respective response. Using [Postman](https://www.getpostman.com), a web browser or any similar tool, you can also call each published service manually, providing the necessary JSON for each kind of request.

# Acessibility #
This Web app can be accessed through any web browser, curl, postman or any other similar tool.  
The basic URL for accessing through a web browser is: **https://rocky-garden-55362.herokuapp.com/cma/view/index.html**  
The following URLs can be used to access the available services:
* **GET request - List all companies:** https://rocky-garden-55362.herokuapp.com/cma/services/companies
* **GET request - Show details about a specific company:** https://rocky-garden-55362.herokuapp.com/cma/services/companies/{companyId}
* **GET request - List all owners:** https://rocky-garden-55362.herokuapp.com/cma/services/owners
* **POST request - Save (create or update) a specific company:** https://rocky-garden-55362.herokuapp.com/cma/services/companies/save
* **POST request - Save (create or update) a specific owner:** https://rocky-garden-55362.herokuapp.com/cma/services/owners/save

# Request Examples #

## Save (create or update) Owner ##
* **Method:** POST
* **URL:** https://rocky-garden-55362.herokuapp.com/cma/services/owners/save
* **Body:** JSON 
```
{
  "name": "Test Owner 1" 
}
```
* **Response:** JSON
```
{
  "ownerId": 1, "name": "Test Owner 1" 
}
```

## Save (create or update) Company ##
* **Method:** POST
* **URL:** https://rocky-garden-55362.herokuapp.com/cma/services/companies/save
* **Body:** JSON
```
{
  "name":"Company 1",
  "email":"company1@test.com",
  "phoneNumber":"(123)456-7890",
  "address":"345 Clay Street",
  "city":"San Francisco",
  "country":"USA",
  "owners": [
    {
      "ownerId":1,"name":"Test Owner 1"
    }
  ]
}
```
* **Response:** JSON
```
{
  "companyId": 2,
  "name": "Company 1",
  "email": "company1@test.com",
  "phoneNumber": "(123)456-7890",
  "address": "345 Clay Street",
  "city": "San Francisco",
  "country": "USA",
  "owners": [
    {
      "ownerId": 1,
      "name": "Test Owner 1"
    }
  ]
}
```

## List All Companies ##
* **Method:** GET
* **URL:** https://rocky-garden-55362.herokuapp.com/cma/services/companies
* **Response:** JSON
```
[
  {
    "companyId": 2,
    "name": "Company 1",
    "email": null,
    "phoneNumber": null,
    "address": null,
    "city": null,
    "country": null,
    "owners": []
  },
  {
    "companyId": 3,
    "name": "Company 2",
    "email": null,
    "phoneNumber": null,
    "address": null,
    "city": null,
    "country": null,
    "owners": []
  }
]
```

## Show Specific Company ##
* **Method:** GET
* **URL:** https://rocky-garden-55362.herokuapp.com/cma/services/companies/2
* **Response:** JSON
```
{
  "companyId": 2,
  "name": "Company 1",
  "email": "company1@test.com",
  "phoneNumber": "(123)456-7890",
  "address": "345 Clay Street",
  "city": "San Francisco",
  "country": "USA",
  "owners": [
    {
      "ownerId": 1,
      "name": "Test Owner 1"
    }
  ]
}
```

## List All Owners ##
* **Method:** GET
* **URL:** https://rocky-garden-55362.herokuapp.com/cma/services/owners
* **Response:** JSON
```
[
  {
    "ownerId": 1,
    "name": "Test Owner 1"
  },
  {
    "ownerId": 4,
    "name": "Test Owner 2"
  }
]
```


# Technical overview #
The RESTful services of this solution were created usign **Hibernate 5**, **JPA 2**, **Spring MVC, Core and Tests version 4.** and **HSQLDB 2**.  
An in memory database **HSQLDB** is being used and since this WebApp is deployed at **[Heroku](https://dashboard.heroku.com)**, it is possible to have data loss when testing this App in different and sparse time. Howerver data loss will not happen when running locally within the same JVM.  
TDD was used to create the whole solution, except Web tests. **Spring Test** is a very and powerful framework to allow the creation and Mocking of a virtual environment to sucessfully test every layer of this app. It was used in conjunction with **[Mockito 1.10.19](http://mockito.org)** to allow assertions with mock data.  
Although simple, I decided to split the solution in modules, which simulates closely an enterprise solution. Furthermore, this Web App is not using Spring Boot in order to show what is necessary to do to create an app like this without the shortcuts offered by Spring Boot. Later I am planning to create this same solution using Spring Boot to allow a comparison between the traditional and the new way of creating Spring applications.

# Useful References #
* **[AngularJS](https://angularjs.org)**
* **[AngularJS API Docs](https://docs.angularjs.org/api)**
* **[Web MVC Framework](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-servlet)**
* **[Spring MVC Test Framework](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html#spring-mvc-test-framework)**
* **[Bootstrap](http://getbootstrap.com)** 
* **[Hibernate ORM](hibernate interview questions)**
* **[HSQLDB - HyperSQL DB](http://hsqldb.org)**
* **[Maven](http://maven.apache.org)**
* **[Heroku - Deploy a Java Web Application That Launches with Jetty Runner](https://devcenter.heroku.com/articles/deploy-a-java-web-application-that-launches-with-jetty-runner)**
* **[Securing RESTful Web Services and Clients](http://docs.oracle.com/middleware/1212/wls/RESTF/secure-restful-service.htm#RESTF280)**
