# web-crawler-demo

This is a Spring Boot microservice intended to fulfill the following code challenge:

## Challenge Overview

Create a web crawler using scraping techniques to extract the first 30 entries from https://news.ycombinator.com/. You'll only care about the number, the title, the points, and the number of comments for each entry.

From there, we want it to be able to perform a couple of filtering operations:
 - Filter all previous entries with more than five words in the title, ordered by the number of comments first.
 - Filter all previous entries with less than or equal to five words in the title, ordered by points.

When counting words, consider only the spaced words and exclude any symbols. For instance, the phrase “This is - a self-explained example” should be counted as having 5 words.

The solution should store usage data, including at least the request timestamp and a field to identify the applied filter. You are free to include any additional fields you deem relevant to track user interaction and crawler behavior. The chosen storage mechanism could be a database, cache, or other suitable tool.

## Running the microservice

The project was created using Java JDK 21 and Gradle 9.2.0. An installed and compatible JDK is required to build and run, while Gradle is optional as I've included its portable Wrapper for the same version.

**Build command:**

 - Windows:  
 ./gradlew.bat clean build

 - UNIX:  
 ./gradlew.bat clean build

Build command automatically runs unit and integration tests with Gradle Test

**Run command:**

 - Windows:  
 ./gradlew.bat bootRun

 - UNIX:  
 ./gradlew bootRun
 
**Code Coverage report:**
Triggered automatically via Build and Test commands.

Generated location: build/reports/jacoco/test/html/index.html

**Mutation Testing report:**

 - Windows:  
 ./gradlew.bat build pitest

 - UNIX:  
 ./gradlew build pitest

Generated location: build/reports/pitest/index.html

**Calling the service:**

The service is configured to run at port 8080, endpoint "/entries". The use case logic is applied via optional query param "filter" with case-insentive options [long,short], plus an extra "limit" param, as such:

 - GET all 30 entries:  
 curl http://localhost:8080/entries

 - GET all 30 entries, then filer long titles ordered by number of comments:  
 curl http://localhost:8080/entries?filter=long

 - GET all 30 entries, then filer short titles ordered by points:  
 curl http://localhost:8080/entries?filter=short

 - (Extra) GET less than 30 entries before applying the desired filter:  
 curl http://localhost:8080/entries?limit=5  
 curl http://localhost:8080/entries?limit=5&filter=short  
 curl http://localhost:8080/entries?limit=5&filter=long  

## Considerations and Thoughts

I've used **Spring Boot** instead of pure Java as it is an industry standard for its ease of setup, quickly adding capabilities for an auto-configured embedded server and dependency injection on its own, and then allowing for a vast library of starter dependencies that simplify code development and maintenance.

I've structured the code following a **Layered Architecture** base with a sprinkle of **Domain Driven Design**. Use case logic that pertains strictly to the requirements is concentrated in the *domain* package classes, while the rest of the logic that ties it to the Web and Repository layers are in the *controller*, *service* and *repository* packages, with cross-cutting *configuration* and *exception* packages. Every software company has its on definitions for the exact architecture to use, allowed names, purist vs custom/mixed architecture, etc, so I just organized it as I'm most used to. I could've gone for something like Hexagonal, for example, just as well.

For **Version Control** I used *GIT* (please judge my commit history!), and the **Automated Testing Framework** is *JUnit* for unit tests, with added *Mockito* for mocking, and *MockMvc* to test the web layer in an integrated way. Techniques included in the tests are dependency injection, parameterized tests, mocking, and resource loading of an HTML file example to allow repeatable, self-contained tests that don't call the real URL. Tests and Main implementations include both functional and object-oriented logic, as Java 8+ does have capabilities for the former. Finally, I've included gradle plugins for *Jacoco* to analyze **Code Coverage**, and *PITest* to analyze test strength using **Mutation Testing**. Current code has over 90% fulfillment on both.

There's several ideas I didn't implement because I'd need more time to do them correctly. I'm gonna express them here as improvements I could do for a similar real-life service, if they are necessary or if they add enough value, alongside any **considerations** I had for each layer:

### Domain layer 

  - I've used *Regex* to parse the titles according to the requirements regarding special characters and spaced words. Patterns are initialized in constants, during app init, to improve performance.

### Controller layer

  - I used *Spring Web* with *Spring MVC* since I have more experience with it and can implement it faster.

  - I've applied centralized exception handling so the controller class has minimal logic.
 
  - I could create an *OpenAPI* contract using *Swagger* to document the service's API better, as well as go for a *contract-first* approach to autogenerate its interface.

  - I added the "limit" param thinking of it as the beginnings of a *Pagination* feature that could be necessary if the webpage returned an excessive amount of entries per page, or if we needed to crawl/scrape beyond page 1 in a controlled way.

  - Finally, if current performance/responsiveness wasn't good enough, I'd consider switching to a reactive stack using *Spring Webflux* instead of MVC.

### Service layer

I've split the logic into several interfaces:

 - *WebCrawlerService* is the main service and interprets the desired use case based on the inputs.

 - *DocumentParser* handles calling and parsing the url:

    - The current implementation (*JSoupDocumentParser*, alongside the accompanying *JSoupClient*) is highly adapted to both the HackerRank URL structure, as well as the scraping library I'm using (*JSoup*) to implemented the logic more easily using selectors and class-searching.
    
        - If the target URL or its HTML structure changes, or if I wanted to use a different library/parse it manually, then I can make a new implementation and inject that without changing the rest of the classes.

    - I've prefered the use of *Java Streams* here to manipulate the data in a more human-readable way (in my opinion) through *Functional Logic*.

    - For better performance I can minimize the outgoing HTTP calls via *Caching* the resulting document, with an In-Memory Map object or via *Redis* with a sensible time-to-live, dependent on how often I need to detect changes in the HackerRank listing.

  - *UsageDataService* handles storing the input metrics as per the requirements. Here I thought about applying *multi-threading*, splitting off the call to that service to its own thread, parallelizing and improving responsiveness if needed.

### Repository layer

I chose a relational-database-paradigm because I have more experience with them and they have more library support, though *NoSQL* is also possible with Spring. So, I made use of *Spring Data JPA* to abstract the database via *Object-Relational-Mapping*:

  - The *UsageData* entity class defines a table in a database-implementation-agnostic way.

  - I'm saving the timestamp and applied filter, as requested, and added the raw query param inputs plus resulting entry count of the response. The ID is delegated to the database implementation.

  - I've added *H2* as an embedded in-memory database for ease of setup. To use a physical, JPA-compatible database, I can just add the driver in *build.gradle* and change the properties *application.yml* for "spring.datasource" and "spring.jpa" accordingly.
    
     - To explore the stored Usage Data, the H2 console is accessible via browser at:  
     http://localhost:8080/h2-console/

     - URL: jdbc:h2:mem:testdb
     - User Name: sa
     - No password
     - Click Connect and look for schema "METRICS", table "USAGE_DATA". Click on the table name to autogenerate a SELECT and click Run as you make calls to /entries.
     
  - Once again, here I thought I could also switch to a reactive stack by using *R2DBC* if performance demands it.

### Exception Layer

 - Here I've added an *Exception Handler* that extends Spring's native one, which returns an informative JSON response for several pre-defined common errors, such as sending an invalid String value to the "limit" param.

 - I added one custom exception to encapsulate connection errors when JSoup fails to connect to the URL, returning an HTTP 502 status. Try running the service locally, switch off your Wi-fi, then call /entries to see that one.

 - For this and other layers that handle cross-cutting concerns I could use *Spring AOP* to apply *Aspect Oriented Programming*, but for such a small service it might be an overkill amount of abstraction. Unless and until there's expectation of growth for the service logic, correct use of interface implementations and injection + an exception handler might be more than enough.