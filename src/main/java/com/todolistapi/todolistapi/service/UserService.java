package com.todolistapi.todolistapi.service;

import com.todolistapi.todolistapi.entity.Account;
import com.todolistapi.todolistapi.entity.AuthToken;
import com.todolistapi.todolistapi.entity.Message;
import com.todolistapi.todolistapi.repo.AccountRepo;
import com.todolistapi.todolistapi.util.JWTManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AccountRepo accountRepo;

    public UserService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public ResponseEntity<Object> register(Account registerData) {

        if (accountRepo.findByEmail(registerData.getEmail()).isPresent()) {
            return new ResponseEntity<>(new Message("User with this email already exists"), HttpStatus.CONFLICT);
        }

        registerData.hashPassword();
        Account savedAccount = accountRepo.save(registerData);
        AuthToken authToken = JWTManager.generateJWT(savedAccount.getEmail());

        return ResponseEntity.ok(authToken);
    }

    public ResponseEntity<Object> login(Account loginData) {
        var account = accountRepo.findByEmail(loginData.getEmail());

        if(account.isPresent()) {
            String accountSalt = account.get().getSalt();
            loginData.setSalt(accountSalt);
            loginData.hashPassword();

            if(account.get().getPassword().equals(loginData.getPassword())) {
                AuthToken authToken = JWTManager.generateJWT(account.get().getEmail());
                return ResponseEntity.ok(authToken);
            } else {
                return new ResponseEntity<>(new Message("Invalid password"), HttpStatus.UNAUTHORIZED);
            }
        }

        return new ResponseEntity<>(new Message("User not found"), HttpStatus.NOT_FOUND);
    }
}
