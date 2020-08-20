package com.br.coletar.controller;

import com.br.coletar.dto.LoginRequest;
import com.br.coletar.dto.Response;

import com.br.coletar.model.User;
import com.br.coletar.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<Response<User>> save(@Valid @RequestBody User user, BindingResult result){
        return userService.save(user, result);
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<List<User>> findAll(){
        return userService.findAll();
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return userService.delete(id);
    }

    @PutMapping("/api/v1/users/{id}")
    public ResponseEntity<Response<User>> update(@Valid @RequestBody User user,@PathVariable Long id
            , BindingResult result){
        return userService.update(id, user, result);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
       userService.login(loginRequest);
    }

    @GetMapping("/api/v1/users/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        userService.verifyAccount(token);
        return new ResponseEntity<>("account activated sucessfully", HttpStatus.OK);
    }
}
