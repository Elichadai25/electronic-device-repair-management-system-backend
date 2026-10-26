package auca.example.electronic_device_repair_management_syste.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.example.electronic_device_repair_management_syste.model.DeviceType;
import auca.example.electronic_device_repair_management_syste.service.DeviceTypeService;

@RestController
@RequestMapping("/api/device-types")
public class DeviceTypeController {
    private final DeviceTypeService deviceTypeService;

    public DeviceTypeController(DeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody DeviceType deviceType) {
        String message = deviceTypeService.save(deviceType);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeviceType>> findAll() {
        return ResponseEntity.ok(deviceTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (deviceTypeService.findById(id).isPresent()) {
            return ResponseEntity.ok(deviceTypeService.findById(id).get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The device type with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody DeviceType deviceType) {

        String result = deviceTypeService.update(id, deviceType);

        if (result.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = deviceTypeService.delete(id);

        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @GetMapping("/unused")
    public ResponseEntity<List<DeviceType>> findUnusedDeviceTypes() {
        return ResponseEntity.ok(deviceTypeService.findUnusedDeviceTypes());
    }
}
