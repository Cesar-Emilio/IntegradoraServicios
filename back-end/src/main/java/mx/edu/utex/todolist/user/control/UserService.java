package mx.edu.utex.todolist.user.control;

import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import mx.edu.utex.todolist.security.JwtUtil;
import mx.edu.utex.todolist.security.UserDetailsServiceImpl;
import mx.edu.utex.todolist.security.dto.AuthResponse;
import mx.edu.utex.todolist.task.model.TaskRepository;
import mx.edu.utex.todolist.user.model.*;
import mx.edu.utex.todolist.utils.EmailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import mx.edu.utex.todolist.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import mx.edu.utex.todolist.utils.TypesResponse;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final ProyectRepository proyectRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailSender emailSender;

    UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserService(UserRepository userRepository, ProyectRepository proyectRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EmailSender emailSender, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.proyectRepository = proyectRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailSender = emailSender;
        this.userDetailsService = userDetailsService;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<User> users = userRepository.findAll();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(users, "Listado de usuarios", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> register(UserDTO dto) {
        if(validateDTOAttributes(dto)) {
            logger.error("Los atributos exceden el número de caracteres");
            return new ResponseEntity<>(new Message("Los atributos exceden el número de caracteres", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            logger.error("El correo ya esta registrado");
            return new ResponseEntity<>(new Message("El correo ya esta registrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setNombre(dto.getName());
        user.setApellido(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setTelefono(dto.getPhone());
        user.setStatus(true);
        user.setAdmin("ROLE_USER");
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAdmin(dto.getAdmin());

        user = userRepository.saveAndFlush(user);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se registró correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        AuthResponse response = authenticateAndGenerateToken(user.getEmail());
        logger.info("El registro ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(response, "El usuario se registró correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> update(Long id, UserDTO dto) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se encontró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        if(validateDTOAttributes(dto)) {
            logger.error("Los atributos exceden el número de caracteres");
            return new ResponseEntity<>(new Message("Los atributos exceden el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        user.setNombre(dto.getName());
        user.setApellido(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setTelefono(dto.getPhone());
        user.setPassword(dto.getPassword());
        user.setAdmin(dto.getAdmin());

        user = userRepository.saveAndFlush(user);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(user, "El usuario se actualizó correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> changeStatus(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se encontró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        user.setStatus(!user.isStatus());
        user = userRepository.saveAndFlush(user);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(user, "El usuario se actualizó correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> changeAdmin(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se encontró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        user.setAdmin(user.getAdmin().equals("ROLE_USER") ? "ROLE_ADMIN" : "ROLE_USER");
        user = userRepository.saveAndFlush(user);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(user, "El usuario se actualizó correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se encontró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(user);
        logger.info("La eliminación ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(user, "El usuario se eliminó correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se encontró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(user, "El usuario se encontró correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> changePassword(Long id, ChangePasswordDTO password) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            logger.error("El usuario no se encontró");
            return new ResponseEntity<>(new Message("El usuario no se encontró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(password.getNewPassword().length() > 100) {
            logger.error("La contraseña excede el número de caracteres");
            return new ResponseEntity<>(new Message("La contraseña excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if(!passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
            logger.error("La contraseña actual no coincide");
            return new ResponseEntity<>(new Message("La contraseña actual no coincide", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(password.getNewPassword()));

        user = userRepository.saveAndFlush(user);
        if(user == null) {
            logger.error("El usuario no se actualizó correctamente");
            return new ResponseEntity<>(new Message("El usuario no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(user, "El usuario se actualizó correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> logout(String token) {
        try {
            return new ResponseEntity<>(new Message("Sesión cerrada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Error al cerrar sesión", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Message> solicitudeChangePassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            logger.error("Correo no registrado");
            return new ResponseEntity<>(new Message("Correo no registrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        String resetToken = createResetToken(email);
        logger.info("Correo de restablecimiento enviado, token: " + resetToken);
        String subject = "Restablecimiento de contraseña";
        String body = "<p>Hola, " + user.getNombre() + ":</p>"
                + "<p>Haz clic en el siguiente enlace para restablecer tu contraseña: localhost:3000/reset-password?token=" + resetToken + "</p>"
                + "<a href='localhost:3000/reset-password?token=" + resetToken + "'>Restablecer contraseña</a>"
                + "<p>Este enlace expirará en 30 minutos.</p>";

        emailSender.sendEmail(email, subject, body);
        return ResponseEntity.ok(new Message("Correo de restablecimiento enviado", TypesResponse.SUCCESS));
    }

    @Transactional
    public ResponseEntity<Message> changePasswordBySolicitude(String token, String password) {
        if(password.length() > 100 ){
            logger.error("La contraseña excede el número de caracteres");
            return new ResponseEntity<>(new Message("La contraseña excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(!validateResetToken(token)) {
            logger.error("Token inválido o expirado");
            return new ResponseEntity<>(new Message("Token inválido o expirado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByResetToken(token).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(password));
        logger.info("Contraseña actualizada: " + passwordEncoder.encode(password));
        user.setResetToken(null);
        user.setResetTokenExpiration(null);

        user = userRepository.saveAndFlush(user);
        if(user == null) {
            logger.info("El usuario no se actualizó correctamente");
            return new ResponseEntity<>(new Message("El usuario no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("Contraseña actualizada correctamente");
        return ResponseEntity.ok(new Message("Contraseña actualizada correctamente", TypesResponse.SUCCESS));
    }

    private boolean validateDTOAttributes(UserDTO dto) {
        return dto.getName().length() > 50 || dto.getLastname().length() > 50 || dto.getEmail().length() > 50 || String.valueOf(dto.getPhone()).length() > 10 || dto.getPassword().length() > 100;
    }

    private boolean validateIdTask(Long id) {
        return taskRepository.findById(id).isPresent();
    }

    private boolean validateIdUser(Long id) {
        return userRepository.findById(id).isPresent();
    }

    private boolean isTaskInProyectAndHasUser(Long idProyect, Long idTask, Long idUser) {
        return taskRepository.findByIdAndProyectIdAndResponsible(idTask, idProyect, idUser).isPresent();
    }
    private AuthResponse authenticateAndGenerateToken(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String jwt = jwtUtil.generateToken(userDetails);
        long expirationTime = jwtUtil.getExpirationTime();

        return new AuthResponse(jwt, user.getId(), user.getEmail(), user.getAdmin(), expirationTime);
    }

    public String createResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(30);

        user.setResetToken(token);
        user.setResetTokenExpiration(expirationTime);

        userRepository.save(user);
        return token;
    }

    public boolean validateResetToken(String token) {
        return userRepository.findByResetToken(token)
                .filter(user -> user.getResetTokenExpiration().isAfter(LocalDateTime.now()))
                .isPresent();
    }
}
