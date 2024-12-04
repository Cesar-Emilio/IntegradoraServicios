package mx.edu.utex.todolist.task.model;


import jakarta.persistence.*;
import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.user.model.User;

import java.util.List;


@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(50)")
    private String description;

    @Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Proyect proyect;

    @ManyToMany(mappedBy = "tasks")
    private List<User> responsibles;

    // Constructores

    public Task() {
    }

    public Task(String name, String description, boolean status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Task(Long id, String name, String description, boolean status, Category category, Proyect proyect, List<User> responsibles) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.proyect = proyect;
        this.responsibles = responsibles;
    }

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

    public List<User> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<User> responsibles) {
        this.responsibles = responsibles;
    }


}