package mx.edu.utex.todolist.proyect.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.task.model.Task;
import mx.edu.utex.todolist.user.model.User;

@Entity
@Table(name="proyect")
public class Proyect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "abreviation", columnDefinition = "VARCHAR(10)")
    private String abreviation;

    @Column(name = "description", columnDefinition = "VARCHAR(100)")
    private String description;

    @Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    @OneToMany(mappedBy = "proyect")
    @JsonManagedReference
    private List<Category> categories;

    @OneToMany(mappedBy = "proyect")
    @JsonManagedReference
    private List<Task> tasks;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_proyect",
            joinColumns = @JoinColumn(name = "proyect_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Proyect() {
    }

    public Proyect(String name, String abreviation, String description, boolean status) {
        this.name = name;
        this.abreviation = abreviation;
        this.description = description;
        this.status = status;
    }

    public Proyect(String name, String abreviation, String description) {
        this.name = name;
        this.abreviation = abreviation;
        this.description = description;
    }

    public Proyect(long id, String name, String abreviation, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.abreviation = abreviation;
        this.description = description;
        this.status = status;
    }

    public Proyect(long id, String name, String abreviation, String description, boolean status, List<Category> categories, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.abreviation = abreviation;
        this.description = description;
        this.status = status;
        this.categories = categories;
        this.tasks = tasks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setProyect_categories(List<Category> proyect_categories) {
        this.categories = proyect_categories;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> proyect_tasks) {
        this.tasks = proyect_tasks;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
