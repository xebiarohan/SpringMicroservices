package com.microservices.restfulwebservices.controllers;

import com.microservices.restfulwebservices.dao.UserDaoService;
import com.microservices.restfulwebservices.dtos.User;
import com.microservices.restfulwebservices.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
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
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
       }
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user) {
        User savedUser = userDaoService.save(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public User deleteById(@PathVariable int id){
        try {
            User user = userDaoService.deleteUser(id);
            return user;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"User does not exist");
        }

    }
}
