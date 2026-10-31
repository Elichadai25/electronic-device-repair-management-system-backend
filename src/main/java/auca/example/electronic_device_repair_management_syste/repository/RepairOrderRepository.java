package auca.example.electronic_device_repair_management_syste.repository;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
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

  // dashboard methods
  long countByReceivedDate(LocalDate receivedDate);

  long countByStatus(RepairStatus status);

  @Query("SELECT r.status, COUNT(r) FROM RepairOrder r GROUP BY r.status")
  List<Object[]> countRepairOrdersByStatus();

  @Modifying
  @Transactional
  @Query("""
      UPDATE RepairOrder r
      SET r.status = auca.example.electronic_device_repair_management_syste.model.RepairStatus.DECLINED
      WHERE r.technician.id = :technicianId
        AND r.receivedDate = :date
        AND r.status <> auca.example.electronic_device_repair_management_syste.model.RepairStatus.FIXED
      """)
  int cancelRepairOrdersByTechnicianAndDate(
      @Param("technicianId") UUID technicianId,
      @Param("date") LocalDate date);

}
