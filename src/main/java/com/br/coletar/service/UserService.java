package com.br.coletar.service;

import com.br.coletar.dto.LoginRequest;
import com.br.coletar.dto.Response;
import com.br.coletar.exception.UserNotFoundException;
import com.br.coletar.model.User;
import com.br.coletar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<Response<User>> save(User user, BindingResult result){

        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<User>(errors) {
            });
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setEnabled(true);
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

    public User getCurrentUser() {
        User principal = (User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findUserByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public void login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
}
