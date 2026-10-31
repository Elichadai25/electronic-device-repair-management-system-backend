package auca.example.electronic_device_repair_management_syste.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import auca.example.electronic_device_repair_management_syste.model.RepairStatus;
import auca.example.electronic_device_repair_management_syste.repository.CustomerRepository;
import auca.example.electronic_device_repair_management_syste.repository.RepairOrderRepository;
import auca.example.electronic_device_repair_management_syste.repository.SparePartRepository;
import auca.example.electronic_device_repair_management_syste.repository.TechnicianRepository;
import auca.example.electronic_device_repair_management_syste.repository.WorkbenchRepository;

@Service
public class DashboardService {
    private final CustomerRepository customerRepository;
    private final TechnicianRepository technicianRepository;
    private final WorkbenchRepository workbenchRepository;
    private final RepairOrderRepository repairOrderRepository;
    private final SparePartRepository sparePartRepository;

    public DashboardService(
            CustomerRepository customerRepository,
            TechnicianRepository technicianRepository,
            WorkbenchRepository workbenchRepository,
            RepairOrderRepository repairOrderRepository,
            SparePartRepository sparePartRepository) {
        this.customerRepository = customerRepository;
        this.technicianRepository = technicianRepository;
        this.workbenchRepository = workbenchRepository;
        this.repairOrderRepository = repairOrderRepository;
        this.sparePartRepository = sparePartRepository;
    }

    public Map<String, Long> getDashboardSummary() {

        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("customers", customerRepository.count());
        summary.put("technicians", technicianRepository.count());
        summary.put("freeWorkbenches",
                (long) workbenchRepository.findFreeWorkbenches().size());
        summary.put("repairsToday",

                repairOrderRepository.countByReceivedDate(LocalDate.now()));
        summary.put("logged",
                repairOrderRepository.countByStatus(RepairStatus.LOGGED));
        summary.put("spareParts", sparePartRepository.count());

        return summary;
    }
}