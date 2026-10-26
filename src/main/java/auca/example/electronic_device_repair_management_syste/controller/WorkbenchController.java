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

import auca.example.electronic_device_repair_management_syste.model.Workbench;
import auca.example.electronic_device_repair_management_syste.service.WorkbenchService;

@RestController
@RequestMapping("/api/workbenches")
public class WorkbenchController {
    private final WorkbenchService workbenchService;

    public WorkbenchController(WorkbenchService workbenchService) {
        this.workbenchService = workbenchService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Workbench workbench) {
        String message = workbenchService.save(workbench);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Workbench>> findAll() {
        return ResponseEntity.ok(workbenchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (workbenchService.findById(id).isPresent()) {
            return ResponseEntity.ok(workbenchService.findById(id).get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The workbench with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody Workbench workbench) {

        String message = workbenchService.update(id, workbench);

        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = workbenchService.delete(id);

        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @GetMapping("/free")
    public ResponseEntity<List<Workbench>> findFreeWorkbenches() {
        return ResponseEntity.ok(workbenchService.findFreeWorkbenches());
    }
}
