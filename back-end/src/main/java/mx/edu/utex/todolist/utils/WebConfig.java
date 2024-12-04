package mx.edu.utex.todolist.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite todas las rutas
                .allowedOrigins("http://localhost:3000") // Permite el origen especificado
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite los métodos HTTP especificados
                .allowedHeaders("*") // Permite todos los headers
                .exposedHeaders("Authorization") // Permite exponer el header especificado
                .allowCredentials(true); // Permite las credenciales
    }
}
