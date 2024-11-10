package com.product.zenkino;

import com.product.zenkino.controllers.UserController;
import com.product.zenkino.entities.User;
import com.product.zenkino.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @InjectMocks
    private UserController userController;

    @Test
    public void test_getAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Yogesh");
        user1.setEmail("mangalyogesh.22@gmail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Harsh");
        user2.setEmail("harshmittal@gmail.com");

        List<User> mockUserList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(mockUserList);
        List<User> result = userController.getAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Yogesh", result.get(0).getName());
        assertEquals("Harsh", result.get(1).getName());
    }

    //TODO: Write the unit tests for all the other APIs.
}
