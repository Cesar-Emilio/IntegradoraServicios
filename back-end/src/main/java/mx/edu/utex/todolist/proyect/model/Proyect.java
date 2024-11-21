package mx.edu.utex.todolist.proyect.model;

import jakarta.persistence.*;

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

    public Proyect() {
    }

    public Proyect(String name, String abreviation, String description, boolean status) {
        this.name = name;
        this.abreviation = abreviation;
        this.description = description;
        this.status = status;
    }

    public Proyect(long id, String name, String abreviation, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.abreviation = abreviation;
        this.description = description;
        this.status = status;
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
}
