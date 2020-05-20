# SpringMicroservices
Microservices

### Swagger

Swagger is a set of rules (in other words, a specification) for a format describing REST APIs. ... As a result, it can be used to share documentation among product managers, testers and developers, but can also be used by various tools to automate API-related processes.

#### Dependencies :

```java
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.6.1</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.6.1</version>
		</dependency>

```

Here I am using 2.6.1, if there is any latest version present. Use that one.

#### Swagger configuration :
Create a Swagger configuration file parallel to the main Application class.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2);
    }
}
```

Here we are enabling Swagger2. Thats it!

Basic Swagger configuration is done. 

Now lets create some REST APIs

```java
@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping(value = "/users", produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public List<User> retreiveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("users/{id}")
    public User retreiveUser(@PathVariable Integer id) {
        try {
            User user = userDaoService.findOne(id);
            if (user == null) {
                throw new UserNotFoundException("id-" + id);
            }
            return user;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity createUser(@RequestBody @Valid User user) {
        User savedUser = null;
        try {
            savedUser = userDaoService.save(user);
            return new ResponseEntity(HttpStatus.CREATED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/users/{id}")
    public User deleteById(@PathVariable int id) {
        try {
            User user = userDaoService.deleteUser(id);
            return user;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist");
        }

    }
}
```
Here we created a User application where we can create, delete,getAllUser, getUserById.

So when we run the code we can get the API decription on 2 URLS

### 1. API DOCS

BASIC_URL/v2/api-docs

example  :  http://localhost:8080/v2/api-docs

It will give data in JSON format like this.

#### NOTE : Download chrome JSON formatter to get formatted data




![Swagger-API-DOCS](https://github.com/xebiarohan/SpringMicroservices/blob/master/restful-web-services/src/main/resources/images/Swagger1.jpg)

Here we have different information like 

#### info: 
Info contains the meta data of the project. Like the title, description,licence etc. We can change these information, We will see that later in this article.

```java
"info": {
"description": "Api Documentation",
"version": "1.0",
"title": "Api Documentation",
"termsOfService": "urn:tos",
"contact": {...},
"license": {
"name": "Apache 2.0",
"url": "http://www.apache.org/licenses/LICENSE-2.0"
}
}
```

#### host : 
Host is where we are publishing our services.
example :
```java
"host": "localhost:8080"
```
#### basePath :
This is also self expainatary. Its the base path of all the services.
```java
"basePath": "/"
```

####  tags:
It will tag the classes where we have the APIs i.e controllers 

```java
"tags": [
{
"name": "user-controller",
"description": "User Controller"
},
{
"name": "basic-error-controller",
"description": "Basic Error Controller"
}
]
```
#### paths:
Its the most important detail. It will tell how many APIs we have, what are the end points of those services, sample request and all the other information related to the APIs.

```java
"paths": {
"/error": {...},
"/users": {...},
"/users/{id}": {...}
}
```
If we open 1 API. It contains all the information related to that API

```java
"/users": {
"get": {
"tags": [
"user-controller"
],
"summary": "retreiveAllUsers",
"operationId": "retreiveAllUsersUsingGET",
"consumes": [
"application/json"
],
"produces": [
"application/xml",
"application/json"
],
"responses": {
"200": {
"description": "OK",
"schema": {
"type": "array",
"items": {
"$ref": "#/definitions/User"
}
}
},
"401": {
"description": "Unauthorized"
},
"403": {
"description": "Forbidden"
},
"404": {
"description": "Not Found"
}
}
}
```

If we see here we can see that we have User GET service. All the information like consumes,produces,type,response types all are listed here. 'type' and 'items' describes the response type. This service will return an array of User type.

#### definitions :
It is used to list all the other POJO classes which we are using in out APIs like :

```java
"definitions": {
"ResponseEntity": {...},
"User": {
"type": "object",
"properties": {
"birthDate": {
"type": "string",
"format": "date-time"
},
"id": {
"type": "integer",
"format": "int32"
},
"name": {
"type": "string"
}
},
"description": "User description"
}
}
```

Here we can see User POJO with all the variables with there datatype is mentioned.


### 2. SWAGGER-UI

BASIC_URL/swagger-ui.html
example: http://localhost:8080/swagger-ui.html


![Swagger-UI](https://github.com/xebiarohan/SpringMicroservices/blob/master/restful-web-services/src/main/resources/images/Swagger2.png)




Here we can see all the controllers we have in our project and when we click on the Expand Operations we can see All the APIs in that controller with all the details.


![Swagger-UI](https://github.com/xebiarohan/SpringMicroservices/blob/master/restful-web-services/src/main/resources/images/Swagger3.png)



#### Advance Configuration.



















