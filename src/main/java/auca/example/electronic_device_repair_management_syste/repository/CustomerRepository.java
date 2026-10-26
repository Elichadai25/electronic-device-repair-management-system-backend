package auca.example.electronic_device_repair_management_syste.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.example.electronic_device_repair_management_syste.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT r.customer FROM RepairOrder r " +
            "WHERE r.technician.id = :technicianId")
    List<Customer> findCustomersByTechnicianId(
            @Param("technicianId") UUID technicianId);
}
