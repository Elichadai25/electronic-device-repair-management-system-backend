package auca.example.electronic_device_repair_management_syste.repository;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import auca.example.electronic_device_repair_management_syste.model.RepairOrder;
import auca.example.electronic_device_repair_management_syste.model.RepairStatus;

public interface RepairOrderRepository extends JpaRepository<RepairOrder, UUID> {
    List<RepairOrder> findByStatusOrderByReceivedDateAsc(RepairStatus status);

    List<RepairOrder> findByReceivedDateBetween(
            LocalDate start, LocalDate end);

    boolean existsByTechnician_IdAndReceivedDateAndStatusNot(
            UUID technicianId,
            LocalDate receivedDate,
            RepairStatus status);
}
