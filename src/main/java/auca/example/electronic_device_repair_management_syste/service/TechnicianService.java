package auca.example.electronic_device_repair_management_syste.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.example.electronic_device_repair_management_syste.model.Technician;
import auca.example.electronic_device_repair_management_syste.model.Workbench;
import auca.example.electronic_device_repair_management_syste.model.DeviceType;
import auca.example.electronic_device_repair_management_syste.repository.TechnicianRepository;
import auca.example.electronic_device_repair_management_syste.repository.WorkbenchRepository;
import auca.example.electronic_device_repair_management_syste.repository.DeviceTypeRepository;

@Service
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final WorkbenchRepository workbenchRepository;
    private final DeviceTypeRepository deviceTypeRepository;

    public TechnicianService(
            TechnicianRepository technicianRepository,
            WorkbenchRepository workbenchRepository,
            DeviceTypeRepository deviceTypeRepository) {
        this.technicianRepository = technicianRepository;
        this.workbenchRepository = workbenchRepository;
        this.deviceTypeRepository = deviceTypeRepository;
    }

    public String save(Technician technician) {
        technicianRepository.save(technician);
        return "Technician saved successfully";
    }

    public List<Technician> findAll() {
        return technicianRepository.findAll();
    }

    public Optional<Technician> findById(UUID id) {
        return technicianRepository.findById(id);
    }

    public String update(UUID id, Technician updatedTechnician) {
        Optional<Technician> existingTechnician = technicianRepository.findById(id);

        if (existingTechnician.isEmpty()) {
            return "The technician with that id does not exist";
        }

        Technician technician = existingTechnician.get();

        technician.setFirstName(updatedTechnician.getFirstName());
        technician.setLastName(updatedTechnician.getLastName());
        technician.setPhone(updatedTechnician.getPhone());

        if (updatedTechnician.getWorkbench() != null
                && updatedTechnician.getWorkbench().getId() != null) {

            Optional<Workbench> workbench = workbenchRepository.findById(
                    updatedTechnician.getWorkbench().getId());

            if (workbench.isEmpty()) {
                return "The workbench with that id does not exist";
            }

            technician.setWorkbench(workbench.get());
        }

        if (updatedTechnician.getDeviceTypes() != null) {
            List<DeviceType> deviceTypes = new ArrayList<>();

            for (DeviceType deviceType : updatedTechnician.getDeviceTypes()) {
                Optional<DeviceType> existingDeviceType = deviceTypeRepository.findById(deviceType.getId());

                if (existingDeviceType.isEmpty()) {
                    return "The device type with that id does not exist";
                }

                deviceTypes.add(existingDeviceType.get());
            }

            technician.setDeviceTypes(deviceTypes);
        }

        technicianRepository.save(technician);

        return "Technician updated successfully";
    }

    public String delete(UUID id) {
        if (technicianRepository.findById(id).isEmpty()) {
            return "The technician with that id does not exist";
        }

        technicianRepository.deleteById(id);

        return "Technician deleted successfully";
    }

    public List<Technician> findByDeviceTypeName(String name) {
        return technicianRepository.findByDeviceTypeName(name);
    }
}