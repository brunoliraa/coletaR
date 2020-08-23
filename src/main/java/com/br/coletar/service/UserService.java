package com.br.coletar.service;

import com.br.coletar.dto.LoginRequest;
import com.br.coletar.dto.Response;
import com.br.coletar.exception.ColetarException;
import com.br.coletar.exception.UserNotFoundException;
import com.br.coletar.model.Email;
import com.br.coletar.model.User;
import com.br.coletar.model.VerificationToken;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final VerificationTokenRepositoryImp verificationTokenRepositoryImp;

    @Transactional
    public ResponseEntity<Response<User>> save(User user, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<User>(errors) {
            });
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.save(user);
        String token = criarTokenVerificacao(user);

        emailService.sendMail(new Email("confirmação de cadastro coletaR", user.getEmail()
                ,"obrigado por se cadastrar, clique no link para ativar a sua conta "+ "http://localhost:8080/api/v1/users/accountVerification/"+token));

        return new ResponseEntity<>(new Response<User>(user)
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

    public ResponseEntity<Void> delete(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("user with id "+ id+ " not found"));
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

    public void verifyAccount(String token) {
        Long userId = verificationTokenRepositoryImp.findByToken(token);
        if(userId==null){
            throw new ColetarException("token invalid");
        }
        fetchUserAndEnable(userId);
    }

    private void fetchUserAndEnable(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User "+userId+ " not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }


    public String criarTokenVerificacao(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setId(UUID.randomUUID().toString());
        verificationToken.setToken(token);
        verificationToken.setUserId(user.getId());
        verificationTokenRepositoryImp.save(verificationToken);
        return token;
    }

}
