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
   "definitions":{
      "User":{
         "type":"object",
         "properties":{
            "birthDate":{
               "type":"string",
               "format":"date-time"
            },
            "id":{
               "type":"integer",
               "format":"int32"
            },
            "name":{
               "type":"string"
            }
         }
      },
      "description":"User description"
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
In the info tag we gets the meta data related to out project like title,version,description, basePath etc.

Or if we want to add some extra meta data we can do it by overriding the default information like :

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact("Rohan Aggarwal",
            "https://github.com/xebiarohan", "aggarwal.rohan17@gmail.com");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Interesting title",
            "Interesting description", "1.0", "urn:tos", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
	    
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Arrays.asList("application/xml", "application/json"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)                              
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}

```
So if you see the api method we are doing 3 things here :

1. Overriding the default information with custom info.
2. Setting a new meta data "produces" and setting its value.
3. Setting a new meta data "consumes" and setting its value.

So out docs will look like this :


swagger4 image goes here



### POJO validations
We have POJO classes details under definations. details like type , format of each variable but the end used will not be able to know about any validations which we applied on our POJO like min length, date in past etc 

So we can add @ApiModel and @ApiModelProperty in our POJO so that swagger can pick these validations.
In @ApiModelProperty we can define the notes for each variable.

```java
@ApiModel(description = "User description")
public class User {


    private Integer id;

    @Size(min = 2,max = 4)
    @ApiModelProperty(notes="Name should be of minimum 2 characters")
    private String name;

    @Past
    @ApiModelProperty(notes = "Birth date should be in past")
    private Date birthDate;

    public User() {
    }

    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
```

So once we add these annotations and notes. we will get these details under descriptions : 

image 5 goes here 












