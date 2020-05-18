package com.microservices.restfulwebservices.dao;

import com.microservices.restfulwebservices.dtos.User;
import com.microservices.restfulwebservices.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int userCount = 3;

    static {
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Alex", new Date()));
        users.add(new User(3, "Peter", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        return users.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public User deleteUser(int id) {
        User user = users
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User doesnot exis"));

        if(user != null){
            users = users.stream().filter(x -> x.getId()!= id).collect(Collectors.toList());
        }
        return user;

    }
}
