package mx.edu.utex.todolist.category.control;

import jakarta.transaction.Transactional;
import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.category.model.CategoryDTO;
import mx.edu.utex.todolist.category.model.CategoryRepository;
import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import mx.edu.utex.todolist.utils.Message;
import mx.edu.utex.todolist.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final ProyectRepository proyectRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProyectRepository proyectRepository) {
        this.categoryRepository = categoryRepository;
        this.proyectRepository = proyectRepository;
    }

    // Registrar Categoria
    public ResponseEntity<Message> registerCategory(CategoryDTO dto) {
        if(validateCategoryDTOAttributes(dto)) {
            logger.error("Los atributos de la categoría exceden el límite de caracteres");
            return new ResponseEntity<>(new Message("Los atributos de la categoría no cumplen con las restricciones", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setStatus(true);
        Optional<Proyect> proyectOptional = proyectRepository.findById(dto.getProyect_id());
        if (!proyectOptional.isPresent()) {
            logger.error("Proyecto con ID " + dto.getProyect_id() + " no encontrado.");
            return new ResponseEntity<>(new Message("Proyecto no encontrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        category.setProyect(proyectOptional.get());

        category = categoryRepository.saveAndFlush(category);
        if (category == null) {
            logger.error("Error al registrar la categoría");
            return new ResponseEntity<>(new Message("Error al registrar la categoría", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("Categoría registrada");
        return new ResponseEntity<>(new Message(category, "Categoría registrada", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> findAllByProyect(Long proyectId) {
        List<Category> categories = categoryRepository.findByProyectId(proyectId);
        logger.info("Listado de categorías realizado correctamente");
        return new ResponseEntity<>(new Message(categories, "Listado de categorías", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findActive() {
        List<Category> categories = categoryRepository.findByStatusIsTrue();

        logger.info("Listado de categorías activas realizado correctamente");
        return new ResponseEntity<>(new Message(categories, "Listado de categorías activas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            logger.error("Categoría no encontrada");
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        logger.info("Categoría encontrada");
        return new ResponseEntity<>(new Message(category.get(), "Categoría encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            logger.error("Categoría no encontrada");
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        categoryRepository.deleteById(id);
        logger.info("Categoría eliminada");
        return new ResponseEntity<>(new Message("Categoría eliminada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> updateCategory(CategoryDTO dto, Long id) {
        if(validateCategoryDTOAttributes(dto)) {
            logger.error("Los atributos de la categoría no cumplen con las restricciones");
            return new ResponseEntity<>(new Message("Los atributos de la categoría no cumplen con las restricciones", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            logger.error("Categoría no encontrada");
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        category = categoryRepository.saveAndFlush(category);
        if (category == null) {
            logger.error("Error al actualizar la categoría");
            return new ResponseEntity<>(new Message("Error al actualizar la categoría", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("Categoría actualizada");
        return new ResponseEntity<>(new Message("Categoría actualizada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Activar categoría
    public ResponseEntity<Message> activateCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        if (category.isStatus()) {
            return new ResponseEntity<>(new Message("La categoría ya está activa", TypesResponse.WARNING), HttpStatus.OK);
        }

        category.setStatus(true);
        categoryRepository.save(category);

        return new ResponseEntity<>(new Message("Categoría activada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> desactivateCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        if (!category.isStatus()) {
            return new ResponseEntity<>(new Message("La categoría ya está desactivada", TypesResponse.WARNING), HttpStatus.OK);
        }

        category.setStatus(false);
        category = categoryRepository.saveAndFlush(category);
        if (category == null) {
            return new ResponseEntity<>(new Message("Error al desactivar la categoría", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message("Categoría desactivada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> addCategory(CategoryDTO dto) {
        if(validateCategoryDTOAttributes(dto)) {
            logger.error("Los atributos de la categoría no cumplen con las restricciones");
            return new ResponseEntity<>(new Message("Los atributos de la categoría no cumplen con las restricciones", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setStatus(true);

        category = categoryRepository.saveAndFlush(category);
        if (category == null) {
            logger.error("Error al añadir la categoría");
            return new ResponseEntity<>(new Message("Error al añadir la categoría", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("Categoría añadida");
        return new ResponseEntity<>(new Message("Categoría añadida", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public boolean validateCategoryDTOAttributes(CategoryDTO dto) {
        return dto.getName().length() > 50 || dto.getDescription().length() > 100;
    }
}
