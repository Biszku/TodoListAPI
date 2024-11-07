package com.todolistapi.todolistapi.rest;

import com.todolistapi.todolistapi.entity.Account;
import com.todolistapi.todolistapi.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class Authentication {
    UserService userService;

    public Authentication(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Object register(@RequestBody Account registerData) {
        return userService.register(registerData);
    };

    @PostMapping("/login")
    public Object login(@RequestBody Account loginData) {
        return userService.login(loginData);
    }
}
