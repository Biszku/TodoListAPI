package com.todolistapi.todolistapi.service;

import com.todolistapi.todolistapi.entity.Account;
import com.todolistapi.todolistapi.repo.AccountRepo;
import com.todolistapi.todolistapi.util.JWTManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AccountRepo accountRepo;

    public UserService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Object register(Account registerData) {

        if (accountRepo.findByEmail(registerData.getEmail()).isPresent()) {
            return "User with this email already exists";
        }
        registerData.hashPassword();

        Account savedAccount = accountRepo.save(registerData);
        return JWTManager.generateJWT(savedAccount.getEmail());
    }

    public Object login(Account loginData) {
        var account = accountRepo.findByEmail(loginData.getEmail());

        if(account.isPresent()) {
            String accountSalt = account.get().getSalt();
            loginData.setSalt(accountSalt);
            loginData.hashPassword();
            if(account.get().getPassword().equals(loginData.getPassword())) {
                return JWTManager.generateJWT(account.get().getEmail());
            } else {
                return "Invalid password";
            }
        }
        return "User not found";
    }
}
