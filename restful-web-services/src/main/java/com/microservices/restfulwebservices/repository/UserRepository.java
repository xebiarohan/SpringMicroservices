package com.microservices.restfulwebservices.repository;

import com.microservices.restfulwebservices.dtos.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Integer> {
}
