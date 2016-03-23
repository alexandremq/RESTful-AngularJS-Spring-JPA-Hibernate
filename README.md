# CMA - Company Management App

# Overview #
This is a very simple web app created using AngularJS, Java 8, Spring 4 RESTful , JPA2, Hibernate, Maven, Git, GitHub and Heroku. It provides RESTful services to create, update and view a singular Company and its respective owner(s) as well as services to retrieve and list all companies and owners stored in the underline repository. When viewed from a web browser, a client is able to place AJAX calls to all these services and visualize the respective response.

# Acessibility #
This Web app can be accessed through any web browser, curl, postman or any other similar tool.  
The basic URL for accessing through a web browser is: **https://rocky-garden-55362.herokuapp.com/cma/view/index.html**  
The following URLs can be used to access the available services:
* **List all companies:** https://rocky-garden-55362.herokuapp.com/cma/services/companies
* **Show details about a specific company:** https://rocky-garden-55362.herokuapp.com/cma/services/companies/{companyId}
* **List all owners:** https://rocky-garden-55362.herokuapp.com/cma/services/owners
* **Save (create or update) a specific company:** https://rocky-garden-55362.herokuapp.com/cma/services/companies/save
* **Save (create or update) a specific owner:** https://rocky-garden-55362.herokuapp.com/cma/services/owners/save

# Technical details #
The RESTful services were created usign **Hibernate 5**, **JPA 2**, **Spring MVC, Core and Tests version 4.** and **HSQLDB 2**.  
An in memory database HSQLDB is being used and since this WebApp is deployed at **[Heroku](https://dashboard.heroku.com)**, it is possible to have data loss when testing this App in different and sparse time. Howerver data loss will not happen when running locally within the same JVM.  
TDD was used to create the whole solution, except Web tests. Spring Test is a very and powerful framework to allow the creation and Mocking of a virtual environment to sucessfully test every layer of this app. It was used in conjunction with **[Mockito 1.10.19](http://mockito.org)** to allow assertions with mock data.  
Although simple, it was decided to split the solution in modules. Furthermore, this Web App is not using Spring Boot in order to show what is necessary to do to create an app like this without the shortcuts offered by Spring Boot. Later I am planning to create this same solution using Spring Boot to allow a comparison between the traditional and the new way of creating Spring applications.

# Useful References #
* **[AngularJS](https://angularjs.org)**
* **[AngularJS API Docs](https://docs.angularjs.org/api)**
* **[Web MVC Framework](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-servlet)**
* **[Spring MVC Test Framework](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html#spring-mvc-test-framework)**
* **[Bootstrap](http://getbootstrap.com)** 
* **[Hibernate ORM](hibernate interview questions)**
* **[HSQLDB - HyperSQL DB](http://hsqldb.org)**
* **[Maven](http://maven.apache.org)**
* * **[Heroku - Deploy a Java Web Application That Launches with Jetty Runner](https://devcenter.heroku.com/articles/deploy-a-java-web-application-that-launches-with-jetty-runner)**
