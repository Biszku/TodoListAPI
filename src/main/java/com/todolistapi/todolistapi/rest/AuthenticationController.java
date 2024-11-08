package com.todolistapi.todolistapi.rest;

import com.todolistapi.todolistapi.entity.Account;
import com.todolistapi.todolistapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Account registerData) {
        return userService.register(registerData);
    };

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Account loginData) {
        return userService.login(loginData);
    }
}
