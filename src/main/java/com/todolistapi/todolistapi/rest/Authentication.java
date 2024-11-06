package com.todolistapi.todolistapi.rest;

//import com.todolistapi.todolistapi.service.UserService;
import com.todolistapi.todolistapi.entity.Account;
import com.todolistapi.todolistapi.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Authentication {
    UserService userService;

    public Authentication(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Account rawPassword) {
        return userService.register(rawPassword);
    };

//    @GetMapping("/login")
//    public boolean login(String rawPassword, String storedHashedPassword) {
//        return userService.login(rawPassword, storedHashedPassword);
//    }
}
