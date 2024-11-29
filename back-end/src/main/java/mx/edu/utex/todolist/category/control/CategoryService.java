package mx.edu.utex.todolist.category.control;

import jakarta.transaction.Transactional;
import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.category.model.CategoryDTO;
import mx.edu.utex.todolist.category.model.CategoryRepository;
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

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Registrar Categoria
    public ResponseEntity<Message> registerCategory(CategoryDTO dto) {
        if(dto.getName().length() > 50) {
            logger.error("El nombre de la categoría no puede exceder los 50 caracteres");
            return new ResponseEntity<>(new Message("El nombre de la categoría no puede exceder los 50 caracteres", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(dto.getDescription().length() > 100) {
            logger.error("La descripción de la categoría no puede exceder los 100 caracteres");
            return new ResponseEntity<>(new Message("La descripción de la categoría no puede exceder los 100 caracteres", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        Category category = new Category(dto.getName(), dto.getDescription(), dto.isStatus());
        category = categoryRepository.saveAndFlush(category);
        if (category == null) {
            logger.error("Error al registrar la categoría");
            return new ResponseEntity<>(new Message("Error al registrar la categoría", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("Categoría registrada");
        return new ResponseEntity<>(new Message("Categoría registrada", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Consultar todas las categorías
    public ResponseEntity<Message> findAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay categorías", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        logger.info("Listado de categorías realizado correctamente");
        return new ResponseEntity<>(new Message(categories, "Listado de categorías", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Consultar categorías activas
    public ResponseEntity<Message> findActive() {
        List<Category> categories = categoryRepository.findByStatusIsTrue();
        if (categories.isEmpty()) {
            logger.info("No hay categorías activas");
            return new ResponseEntity<>(new Message("No hay categorías activas", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        logger.info("Listado de categorías activas realizado correctamente");
        return new ResponseEntity<>(new Message(categories, "Listado de categorías activas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Consultar categoría por ID
    public ResponseEntity<Message> idCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            logger.error("Categoría no encontrada");
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        logger.info("Categoría encontrada");
        return new ResponseEntity<>(new Message(category.get(), "Categoría encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Eliminar categoría
    public ResponseEntity<Message> deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            logger.error("Categoría no encontrada");
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        logger.info("Categoría eliminada");
        categoryRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Categoría eliminada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Actualizar categoría
    public ResponseEntity<Message> updateCategory(CategoryDTO dto, Long id) {
        if(dto.getName().length() > 50) {
            logger.error("El nombre de la categoría no puede exceder los 50 caracteres");
            return new ResponseEntity<>(new Message("El nombre de la categoría no puede exceder los 50 caracteres", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(dto.getDescription().length() > 100) {
            logger.error("La descripción de la categoría no puede exceder los 100 caracteres");
            return new ResponseEntity<>(new Message("La descripción de la categoría no puede exceder los 100 caracteres", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            logger.error("Categoría no encontrada");
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        Category updatedCategory = category.get();
        updatedCategory.setName(dto.getName());
        updatedCategory.setDescription(dto.getDescription());
        updatedCategory.setStatus(dto.isStatus());
        updatedCategory = categoryRepository.saveAndFlush(updatedCategory);
        if (updatedCategory == null) {
            logger.error("Error al actualizar la categoría");
            return new ResponseEntity<>(new Message("Error al actualizar la categoría", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("Categoría actualizada");
        return new ResponseEntity<>(new Message("Categoría actualizada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Activar categoría
    public ResponseEntity<Message> activateCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Category updatedCategory = category.get();
        if (updatedCategory.isStatus()) {
            return new ResponseEntity<>(new Message("La categoría ya está activa", TypesResponse.WARNING), HttpStatus.OK);
        }

        updatedCategory.setStatus(true);
        categoryRepository.save(updatedCategory);

        return new ResponseEntity<>(new Message("Categoría activada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Desactivar categoría
    public ResponseEntity<Message> desactivateCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Category updatedCategory = category.get();
        if (!updatedCategory.isStatus()) {
            return new ResponseEntity<>(new Message("La categoría ya está desactivada", TypesResponse.WARNING), HttpStatus.OK);
        }

        updatedCategory.setStatus(false);
        categoryRepository.save(updatedCategory);
        return new ResponseEntity<>(new Message("Categoría desactivada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Añadir categoría
    public ResponseEntity<Message> addCategory(CategoryDTO dto) {
        if(dto.getName().length() > 50) {
            logger.error("El nombre de la categoría no puede exceder los 50 caracteres");
            return new ResponseEntity<>(new Message("El nombre de la categoría no puede exceder los 50 caracteres", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(dto.getDescription().length() > 100) {
            logger.error("La descripción de la categoría no puede exceder los 100 caracteres");
            return new ResponseEntity<>(new Message("La descripción de la categoría no puede exceder los 100 caracteres", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        Category category = new Category(dto.getName(), dto.getDescription(), true);
        category = categoryRepository.saveAndFlush(category);
        if (category == null) {
            logger.error("Error al añadir la categoría");
            return new ResponseEntity<>(new Message("Error al añadir la categoría", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("Categoría añadida");
        return new ResponseEntity<>(new Message("Categoría añadida", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }
}
