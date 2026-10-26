package auca.example.electronic_device_repair_management_syste.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import auca.example.electronic_device_repair_management_syste.model.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, UUID> {

    @Query("SELECT d FROM DeviceType d WHERE d.technicians IS EMPTY")
    List<DeviceType> findUnusedDeviceTypes();

}
