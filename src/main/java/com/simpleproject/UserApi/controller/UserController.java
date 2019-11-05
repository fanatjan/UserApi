package com.simpleproject.UserApi.controller;

import com.simpleproject.UserApi.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping(produces = "application/json")
    public List<User> getUsers(){
        return Arrays.asList(new User(), new User());
    }
}
