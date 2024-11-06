package com.todolistapi.todolistapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolistapi.todolistapi.entity.Account;
import com.todolistapi.todolistapi.repo.AccountRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    AccountRepo accountRepo;
    ObjectMapper objectMapper;

    public UserService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
        this.objectMapper = new ObjectMapper();
    }

    public String register(Account rawPassword) {

        if (accountRepo.findByEmail(rawPassword.getEmail()).isPresent()) {
            return "User with this email already exists";
        }

        Account savedAccount = accountRepo.save(rawPassword);
        String response = null;

        try {
            response = objectMapper.writeValueAsString(savedAccount);
        } catch (JsonProcessingException e) {
            return "Something went wrong with saving the account";
        }

        return response;
    }

    public boolean login(String rawPassword, String storedHashedPassword) {
        return true;
    }
}
