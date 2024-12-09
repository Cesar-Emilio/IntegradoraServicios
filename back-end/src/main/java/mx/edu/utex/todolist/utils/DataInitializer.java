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
            if (!userRepository.findByEmail("admin@admin.com").isPresent()) {
                User admin = new User(
                        "Carlos",
                        "Sánchez",
                        "admin@admin.com",
                        633456789L,
                        passwordEncoder.encode("12345"),
                        "ROLE_ADMIN"
                );
                userRepository.saveAndFlush(admin);
            }
        };
    }
}