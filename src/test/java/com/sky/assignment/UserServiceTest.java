package com.sky.assignment;

import com.sky.assignment.model.User;
import com.sky.assignment.repository.ProjectRepository;
import com.sky.assignment.repository.UserRepository;
import com.sky.assignment.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@TestPropertySource(properties = {"spring.flyway.enabled=false", "spring.main.lazy-initialization=true"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @Test
    public void testGetUser() {
        User user = new User();
        user.setName("me");
        user.setEmail("me@gmail.com");
        user.setPassword("letmein");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getUser(1);
        Assertions.assertNotNull(result);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setName("me");
        user.setEmail("me@gmail.com");
        user.setPassword("letmein");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.deleteUser(1);
        Assertions.assertNotNull(result);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).deleteById(1);
    }

}

