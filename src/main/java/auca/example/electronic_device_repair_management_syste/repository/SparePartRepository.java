package auca.example.electronic_device_repair_management_syste.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import auca.example.electronic_device_repair_management_syste.model.SparePart;

public interface SparePartRepository extends JpaRepository<SparePart, UUID> {

}
