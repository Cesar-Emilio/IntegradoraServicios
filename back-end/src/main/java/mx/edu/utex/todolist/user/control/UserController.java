package mx.edu.utex.todolist.user.control;

import mx.edu.utex.todolist.user.model.User;
import mx.edu.utex.todolist.user.model.UserDTO;
import mx.edu.utex.todolist.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Message> findAll() {
        return userService.findAll();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Message> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Message> edit(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.edit(id, userDTO);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return userService.changeStatus(id);
    }


    @PostMapping("/logout")
    public ResponseEntity<Message> logout(@RequestHeader("Authorization") String token) {
        return userService.logout(token.replace("Bearer ", ""));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/updatePassword /{id}")
    public ResponseEntity<Message> UpdatePassword(@PathVariable Long id, @RequestBody String password) {
        return userService.updatePassword(id, password);
    }

    @PostMapping("/solicitudeChangePassword/{email}")
    public ResponseEntity<Message> solicitudeChangePassword(@PathVariable String email) {
        return userService.solicitudeChangePassword(email);
    }

    @PutMapping("/changePasswordBySolicitude/{token}")
    public ResponseEntity<Message> changePasswordBySolicitude(@PathVariable String token, @RequestBody String password) {
        return userService.changePasswordBySolicitude(token, password);
    }
}
