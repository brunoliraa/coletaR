package com.br.coletar.integration;

import com.br.coletar.model.User;
import com.br.coletar.repository.UserRepository;
import com.br.coletar.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    @MockBean
    private UserRepository userRepositoryMock;

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplateRoleUser;

    @Lazy
    @TestConfiguration
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("user", "user");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @BeforeEach
    public void setUp() {

        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(UserCreator.createValdiUser()));
    }

    @Test
    @DisplayName("findById returns a user when Success")
    public void findById_returns_User_WhenSuccess(){
        Long expectedId = UserCreator.createValdiUser().getId();

        User user = testRestTemplateRoleUser.getForObject("/api/v1/users/1", User.class);

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(expectedId).isEqualTo(user.getId());


    }
}
