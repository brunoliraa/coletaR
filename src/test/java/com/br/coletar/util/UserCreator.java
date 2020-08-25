package com.br.coletar.util;

import com.br.coletar.model.Email;
import com.br.coletar.model.User;

public class UserCreator {

    public static User createUserToSave(){
        return User.builder()
                .name("user")
                .username("user")
                .password("user")
                .email("user@email.com").build();
    }

    public static User createValdiUser(){
        return User.builder()
                .id(1L)
                .name("user")
                .username("user")
                .password("user")
                .email("user@email.com").build();
    }

    public static User createUserToUpdate(){
        return User.builder()
                .id(1L)
                .name("user updated")
                .username("user2")
                .password("user2")
                .email("user@email.com").build();
    }

    public static Email createEmail(){
        return new Email("teste", "user@email.com", "testando");
    }
}
