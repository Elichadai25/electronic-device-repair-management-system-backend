package auca.example.electronic_device_repair_management_syste.model;

import java.util.UUID;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "spare_part")
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String partName;
    private String note;
    private int unitCost;
    private LocalDate fittedDate;

    @ManyToOne
    @JoinColumn(name = "repair_order_id")
    private RepairOrder repairOrder;

    public SparePart() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(RepairOrder repairOrder) {
        this.repairOrder = repairOrder;
    }

    public LocalDate getFittedDate() {
        return fittedDate;
    }

    public void setFittedDate(LocalDate fittedDate) {
        this.fittedDate = fittedDate;
    }
}
