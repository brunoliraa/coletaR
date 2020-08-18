package com.br.coletar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ColetarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColetarApplication.class, args);
    }

}
