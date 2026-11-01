package auca.example.electronic_device_repair_management_syste.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.example.electronic_device_repair_management_syste.model.SparePart;
import auca.example.electronic_device_repair_management_syste.service.SparePartService;

@RestController
@RequestMapping("/api/spare-parts")
public class SparePartController {
    private final SparePartService sparePartService;

    public SparePartController(SparePartService sparePartService) {
        this.sparePartService = sparePartService;
    }

    @PostMapping("/save")
    public ResponseEntity<SparePart> saveSparePart(
            @RequestBody SparePart sparePart,
            @RequestParam UUID repairOrderId) {

        SparePart saved = sparePartService.saveSparePart(sparePart, repairOrderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<SparePart> getAllSpareParts() {
        return sparePartService.getAllSpareParts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePart> getSparePartById(
            @PathVariable UUID id) {
        SparePart sparePart = sparePartService.getSparePartById(id);

        if (sparePart == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(sparePart);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSparePart(@PathVariable UUID id) {
        sparePartService.deleteSparePart(id);
        return ResponseEntity.ok("Spare part deleted successfully");
    }

    @GetMapping("/customer/{customerCode}")
    public List<SparePart> getSparePartsByCustomerCode(
            @PathVariable String customerCode) {
        return sparePartService.getSparePartsByCustomerCode(customerCode);
    }
}
