package mx.edu.utex.todolist.category.control;

import jakarta.transaction.Transactional;
import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.category.model.CategoryRepository;
import mx.edu.utex.todolist.utils.Message;
import mx.edu.utex.todolist.utils.TypesResponse;
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

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Registrar Categoria
    public ResponseEntity<Message> registerCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            return new ResponseEntity<>(new Message("Ya existe una categoría con el mismo nombre", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        categoryRepository.save(category);  // Guardamos la categoría
        return new ResponseEntity<>(new Message("Se registró la categoría", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Consultar todas las categorías
    public ResponseEntity<Message> consultCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay categorías", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(categories, "Listado de categorías", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Consultar categoría por ID
    public ResponseEntity<Message> idCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(category.get(), "Categoría encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Eliminar categoría
    public ResponseEntity<Message> deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        categoryRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Categoría eliminada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Actualizar categoría
    public ResponseEntity<Message> updateCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(category.getId());
        if (!existingCategory.isPresent()) {
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        categoryRepository.save(category);
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
    public ResponseEntity<Message> addCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            return new ResponseEntity<>(new Message("Categoría ya existente", TypesResponse.ERROR), HttpStatus.CONFLICT);
        }

        categoryRepository.save(category);
        return new ResponseEntity<>(new Message("Categoría agregada con éxito", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }
}
