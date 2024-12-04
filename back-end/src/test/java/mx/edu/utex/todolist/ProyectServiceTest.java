package mx.edu.utex.todolist;

import mx.edu.utex.todolist.proyect.control.ProyectService;
import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import mx.edu.utex.todolist.user.control.UserService;
import mx.edu.utex.todolist.user.model.UserDTO;
import mx.edu.utex.todolist.user.model.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProyectServiceTest{

    @Autowired
    private ProyectService proyectService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProyectRepository proyectRepository;


    @BeforeEach
    void setUp() {
        proyectRepository.deleteAll();
        userRepository.deleteAll();
        userService.register(new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER"));

    }
    @AfterEach
    void setup2() {
        proyectRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Long> list = new ArrayList<>();
        list.add(userRepository.findByEmail("erickhumbetotc@gmail.com").get().getId());
        proyectService.register(new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list));

        List<Proyect> proyects = (List<Proyect>) proyectService.findAll().getBody().getResult();
        Assertions.assertFalse(proyects.isEmpty());
    }

    @Test
    public void testFindById() {
        List<Long> list = new ArrayList<>();
        list.add(userRepository.findByEmail("erickhumbetotc@gmail.com").get().getId());
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list);
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.findById(proyectRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testFindActive() {
        List<Long> list = new ArrayList<>();
        list.add(userRepository.findByEmail("erickhumbetotc@gmail.com").get().getId());
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list);
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.findActive().getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testFindInactive() {
        List<Long> list = new ArrayList<>();
        list.add(userRepository.findByEmail("erickhumbetotc@gmail.com").get().getId());
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list);
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.findInactive().getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testRegisterProyect() {
        List<Long> list = new ArrayList<>();
        list.add(userRepository.findByEmail("erickhumbetotc@gmail.com").get().getId());
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list);

        Assertions.assertEquals(HttpStatus.CREATED, proyectService.register(dto).getStatusCode());
        //List<User> users = (List<User>) userService.findAll().getBody().getResult();
        //Assertions.assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(dto.getEmail())));
    }

    @Test
    public void testUpdateProyect() {
        List<Long> list = new ArrayList<>();
        list.add(userRepository.findByEmail("erickhumbetotc@gmail.com").get().getId());
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list);
        proyectService.register(dto);

        dto.setDescription("Una aplicación para controlar tus tareas ahora en movil!");

        Assertions.assertEquals(HttpStatus.OK, proyectService.update(proyectRepository.findByName(dto.getName()).get().getId(), dto).getStatusCode());
    }

    @Test
    public void testChangeStatus() {
        List<Long> list = new ArrayList<>();
        list.add(userRepository.findByEmail("erickhumbetotc@gmail.com").get().getId());
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list);
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.changeStatus(proyectRepository.findByName(dto.getName()).get().getId()).getStatusCode());
    }

    @Test
    public void testFindTasks() {
        List<Long> list = new ArrayList<>();
        list.add(userRepository.findByEmail("erickhumbetotc@gmail.com").get().getId());
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list);
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.findTasks(proyectRepository.findByName(dto.getName()).get().getId()).getStatusCode());
    }
}