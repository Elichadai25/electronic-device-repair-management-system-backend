package auca.example.electronic_device_repair_management_syste.service;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import auca.example.electronic_device_repair_management_syste.model.RepairOrder;
import auca.example.electronic_device_repair_management_syste.model.RepairStatus;
import auca.example.electronic_device_repair_management_syste.model.SparePart;
import auca.example.electronic_device_repair_management_syste.repository.RepairOrderRepository;
import auca.example.electronic_device_repair_management_syste.repository.SparePartRepository;

@Service
public class SparePartService {
    private final SparePartRepository sparePartRepository;
    private final RepairOrderRepository repairOrderRepository;

    public SparePartService(SparePartRepository sparePartRepository, RepairOrderRepository repairOrderRepository) {
        this.sparePartRepository = sparePartRepository;
        this.repairOrderRepository = repairOrderRepository;
    }

    public SparePart saveSparePart(SparePart sparePart, UUID repairOrderId) {
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "The repair order with that id does not exist"));

        if (repairOrder.getStatus() != RepairStatus.FIXED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Spare parts are only for FIXED repair orders");
        }

        sparePart.setRepairOrder(repairOrder);
        sparePart.setFittedDate(LocalDate.now());

        return sparePartRepository.save(sparePart);
    }

    public List<SparePart> getAllSpareParts() {
        return sparePartRepository.findAll();
    }

    public SparePart getSparePartById(UUID id) {
        return sparePartRepository.findById(id).orElse(null);
    }

    public void deleteSparePart(UUID id) {
        SparePart sparePart = sparePartRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "The spare part with that id does not exist"));

        sparePartRepository.delete(sparePart);
    }

    public List<SparePart> getSparePartsByCustomerCode(String customerCode) {
        return sparePartRepository.findByCustomerCode(customerCode);
    }
}
