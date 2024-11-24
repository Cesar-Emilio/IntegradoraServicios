package mx.edu.utex.todolist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/login", "/register").permitAll()

                        //Rutas protegidas para ROLE_USER y ROLE_ADMIN
                        .requestMatchers("/users/login", "/users/logout", "/users/changePassword/**",
                                "/users/edit/**", "/users/solicitudeChangePassword/**",
                                "/users/changePasswordBySolicitude/**", "/users/update/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/proyects/findAll", "/proyects/find/**","/proyects/active", "/proyects/inactive",
                                "/proyects/getTasks").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/category/findAll").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        // Rutas protegidas exclusivas para ROLE_ADMIN
                        .requestMatchers("/users/changeStatus/**", "/users/find/**", "/users/findAll")
                        .hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/proyects/register", "/proyects/update/**", "/proyects/changeStatus/**",
                                ).hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/category/update/**", "/category/delete/**", "/category/activate/**",
                                "/category/deactivate/**", "/category/add").hasAuthority("ROLE_ADMIN")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}