package mx.edu.utex.todolist.proyect.control;

import mx.edu.utex.todolist.proyect.model.ProyectDTO;
import mx.edu.utex.todolist.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proyects")
public class ProyectController {

     @Autowired
    private ProyectService proyectService;

    @GetMapping("/findAll")
    public ResponseEntity<Message> findAll() {
        return proyectService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody ProyectDTO proyectDTO) {
        return proyectService.register(proyectDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Message> update(@PathVariable Long id, @RequestBody ProyectDTO proyectDTO) {
        return proyectService.update(id, proyectDTO);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return proyectService.changeStatus(id);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return proyectService.findById(id);
    }

    @GetMapping("/active")
    public ResponseEntity<Message> findActive() {
        return proyectService.findActive();
    }

    @GetMapping("/inactive")
    public ResponseEntity<Message> findInactive() {
        return proyectService.findInactive();
    }

    @GetMapping("/getTasks/{id}")
    public ResponseEntity<Message> findTasks(@PathVariable Long id) {
        return proyectService.findTasks(id);
    }
}
