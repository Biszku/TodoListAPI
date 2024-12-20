package com.todolistapi.todolistapi.service;

import com.todolistapi.todolistapi.entity.*;
import com.todolistapi.todolistapi.repo.AccountRepo;
import com.todolistapi.todolistapi.repo.TodoRepo;
import com.todolistapi.todolistapi.util.JWTManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final AccountRepo accountRepo;

    public TodoService(TodoRepo todoRepo, AccountRepo accountRepo) {
        this.todoRepo = todoRepo;
        this.accountRepo = accountRepo;
    }

    public ResponseEntity<Object> addPost(String authorization, Todo todo) {
        Account account = accountRepo.findByEmail(getEncodedEmail(authorization)).orElse(null);

        if (account == null) {
            return new ResponseEntity<>(new Message("Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        account.addPost(todo);
        return ResponseEntity.ok(todoRepo.save(todo));
    }

    public ResponseEntity<Object> updatePost(Long id, String authorization, Todo post) {
        Todo postInTodo;

        try {
            postInTodo = getPostByMail(id, getEncodedEmail(authorization));
        } catch (IndexOutOfBoundsException notFound) {
            return new ResponseEntity<>(new Message(notFound.getMessage()), HttpStatus.NOT_FOUND);
        } catch (SecurityException forbidden) {
            return new ResponseEntity<>(new Message(forbidden.getMessage()), HttpStatus.FORBIDDEN);
        }

        postInTodo.setTitle(post.getTitle());
        postInTodo.setDescription(post.getDescription());
        return ResponseEntity.ok(todoRepo.save(postInTodo));
    }

    public ResponseEntity<Object> deletePost(Long id, String authorization) {
        Todo postInTodo;

        try {
            postInTodo = getPostByMail(id, getEncodedEmail(authorization));
        } catch (IndexOutOfBoundsException notFound) {
            return new ResponseEntity<>(new Message(notFound.getMessage()), HttpStatus.NOT_FOUND);
        } catch (SecurityException forbidden) {
            return new ResponseEntity<>(new Message(forbidden.getMessage()), HttpStatus.FORBIDDEN);
        }

        todoRepo.delete(postInTodo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Object> listPosts(String authorization, int page, int limit) {
        Account account = accountRepo.findByEmail(getEncodedEmail(authorization)).orElse(null);

        if (account == null) {
            return new ResponseEntity<>(new Message("Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        List<Todo> posts = new ArrayList<>(limit);
        int postsAmount = account.getPosts().size();
        for (int i = 0; i < limit && isPageAvailable(page, limit , postsAmount); i++) {
            posts.add(account.getPosts().get(i));
        }
        ResponsePage response = new ResponsePage(posts, page, limit, account.getPosts().size());
        return ResponseEntity.ok(response);
    }

    private boolean isPageAvailable(int page, int limit, int totalPosts) {
        return (page - 1) * limit < totalPosts;
    }

    private String getEncodedEmail(String rawToken) {
        String token = rawToken.substring(7);
        AuthToken authToken = new AuthToken(token);
        return JWTManager.readJWT(authToken.getToken());
    }

    private Todo getPostByMail(Long id, String accountMail) {
        Todo todo = todoRepo.findById(id).orElse(null);

        if (todo == null) {
            throw new IndexOutOfBoundsException("Post not found");

        }

        if (!Objects.equals(todo.getAccount().getEmail(), accountMail)) {
            throw new SecurityException("Forbidden");
        }

        return todo;
    }
}
