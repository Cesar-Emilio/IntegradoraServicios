package mx.edu.utex.todolist;

import mx.edu.utex.todolist.user.control.UserService;
import mx.edu.utex.todolist.user.model.User;
import mx.edu.utex.todolist.user.model.UserDTO;
import mx.edu.utex.todolist.user.model.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootTest
public class UserServiceTest{

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        userService.register(new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER"));

        List<User> users = (List<User>) userService.findAll().getBody().getResult();
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    public void testRegisterUser() {
        UserDTO dto = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");

        Assertions.assertEquals(HttpStatus.CREATED, userService.register(dto).getStatusCode());
        //List<User> users = (List<User>) userService.findAll().getBody().getResult();
        //Assertions.assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(dto.getEmail())));
    }

    @Test
    public void testUpdateUser() {
        UserDTO dto = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");
        userService.register(dto);

        dto.setLastname("Carvajal");

        Assertions.assertEquals(HttpStatus.OK, userService.update(userRepository.findByEmail(dto.getEmail()).get().getId(), dto).getStatusCode());

    }

    @Test
    public void testChangeStatus() {
        UserDTO dto = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");
        userService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, userService.changeStatus(userRepository.findByEmail(dto.getEmail()).get().getId()).getStatusCode());
    }

    @Test
    public void testChangeAdminRole() {
        UserDTO dto = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");
        userService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, userService.changeAdmin(userRepository.findByEmail(dto.getEmail()).get().getId()).getStatusCode());
    }

    @Test
    public void testDeleteUser() {
        UserDTO dto = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");
        userService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, userService.delete(userRepository.findByEmail(dto.getEmail()).get().getId()).getStatusCode());
    }

    @Test
    public void testFindById() {
        UserDTO dto = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");
        userService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, userService.findById(userRepository.findByEmail(dto.getEmail()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testChangePassword() {
        UserDTO dto = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");
        userService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, userService.changePassword(userRepository.findByEmail(dto.getEmail()).get().getId(), "54321").getStatusCode());
    }

    @Test
    public void testSolicitudeChangePassword() {
        UserDTO dto = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");
        userService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, userService.solicitudeChangePassword(dto.getEmail()).getStatusCode());
        //Assertions.assertEquals("Correo de restablecimiento enviado", userService.solicitudeChangePassword(user.getEmail()).getBody().get());
    }

    @Test
    public void testLogout() {

        Assertions.assertEquals(HttpStatus.OK, userService.logout("").getStatusCode());
        //Assertions.assertEquals("Sesión cerrada correctamente", response.getBody().getMessage());
    }
}