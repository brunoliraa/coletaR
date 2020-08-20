package com.br.coletar.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.br.coletar.model.User;
import com.br.coletar.service.UserService;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest //UserController.class
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

//    @Autowired
//    private UserController userController;
//
//    @MockBean
//    private UserService userServiceMock;
//
//    @BeforeEach
//    public void setup() {
//        standaloneSetup(this.userController);
//    }
//
//
//    @Test
//    public void returnSuccess_whenSeachForAUser(){
//
//        when(userServiceMock.findById(1L))
//                .thenReturn(ResponseEntity.ok(User.builder().id(1L).name("regular user").build()));
//
//        given()
//                .accept(ContentType.JSON)
//                .when()
//                .get("/api/v1/users/{id}", 1)
//                .then()
//                .statusCode(HttpStatus.OK.value());
//    }

}
