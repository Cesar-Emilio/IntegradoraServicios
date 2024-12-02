package mx.edu.utex.todolist;

import mx.edu.utex.todolist.category.control.CategoryService;
import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.category.model.CategoryDTO;
import mx.edu.utex.todolist.category.model.CategoryRepository;
import mx.edu.utex.todolist.proyect.control.ProyectService;
import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

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

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        proyectRepository.deleteAll();
    }

    @Test
    public void testRegisterCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes");

        Assertions.assertEquals(HttpStatus.CREATED, categoryService.registerCategory(dto).getStatusCode());
        //List<User> users = (List<User>) userService.findAll().getBody().getResult();
        //Assertions.assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(dto.getEmail())));
    }

    @Test
    public void testFindAllByProyect() {
        ProyectDTO dto = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas");
        proyectService.register(dto);

        Assertions.assertEquals(HttpStatus.OK, categoryService.findAllByProyect(proyectRepository.findByName(dto.getName()).getId()).getStatusCode());
    }

    @Test
    public void testFindActive() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes");
        categoryService.registerCategory(dto);

        Assertions.assertEquals(HttpStatus.OK, categoryService.findActive().getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testFindById() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes");
        categoryService.registerCategory(dto);

        Assertions.assertEquals(HttpStatus.OK, categoryService.findById(categoryRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testDeleteCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes");
        categoryService.registerCategory(dto);

        Assertions.assertEquals(HttpStatus.OK, categoryService.deleteCategory(categoryRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testUpdateCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes");
        categoryService.registerCategory(dto);

        dto.setDescription("Esta es una categoria de tareas pendientes y atrasados");

        Assertions.assertEquals(HttpStatus.OK, categoryService.updateCategory(dto, categoryRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testActivateCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes");
        categoryService.registerCategory(dto);

        Assertions.assertEquals(HttpStatus.OK, categoryService.activateCategory(categoryRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testDesactivateCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes");
        categoryService.registerCategory(dto);

        Assertions.assertEquals(HttpStatus.OK, categoryService.desactivateCategory(categoryRepository.findByName(dto.getName()).get().getId()).getStatusCode());
        //Assertions.assertEquals(user.getEmail(), ((User) userService.findById(user.getId()).getBody().getResult()).getEmail());
    }

    @Test
    public void testAddCategory() {
        CategoryDTO dto = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes");

        Assertions.assertEquals(HttpStatus.CREATED, categoryService.registerCategory(dto).getStatusCode());
        //List<User> users = (List<User>) userService.findAll().getBody().getResult();
        //Assertions.assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(dto.getEmail())));
    }
}