package com.microservices.restfulwebservices.controllers;

import com.microservices.restfulwebservices.dao.UserDaoService;
import com.microservices.restfulwebservices.dtos.Post;
import com.microservices.restfulwebservices.dtos.User;
import com.microservices.restfulwebservices.exceptions.UserNotFoundException;
import com.microservices.restfulwebservices.repository.PostRepository;
import com.microservices.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(value = "/jpa/users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> retreiveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public User retreiveUser(@PathVariable Integer id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            return userOptional.orElseThrow(() -> new UserNotFoundException("User not found"));
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
        }
    }

    @PostMapping(value = "/jpa/users")
    public ResponseEntity createUser(@RequestBody @Valid User user) {
        User savedUser = null;
        try {
            User save = userRepository.save(user);
            return new ResponseEntity(HttpStatus.CREATED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteById(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist");
        }
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> getAllPostByUserId(@PathVariable int id) {
        try {
            User user = userRepository
                    .findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            return user.getPosts();
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }

    }

    @PostMapping(value = "/jpa/users/{id}/posts")
    public ResponseEntity createPost(@PathVariable int id,@RequestBody Post post) {
        try {
            User user = userRepository
                    .findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            post.setUser(user);
            postRepository.save(post);

            return new ResponseEntity(HttpStatus.CREATED);

        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }
}
