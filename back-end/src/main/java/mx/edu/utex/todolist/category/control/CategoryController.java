package mx.edu.utex.todolist.category.control;

import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.category.model.CategoryDTO;
import mx.edu.utex.todolist.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Registrar categoría
    @PostMapping("/register")
    public ResponseEntity<Message> registerCategory(@RequestBody CategoryDTO category) {
        return categoryService.registerCategory(category);
    }

    // Consultar todas las categorías
    @GetMapping("/findAll/{proyectId}")
    public ResponseEntity<Message> findAll(@PathVariable Long proyectId) {
        return categoryService.findAllByProyect(proyectId);
    }

    // Consultar categoría por ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/findActive")
    public ResponseEntity<Message> findActive() {
        return categoryService.findActive();
    }

    // Eliminar categoría
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    // Actualizar categoría
    @PutMapping("/update/{id}")
    public ResponseEntity<Message> updateCategory(@RequestBody CategoryDTO category, @PathVariable Long id) {
        return categoryService.updateCategory(category, id);
    }

    // Activar categoría
    @PutMapping("/activate/{id}")
    public ResponseEntity<Message> activateCategory(@PathVariable Long id) {
        return categoryService.activateCategory(id);
    }

    // Desactivar categoría
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Message> desactivateCategory(@PathVariable Long id) {
        return categoryService.desactivateCategory(id);
    }

    // Añadir nueva categoría
    @PostMapping("/add")
    public ResponseEntity<Message> addCategory(@RequestBody CategoryDTO category) {
        return categoryService.addCategory(category);
    }
}
