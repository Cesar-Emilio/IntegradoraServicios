package mx.edu.utex.todolist;

import mx.edu.utex.todolist.user.control.UserService;
import mx.edu.utex.todolist.user.model.User;
import mx.edu.utex.todolist.user.model.UserDTO;
import mx.edu.utex.todolist.user.model.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest{

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRegistrarUsuario() {
        UserDTO dto = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");

        userService.register(dto);

        List<User> users = (List<User>) userService.findAll().getBody().getResult();
        Assertions.assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(dto.getEmail())));
    }
}