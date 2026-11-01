package auca.example.electronic_device_repair_management_syste.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.example.electronic_device_repair_management_syste.model.SparePart;

public interface SparePartRepository extends JpaRepository<SparePart, UUID> {
    boolean existsByRepairOrder_Id(UUID repairOrderId);

    @Query("SELECT s FROM SparePart s WHERE s.repairOrder.customer.customerCode = :customerCode")
    List<SparePart> findByCustomerCode(@Param("customerCode") String customerCode);
}
