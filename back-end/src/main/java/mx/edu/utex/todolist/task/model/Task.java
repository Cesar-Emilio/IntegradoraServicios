package mx.edu.utex.todolist.task.model;


import jakarta.persistence.*;
import jakarta.persistence.*;
import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.proyect.model.Proyect;


@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private boolean status;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Proyect proyect;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Proyect getProyect() {
        return proyect;
    }

    public void setProyect(Proyect project) {
        this.proyect = project;
    }
}