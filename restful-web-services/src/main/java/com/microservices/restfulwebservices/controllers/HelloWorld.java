package com.microservices.restfulwebservices.controllers;


import com.microservices.restfulwebservices.dtos.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorld {

//    @Autowired
//    private MessageSource messageSource;

    @GetMapping("/hello-world")
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello world");
    }

    @GetMapping("/hello-world-bean/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello world %s", name));
    }

    @GetMapping("/hello-world-internationalization")
    public ResponseEntity<String> helloWorldInternationalization(@RequestHeader(name="Accept-Language",required = false) Locale locale) {
//        return new ResponseEntity<>(
//                messageSource.getMessage("good.morning.message", null, locale),
//                HttpStatus.OK);
        return new ResponseEntity<>("Good morning",HttpStatus.OK);
    }

}
