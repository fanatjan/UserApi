package com.simpleproject.UserApi.service;

import com.simpleproject.UserApi.entity.User;
import com.simpleproject.UserApi.exception.NotValidParamException;
import com.simpleproject.UserApi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Simple test for User Service
 */
public class UserServiceTest {

    private UserService userService;

    @Before
    public void start(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        User defUser = User.builder()
                .email("myemail@mail.com")
                .password("mypass")
                .createTime(new Date())
                .lastUpdateTime(new Date())
                .id(1L).build();
        when(userRepository.findAll()).thenReturn(Arrays.asList(
                defUser,
                User.builder()
                        .email("myemail2@mail.com")
                        .password("mypass")
                        .createTime(new Date())
                        .lastUpdateTime(new Date())
                        .id(2L).build()
                )
        );
        when(userRepository.save(any())).thenReturn(defUser);
        when(userRepository.findById(any())).thenReturn(Optional.of(defUser));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder, entityManager);
    }

    @Test
    public void test_getAllHosts() {
        assertNotNull(userService.getAll());
    }

    @Test
    public void test_addUser() throws NotValidParamException {
        assertThrows(NotValidParamException.class, () -> userService.save(new User()));
        assertThrows(NotValidParamException.class, () -> userService.save(User.builder().email("").build()));
        assertThrows(NotValidParamException.class, () -> userService.save(User.builder().email("myemail@mail.com").build()));
        assertNotNull(userService.save(User.builder().email("myemail@mail.com").password("pass").lastUpdateTime(new Date()).createTime(new Date()).build()));
    }
}
