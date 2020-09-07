package com.br.coletar.service;

import com.br.coletar.dto.Response;
import com.br.coletar.exception.UserNotFoundException;
import com.br.coletar.model.Email;
import com.br.coletar.model.User;
import com.br.coletar.repository.UserRepository;
import com.br.coletar.util.UserCreator;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.assertj.core.api.Assertions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepositoryMock;
    @MockBean
    private BindingResult result;
    @Mock
    private EmailService emailServiceMock;

    final UserService mockService= mock(UserService.class);

    @BeforeEach
    public void setUp() {
        List<User> produtos = Arrays.asList(UserCreator.createValdiUser());
        BDDMockito.when(userRepositoryMock.findAll())
                .thenReturn(produtos);
        BDDMockito.when(userRepositoryMock.save(UserCreator.createUserToSave()))
                .thenReturn(UserCreator.createValdiUser());
        BDDMockito.doNothing().when(emailServiceMock).sendMail(ArgumentMatchers.any(Email.class));
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(UserCreator.createValdiUser()));
        BDDMockito.doNothing().when(userRepositoryMock).delete(ArgumentMatchers.any(User.class));
        BDDMockito.when(mockService.criarTokenVerificacao(ArgumentMatchers.any(User.class)))
                .thenReturn(ArgumentMatchers.anyString());


//        String token =Mockito.spy(userService.criarTokenVerificacao(UserCreator.createUserToSave()));
//        BDDMockito.doNothing().when().criarTokenVerificacao(ArgumentMatchers.any(User.class));

    }

    @Test
    @DisplayName("findAll returns a list of users when successful")
    public void listAll_returnListOfUser_WhenSuccess(){

        String expectedName = UserCreator.createValdiUser().getName();

        List<User> users = userService.findAll();

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(!users.isEmpty());
        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }

//    @Test
//    @DisplayName("save creates a user when successful")
//    public void save_CreatesUser_WhenSuccessful() {
//        Long expectedId = UserCreator.createValdiUser().getId();
//
//        User savedUser = UserCreator.createUserToSave();
//
//        Response<User> user = userService.save(savedUser,result).getBody();
//
//        Assertions.assertThat(user.getData()).isNotNull();
//
//        Assertions.assertThat(user.getData().getId()).isNotNull();
//
//        Assertions.assertThat(user.getData().getId()).isEqualTo(expectedId);
//    }

    @Test
    @DisplayName("find by id return an User when success")
    public void findById_ReturnUser_WhenSuccess(){

        User user = UserCreator.createValdiUser();

        User userSaved =userService.findById(1L);

        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getId()).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(userSaved.getId());

    }

    @Test
    @DisplayName("find by id return a UserNotFoundException when user id does not exist")
    public void findById_throwsUserNotFoundException_WhenFailure(){

            BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                    .thenThrow(new UserNotFoundException("user not found"));

            Assertions.assertThatExceptionOfType(UserNotFoundException.class)
                    .isThrownBy(() ->userService.findById(2L));
    }

    @Test
    @DisplayName("delete removes a user when Success")
    public void delete_removesUser_WhenSuccess(){

        Assertions.assertThatCode(() ->userService.delete(ArgumentMatchers.anyLong()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete throws UserNotFoundException when user does not Exist")
    public void delete_throwsUserNotFoundException_WhenUserDoesNotExist(){

        BDDMockito.doThrow(new UserNotFoundException("user not found"))
                .when(userRepositoryMock).delete(ArgumentMatchers.any(User.class));

        Assertions.assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() ->userService.delete(2L));
    }


}