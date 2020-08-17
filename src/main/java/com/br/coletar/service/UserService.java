package com.br.coletar.service;

import com.br.coletar.dto.Response;
import com.br.coletar.exception.UserNotFoundException;
import com.br.coletar.model.User;
import com.br.coletar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<Response<User>> save(User user, BindingResult result){

        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<User>(errors) {
            });
        }

        return new ResponseEntity<>(new Response<User>(userRepository.save(user))
                , HttpStatus.CREATED);
    }

    public ResponseEntity<List<User>> findAll(){
        if(userRepository.findAll().isEmpty()){
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<User> findById(Long id){
        return ResponseEntity.ok(userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("user with id "+ id+ " not found")));

    }

    public ResponseEntity<String> delete(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("user with id "+ id+ " not found"));
        userRepository.delete(user);
        return new ResponseEntity<>("point "+id + " success deleted", HttpStatus.OK);
    }

    public ResponseEntity<Response<User>> update(Long id, User user, BindingResult result ){
        user.setId(id);
        return this.save(user, result);
    }
}
