package auca.example.electronic_device_repair_management_syste.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.example.electronic_device_repair_management_syste.model.DeviceType;
import auca.example.electronic_device_repair_management_syste.repository.DeviceTypeRepository;

@Service
public class DeviceTypeService {
    private final DeviceTypeRepository deviceTypeRepository;

    public DeviceTypeService(DeviceTypeRepository deviceTypeRepository) {
        this.deviceTypeRepository = deviceTypeRepository;
    }

    public String save(DeviceType deviceType) {
        deviceTypeRepository.save(deviceType);
        return "Device type saved successfully";
    }

    public List<DeviceType> findAll() {
        return deviceTypeRepository.findAll();
    }

    public Optional<DeviceType> findById(UUID id) {
        return deviceTypeRepository.findById(id);
    }

    public String update(UUID id, DeviceType updatedDeviceType) {
        Optional<DeviceType> existingDeviceType = deviceTypeRepository.findById(id);

        if (existingDeviceType.isEmpty()) {
            return "The device type with that id does not exist";
        }

        DeviceType deviceType = existingDeviceType.get();
        deviceType.setName(updatedDeviceType.getName());

        deviceTypeRepository.save(deviceType);

        return "Device type updated successfully";
    }

    public String delete(UUID id) {
        if (deviceTypeRepository.findById(id).isEmpty()) {
            return "The device type with that id does not exist";
        }

        deviceTypeRepository.deleteById(id);

        return "Device type deleted successfully";
    }

    public List<DeviceType> findUnusedDeviceTypes() {
        return deviceTypeRepository.findUnusedDeviceTypes();
    }
}
