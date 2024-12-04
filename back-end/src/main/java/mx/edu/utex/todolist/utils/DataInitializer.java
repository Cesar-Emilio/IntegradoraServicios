package mx.edu.utex.todolist.utils;

import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.category.model.CategoryRepository;
import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import mx.edu.utex.todolist.task.model.Task;
import mx.edu.utex.todolist.task.model.TaskRepository;
import mx.edu.utex.todolist.user.model.User;
import mx.edu.utex.todolist.user.model.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder, ProyectRepository proyectRepository, CategoryRepository categoryRepository, TaskRepository taskRepository) {
        return args -> {
            if (!userRepository.findByEmail("erickhumbetotc@gmail.com").isPresent()) {
                User user = new User(
                        "Erick",
                        "Teja",
                        "erickhumbetotc@gmail.com",
                        777480654L,
                        passwordEncoder.encode("12345"),
                        "ROLE_USER"
                );
                userRepository.saveAndFlush(user);
            }
            if (!userRepository.findByEmail("laura.gonzalez@example.com").isPresent()) {
                User user = new User(
                        "Laura",
                        "González",
                        "laura.gonzalez@example.com",
                        612345678L,
                        passwordEncoder.encode("12345"),
                        "ROLE_USER"
                );
                userRepository.saveAndFlush(user);
            }
            if (!userRepository.findByEmail("carlos.sanchez@admin.com").isPresent()) {
                User admin = new User(
                        "Carlos",
                        "Sánchez",
                        "carlos.sanchez@admin.com",
                        633456789L,
                        passwordEncoder.encode("12345"),
                        "ROLE_ADMIN"
                );
                userRepository.saveAndFlush(admin);
            }
            if (!userRepository.findByEmail("ana.martinez@example.com").isPresent()) {
                User admin = new User(
                        "Ana",
                        "Martínez",
                        "ana.martinez@example.com",
                        698745123L,
                        passwordEncoder.encode("12345"),
                        "ROLE_ADMIN"
                );
                userRepository.saveAndFlush(admin);
            }

            if (!proyectRepository.findByName("Controlador Tareas").isPresent()) {
                Proyect proyect = new Proyect("Controlador Tareas", "CT", "Una aplicación para controlar tareas");
                proyectRepository.saveAndFlush(proyect);
            }

            if (!proyectRepository.findByName("Sistema de Inventarios").isPresent()) {
                Proyect proyect = new Proyect("Sistema de Inventarios", "SI", "Sistema para gestionar inventarios de productos");
                proyectRepository.saveAndFlush(proyect);
            }

            if (!proyectRepository.findByName("Gestión de Proyectos").isPresent()) {
                Proyect proyect = new Proyect("Gestión de Proyectos", "GP", "Aplicación para la gestión de proyectos empresariales");
                proyectRepository.saveAndFlush(proyect);
            }

            if (!categoryRepository.findByName("Pendiente").isPresent()) {
                Category category = new Category("Pendiente", "Tareas que aún no han sido comenzadas", proyectRepository.findByName("Controlador Tareas").get());
                categoryRepository.saveAndFlush(category);
            }

            if (!categoryRepository.findByName("En Progreso").isPresent()) {
                Category category = new Category("En Progreso", "Tareas que están siendo trabajadas", proyectRepository.findByName("Controlador Tareas").get());
                categoryRepository.saveAndFlush(category);
            }

            if (!categoryRepository.findByName("Finalizado").isPresent()) {
                Category category = new Category("Finalizado", "Tareas completadas", proyectRepository.findByName("Controlador Tareas").get());
                categoryRepository.saveAndFlush(category);
            }

            if (!taskRepository.findByName("Tarea 1").isPresent()) {
                Task task = new Task("Tarea 1", "Descripción de la tarea 1", categoryRepository.findByName("Pendiente").get());
                taskRepository.saveAndFlush(task);
            }

            if (!taskRepository.findByName("Tarea 2").isPresent()) {
                Task task = new Task("Tarea 2", "Descripción de la tarea 2", categoryRepository.findByName("Pendiente").get());
                taskRepository.saveAndFlush(task);
            }

            if (!taskRepository.findByName("Tarea 3").isPresent()) {
                Task task = new Task("Tarea 3", "Descripción de la tarea 3", categoryRepository.findByName("Pendiente").get());
                taskRepository.saveAndFlush(task);
            }

        };
    }
}