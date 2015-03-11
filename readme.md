#Grizzly2, MOXy, embedded Jetty
Maven project demonstrating how to run a JAX-RS 2 project in embedded Jetty with Servlet3 annotation based configuration,
using Grizzly2 with JSON binding via MOXy.

## Steps to run this project
* Clone this repo
* Build project: mvn clean install -U
* Start Jetty: mvn exec:java
* Application.wadl: http://localhost:8080/api/application.wadl
* Example usage: http://localhost:8080/api/books
* Open BookResourceTest.java to explore code
