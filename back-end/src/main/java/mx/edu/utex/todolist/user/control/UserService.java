package mx.edu.utex.todolist.user.control;

import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import mx.edu.utex.todolist.security.JwtUtil;
import mx.edu.utex.todolist.security.UserDetailsServiceImpl;
import mx.edu.utex.todolist.security.dto.AuthResponse;
import mx.edu.utex.todolist.task.model.TaskRepository;
import mx.edu.utex.todolist.user.model.UserDTO;
import mx.edu.utex.todolist.utils.EmailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import mx.edu.utex.todolist.user.model.UserRepository;
import mx.edu.utex.todolist.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import mx.edu.utex.todolist.user.model.User;
import mx.edu.utex.todolist.utils.TypesResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Message> changePassword(Long id, String password) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se encontró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(password.length() > 50) {
            return new ResponseEntity<>(new Message("La contraseña excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(password));

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
            return new ResponseEntity<>(new Message("Correo no registrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        String resetToken = jwtUtil.generateTemporaryToken(email);

        emailSender.sendPasswordResetEmail(user.getEmail(), resetToken);
        return ResponseEntity.ok(new Message("Correo de restablecimiento enviado", TypesResponse.SUCCESS));
    }

    @Transactional
    public ResponseEntity<Message> changePasswordBySolicitude(String token, String password) {
        String email = jwtUtil.validateTemporaryToken(token);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(password));
        user = userRepository.saveAndFlush(user);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(new Message("Contraseña actualizada correctamente", TypesResponse.SUCCESS));
    }

    private boolean validateDTOAttributes(UserDTO dto) {
        return dto.getName().length() > 50 || dto.getLastname().length() > 50 || dto.getEmail().length() > 50 || String.valueOf(dto.getPhone()).length() > 10 || dto.getPassword().length() > 50;
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
}
