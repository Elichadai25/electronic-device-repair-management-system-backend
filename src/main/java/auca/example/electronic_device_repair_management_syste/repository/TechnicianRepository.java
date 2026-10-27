package auca.example.electronic_device_repair_management_syste.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.example.electronic_device_repair_management_syste.model.Technician;

public interface TechnicianRepository extends JpaRepository<Technician, UUID> {
    @Query("SELECT DISTINCT t FROM Technician t JOIN t.deviceTypes d " +
            "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Technician> findByDeviceTypeName(@Param("name") String name);

    boolean existsByWorkbench_BenchNumber(int benchNumber);
}
