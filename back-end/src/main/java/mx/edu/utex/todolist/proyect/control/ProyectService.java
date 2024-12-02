package mx.edu.utex.todolist.proyect.control;

import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import mx.edu.utex.todolist.task.model.TaskRepository;
import mx.edu.utex.todolist.utils.Message;
import mx.edu.utex.todolist.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Transactional
@Service
public class ProyectService {
    private static final Logger logger = LoggerFactory.getLogger(ProyectService.class);

    private final ProyectRepository proyectRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ProyectService(ProyectRepository proyectRepository, TaskRepository taskRepository) {
        this.proyectRepository = proyectRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Proyect> proyects = proyectRepository.findAll();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(proyects, "Listado de proyectos", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        Proyect proyect = proyectRepository.findById(id).orElse(null);
        if(proyect == null) {
            return new ResponseEntity<>(new Message("No se ha encontrado el proyecto", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(proyect, "Proyecto encontrado", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findActive() {
        List<Proyect> proyects = proyectRepository.findByStatusIsTrue();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(proyects, "Listado de proyectos activos", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findInactive() {
        List<Proyect> proyects = proyectRepository.findByStatusIsFalse();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(proyects, "Listado de proyectos inactivos", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> register(ProyectDTO proyectDTO) {
        if(validateProyectDTOAttributes(proyectDTO)) {
            return new ResponseEntity<>(new Message("Los atributos exceden el número de carácteres", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        Proyect proyect = new Proyect();
        proyect.setName(proyectDTO.getName());
        proyect.setAbreviation(proyectDTO.getAbreviation());
        proyect.setDescription(proyectDTO.getDescription());
        proyect.setStatus(true);

        proyect = proyectRepository.saveAndFlush(proyect);
        if(proyect == null) {
            return new ResponseEntity<>(new Message("El proyecto no se registró correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("El registro ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(proyect, "El proyecto se registró correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> update(Long id, ProyectDTO proyectDTO) {
        Proyect proyect = proyectRepository.findById(id).orElse(null);
        if(proyect == null) {
            return new ResponseEntity<>(new Message("No se ha encontrado el proyecto a actualizar", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(validateProyectDTOAttributes(proyectDTO)) {
            return new ResponseEntity<>(new Message("Los atributos exceden el número de carácteres", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        proyect.setName(proyectDTO.getName());
        proyect.setAbreviation(proyectDTO.getAbreviation());
        proyect.setDescription(proyectDTO.getDescription());
        proyect = proyectRepository.saveAndFlush(proyect);
        if(proyect == null) {
            return new ResponseEntity<>(new Message("El proyecto no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("El proyecto ha sido actualizado correctamente");
        return new ResponseEntity<>(new Message(proyect, "El proyecto se actualizó correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> changeStatus(Long id) {
        Proyect proyect = proyectRepository.findById(id).orElse(null);
        if(proyect == null) {
            return new ResponseEntity<>(new Message("No se ha encontrado el proyecto", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        proyect.setStatus(!proyect.isStatus());
        proyect = proyectRepository.saveAndFlush(proyect);
        if(proyect == null) {
            return new ResponseEntity<>(new Message("El proyecto no se actualizó correctamente", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("El proyecto ha sido actualizado correctamente");
        return new ResponseEntity<>(new Message(proyect, "El proyecto se actualizó correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findTasks(Long id){
        Proyect proyect = proyectRepository.findById(id).orElse(null);
        if(proyect == null) {
            return new ResponseEntity<>(new Message("No se ha encontrado el proyecto", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(taskRepository.findByProyectId(id), "Listado de tareas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    private boolean validateProyectDTOAttributes(ProyectDTO proyectDTO) {
        return proyectDTO.getName().length() > 50 || proyectDTO.getAbreviation().length() > 10 || proyectDTO.getDescription().length() > 100;
    }
}
