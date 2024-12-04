package mx.edu.utex.todolist;

import mx.edu.utex.todolist.category.control.CategoryService;
import mx.edu.utex.todolist.category.model.CategoryDTO;
import mx.edu.utex.todolist.category.model.CategoryRepository;
import mx.edu.utex.todolist.proyect.control.ProyectService;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import mx.edu.utex.todolist.user.control.UserService;
import mx.edu.utex.todolist.user.model.User;
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
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest{

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProyectService proyectService;
    @Autowired
    private ProyectRepository proyectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        proyectRepository.deleteAll();
        userRepository.deleteAll();

        userService.register(new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER"));
        Optional<User> savedUser = userRepository.findByEmail("erickhumbetotc@gmail.com");
        List<Long> list = new ArrayList<>();
        list.add(savedUser.get().getId());
        proyectService.register(new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list));

    }
    @AfterEach
    void setup2() {
        categoryRepository.deleteAll();
        proyectRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes", proyectRepository.findByName("Controlador Tareas").get().getId(), true);

        Assertions.assertEquals(HttpStatus.CREATED, categoryService.registerCategory(dto).getStatusCode());
        //List<User> users = (List<User>) userService.findAll().getBody().getResult();
        //Assertions.assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(dto.getEmail())));
    }

    @Test
    public void testFindAllByProyect() {
        categoryService.registerCategory(new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes", proyectRepository.findByName("Controlador Tareas").get().getId(), true));
        Assertions.assertEquals(HttpStatus.OK, categoryService.findAllByProyect(1L).getStatusCode());
    }

    @Test
    public void testFindActive() {
        categoryService.registerCategory(new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes", proyectRepository.findByName("Controlador Tareas").get().getId(), true));

        Assertions.assertEquals(HttpStatus.OK, categoryService.findActive().getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testFindById() {
        categoryService.registerCategory(new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes", proyectRepository.findByName("Controlador Tareas").get().getId(), true));

        Assertions.assertEquals(HttpStatus.OK, categoryService.findById(categoryRepository.findByName("Pendiente").get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }
    /*

    @Test
    public void testDeleteCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes", 1L);
        categoryService.registerCategory(dto);

        Assertions.assertEquals(HttpStatus.OK, categoryService.deleteCategory(categoryRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testUpdateCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes", 1L);
        categoryService.registerCategory(dto);

        dto.setDescription("Esta es una categoria de tareas pendientes y atrasados");

        Assertions.assertEquals(HttpStatus.OK, categoryService.updateCategory(dto, categoryRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testActivateCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes", 1L);
        categoryService.registerCategory(dto);

        Assertions.assertEquals(HttpStatus.OK, categoryService.activateCategory(categoryRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testDesactivateCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes", 1L);
        categoryService.registerCategory(dto);

        Assertions.assertEquals(HttpStatus.OK, categoryService.desactivateCategory(categoryRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

     */
}