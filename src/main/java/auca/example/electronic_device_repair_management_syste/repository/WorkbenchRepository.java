package auca.example.electronic_device_repair_management_syste.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import auca.example.electronic_device_repair_management_syste.model.Workbench;

public interface WorkbenchRepository extends JpaRepository<Workbench, UUID> {

    @Query("SELECT w FROM Workbench w " +
            "WHERE NOT EXISTS (" +
            "SELECT t FROM Technician t WHERE t.workbench = w) " +
            "ORDER BY w.benchNumber ASC")
    List<Workbench> findFreeWorkbenches();
}
