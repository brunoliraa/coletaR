package com.br.coletar.controller;

import com.br.coletar.dto.LoginRequest;
import com.br.coletar.dto.Response;

import com.br.coletar.model.User;
import com.br.coletar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Response<User>> save(@Valid @RequestBody User user, BindingResult result){
        return userService.save(user, result);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return userService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<User>> update(@Valid @RequestBody User user,@PathVariable Long id
            , BindingResult result){
        return userService.update(id, user, result);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
       userService.login(loginRequest);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        userService.verifyAccount(token);
        return new ResponseEntity<>("account activated sucessfully", HttpStatus.OK);
    }
}
