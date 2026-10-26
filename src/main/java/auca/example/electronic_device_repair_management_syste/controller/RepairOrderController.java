package auca.example.electronic_device_repair_management_syste.controller;

import org.springframework.web.bind.annotation.RequestParam;
import auca.example.electronic_device_repair_management_syste.model.RepairStatus;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.example.electronic_device_repair_management_syste.model.RepairOrder;
import auca.example.electronic_device_repair_management_syste.service.RepairOrderService;

@RestController
@RequestMapping("/api/repair-orders")
public class RepairOrderController {
    private final RepairOrderService repairOrderService;

    public RepairOrderController(RepairOrderService repairOrderService) {
        this.repairOrderService = repairOrderService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody RepairOrder repairOrder) {

        String result = repairOrderService.save(repairOrder);

        if (result.equals("Repair order date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        if (result.equals("Technician already has a repair order on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        if (result.equals("A technician must be selected")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RepairOrder>> findAll() {
        return ResponseEntity.ok(repairOrderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (repairOrderService.findById(id).isPresent()) {
            return ResponseEntity.ok(repairOrderService.findById(id).get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The repair order with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody RepairOrder repairOrder) {

        String result = repairOrderService.update(id, repairOrder);

        if (result.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable UUID id,
            @RequestParam RepairStatus status) {

        String result = repairOrderService.updateStatus(id, status);

        if (result.equals("The repair order with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        if (result.startsWith("Cannot change status from")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String result = repairOrderService.delete(id);

        if (result.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<RepairOrder>> findByStatus(
            @RequestParam RepairStatus status) {

        return ResponseEntity.ok(
                repairOrderService.findByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<RepairOrder>> findBetweenDates(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(
                repairOrderService.findBetweenDates(startDate, endDate));
    }
}