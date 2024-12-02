package mx.edu.utex.todolist;

import mx.edu.utex.todolist.proyect.control.ProyectController;
import mx.edu.utex.todolist.proyect.control.ProyectService;
import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
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
public class ProyectServiceTest{

    @Autowired
    private ProyectService proyectService;
    @Autowired
    private ProyectRepository proyectRepository;

    @BeforeEach
    void setUp() {
        proyectRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        proyectService.register(new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas"));

        List<Proyect> proyects = (List<Proyect>) proyectService.findAll().getBody().getResult();
        Assertions.assertFalse(proyects.isEmpty());
    }

    @Test
    public void testFindById() {
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas");
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.findById(proyectRepository.findByName(dto.getName()).getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testFindActive() {
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas");
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.findActive().getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testFindInactive() {
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas");
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.findInactive().getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testRegisterProyect() {
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas");

        Assertions.assertEquals(HttpStatus.CREATED, proyectService.register(dto).getStatusCode());
        //List<User> users = (List<User>) userService.findAll().getBody().getResult();
        //Assertions.assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(dto.getEmail())));
    }

    @Test
    public void testUpdateProyect() {
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas");
        proyectService.register(dto);

        dto.setDescription("Una aplicación para controlar tus tareas ahora en movil!");

        Assertions.assertEquals(HttpStatus.OK, proyectService.update(proyectRepository.findByName(dto.getName()).getId(), dto).getStatusCode());
    }

    @Test
    public void testChangeStatus() {
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas");
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.changeStatus(proyectRepository.findByName(dto.getName()).getId()).getStatusCode());
    }

    @Test
    public void testFindTasks() {
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas");
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, proyectService.findTasks(proyectRepository.findByName(dto.getName()).getId()).getStatusCode());
    }
}