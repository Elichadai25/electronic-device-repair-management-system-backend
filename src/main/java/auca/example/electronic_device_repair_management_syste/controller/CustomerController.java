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
import org.springframework.web.bind.annotation.RequestParam;
import auca.example.electronic_device_repair_management_syste.model.Customer;
import auca.example.electronic_device_repair_management_syste.service.CustomerService;
import auca.example.electronic_device_repair_management_syste.service.TechnicianService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final TechnicianService technicianService;

    public CustomerController(
            CustomerService customerService,
            TechnicianService technicianService) {
        this.customerService = customerService;
        this.technicianService = technicianService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer) {
        String result = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (customerService.findById(id).isPresent()) {
            return ResponseEntity.ok(customerService.findById(id).get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The customer with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Customer customer) {

        String result = customerService.update(id, customer);

        if (result.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String result = customerService.delete(id);

        if (result.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Customer>> findByLastName(
            @RequestParam String lastName) {

        return ResponseEntity.ok(
                customerService.findByLastName(lastName));
    }

    @GetMapping("/of-technician/{technicianId}")
    public ResponseEntity<?> findCustomersByTechnician(
            @PathVariable UUID technicianId) {

        if (technicianService.findById(technicianId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The technician with that id does not exist");
        }

        return ResponseEntity.ok(
                customerService.findCustomersByTechnicianId(technicianId));
    }
}
