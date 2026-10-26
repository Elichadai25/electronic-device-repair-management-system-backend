package auca.example.electronic_device_repair_management_syste.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.example.electronic_device_repair_management_syste.model.Workbench;
import auca.example.electronic_device_repair_management_syste.repository.WorkbenchRepository;

@Service
public class WorkbenchService {

    private final WorkbenchRepository workbenchRepository;

    public WorkbenchService(WorkbenchRepository workbenchRepository) {
        this.workbenchRepository = workbenchRepository;
    }

    public String save(Workbench workbench) {
        workbenchRepository.save(workbench);
        return "Workbench saved successfully";
    }

    public List<Workbench> findAll() {
        return workbenchRepository.findAll();
    }

    public Optional<Workbench> findById(UUID id) {
        return workbenchRepository.findById(id);
    }

    public String update(UUID id, Workbench updatedWorkbench) {
        Optional<Workbench> existingWorkbench = workbenchRepository.findById(id);

        if (existingWorkbench.isEmpty()) {
            return "The workbench with that id does not exist";
        }

        Workbench workbench = existingWorkbench.get();
        workbench.setBenchNumber(updatedWorkbench.getBenchNumber());
        workbench.setName(updatedWorkbench.getName());

        workbenchRepository.save(workbench);

        return "Workbench updated successfully";
    }

    public String delete(UUID id) {
        if (workbenchRepository.findById(id).isEmpty()) {
            return "The workbench with that id does not exist";
        }

        workbenchRepository.deleteById(id);

        return "Workbench deleted successfully";
    }

    public List<Workbench> findFreeWorkbenches() {
        return workbenchRepository.findFreeWorkbenches();
    }
}
