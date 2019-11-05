package com.simpleproject.UserApi.service;

import com.simpleproject.UserApi.entity.User;
import com.simpleproject.UserApi.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
//import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    private UserService userService;

    @BeforeAll
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
    }

//    @Test
//    public void test_getAllHosts() {
//        assertNotNull(hostService.findAll());
//    }
}
