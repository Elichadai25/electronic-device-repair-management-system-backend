package auca.example.electronic_device_repair_management_syste.model;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "workbench")
public class Workbench {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int benchNumber;

    private String name;

    public Workbench() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getBenchNumber() {
        return benchNumber;
    }

    public void setBenchNumber(int benchNumber) {
        this.benchNumber = benchNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
