# KB Client Registration API Java SDK

#### Links
* [KB API Business portal](https://www.kb.cz/api)
* [KB API Developer portal](https://api.kb.cz/open/apim/store)

---

The main purpose of this SDK is to provide a simple interface
to connect to the KB Client Registration API and its basic implementation.
Developer can use provided implementation of the interface or use it to build own implementation.

#### Structure
This SDK contains three modules:
* `api` - contains the interfaces for calling KB Client Registration API
and the current version of the API's Swagger documentation
* `jersey-impl` - JAX-RS Jersey implementation of the interfaces, provided by `api` module
* `spring-boot-auto-configuration` - Spring Boot auto-configuration that uses the Jersey implementation under the hood and serves as easy integration of this SDK to your Spring Boot application

#### Usage
You can use this SDK as a Maven dependency or as a external JAR.

##### As Maven dependencies
```xml
...
    <dependency>
        <groupId>cz.kb.openbanking.clientregistration.client</groupId>
        <artifactId>api</artifactId>
        <version>1.0.0</version>
    </dependency>
    <dependency>
        <groupId>cz.kb.openbanking.clientregistration.client</groupId>
        <artifactId>jersey-impl</artifactId>
        <version>1.0.0</version>
    </dependency>
...
```

##### KB Client Registration API call
To call KB Client Registration API:
```java
SoftwareStatementsApi softwareStatementsApi = new SoftwareStatementsJerseyImpl("apiUri", "apiKey"));
```
where:
* `apiUri` - URI of the KB Client Registration API. Currently: https://openbanking.kb.cz/client-api-management
* `apiKey` - authorization API key generated at [KB API Developer portal](https://api.kb.cz/open/apim/store)

---
For more details and examples of usage this SDK see [ADAA API's client reference example](todo)
