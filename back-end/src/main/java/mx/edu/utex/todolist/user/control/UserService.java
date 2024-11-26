package mx.edu.utex.todolist.user.control;

import mx.edu.utex.todolist.user.model.UserDTO;
import org.springframework.security.core.token.TokenService;
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
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<User> users = userRepository.findAll();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(users, "Listado de usuarios", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> register(UserDTO dto) {
        if(dto.getName().length() > 50) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dto.getSurname().length() > 50) {
            return new ResponseEntity<>(new Message("El apellido excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dto.getEmail().length() > 50) {
            return new ResponseEntity<>(new Message("El correo excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(String.valueOf(dto.getCellphone()).length() > 10) {
            return new ResponseEntity<>(new Message("El teléfono excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dto.getPassword().length() > 50) {
            return new ResponseEntity<>(new Message("La contraseña excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        User user = new User(dto.getName(), dto.getSurname(), dto.getEmail(), dto.getCellphone(), passwordEncoder.encode(dto.getPassword()), dto.isStatus(), dto.getAdmin())
                ;
        user = userRepository.saveAndFlush(user);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se registró correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("El registro ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(user, "El usuario se registró correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> update(Long id, UserDTO dto) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se encontró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(dto.getName().length() > 50) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dto.getSurname().length() > 50) {
            return new ResponseEntity<>(new Message("El apellido excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dto.getEmail().length() > 50) {
            return new ResponseEntity<>(new Message("El correo excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(String.valueOf(dto.getCellphone()).length() > 10) {
            return new ResponseEntity<>(new Message("El teléfono excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dto.getPassword().length() > 50) {
            return new ResponseEntity<>(new Message("La contraseña excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        user.setNombre(dto.getName());
        user.setApellido(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setTelefono(dto.getCellphone());
        user.setPassword(dto.getPassword());
        user.setStatus(dto.isStatus());
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
        user.setPassword(password);
        user = userRepository.saveAndFlush(user);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(user, "El usuario se actualizó correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> edit(Long id, UserDTO dto) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se encontró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(dto.getName().length() > 50) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dto.getSurname().length() > 50) {
            return new ResponseEntity<>(new Message("El apellido excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dto.getEmail().length() > 50) {
            return new ResponseEntity<>(new Message("El correo excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(String.valueOf(dto.getCellphone()).length() > 10) {
            return new ResponseEntity<>(new Message("El teléfono excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dto.getPassword().length() > 50) {
            return new ResponseEntity<>(new Message("La contraseña excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        user.setNombre(dto.getName());
        user.setApellido(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setTelefono(dto.getCellphone());
        user.setPassword(dto.getPassword());
        user = userRepository.saveAndFlush(user);
        if(user == null) {
            return new ResponseEntity<>(new Message("El usuario no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(user, "El usuario se actualizó correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> logout(String token) {
        tokenService.invalidateToken(token);
        return new ResponseEntity<>(new Message("Sesión cerrada con éxito", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> updatePassword(Long id, String password) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        user.setPassword(passwordEncoder.encode(password));
        user = userRepository.saveAndFlush(user);
        if (user == null) {
            return new ResponseEntity<>(new Message("Error al actualizar la contraseña", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message("Contraseña actualizada con éxito", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> solicitudeChangePassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new Message("Correo no registrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        // Lógica para enviar un correo con un token de restablecimiento
        String resetToken = tokenService.generateTokenForUser(user);
        // emailService.sendPasswordResetEmail(user.getEmail(), resetToken);

        return new ResponseEntity<>(new Message("Solicitud de cambio de clave enviada al correo", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> changePasswordBySolicitude(String token, String password) {
        Token resetToken = tokenService.findByToken(token);
        if (resetToken == null) {
            return new ResponseEntity<>(new Message("Token inválido", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if (resetToken.isExpired()) {
            return new ResponseEntity<>(new Message("Token expirado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.saveAndFlush(user);

        // Eliminar el token una vez utilizado
        tokenService.delete(resetToken);

        return new ResponseEntity<>(new Message("Contraseña actualizada con éxito", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
