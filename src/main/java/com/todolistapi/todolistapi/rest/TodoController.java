package com.todolistapi.todolistapi.rest;

import com.todolistapi.todolistapi.entity.Todo;
import com.todolistapi.todolistapi.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoController {

    TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    public ResponseEntity<Object> register(@RequestHeader String authorization, @RequestBody Todo post) {
        return todoService.addPost(authorization, post);
    };

    @PutMapping("/todos/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,
                                         @RequestHeader String authorization,
                                         @RequestBody Todo post) {
        return todoService.updatePost(id, authorization, post);
    };

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         @RequestHeader String authorization) {
        return todoService.deletePost(id, authorization);
    };

    @GetMapping("/todos")
    public ResponseEntity<Object> list(@RequestParam(name = "page", required = false) int page,
                                       @RequestParam(name = "limit", required = false) int limit,
                                       @RequestHeader String authorization) {
        return todoService.listPosts(authorization, page, limit);
    };
}
