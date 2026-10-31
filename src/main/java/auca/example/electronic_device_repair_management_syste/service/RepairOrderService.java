package auca.example.electronic_device_repair_management_syste.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import auca.example.electronic_device_repair_management_syste.model.RepairOrder;
import auca.example.electronic_device_repair_management_syste.model.RepairStatus;
import auca.example.electronic_device_repair_management_syste.repository.RepairOrderRepository;

@Service
public class RepairOrderService {
    private final RepairOrderRepository repairOrderRepository;

    public RepairOrderService(RepairOrderRepository repairOrderRepository) {
        this.repairOrderRepository = repairOrderRepository;
    }

    public String save(RepairOrder repairOrder) {

        LocalDate today = LocalDate.now();

        if (repairOrder.getReceivedDate() == null
                || repairOrder.getReceivedDate().isBefore(today)) {
            return "Repair order date cannot be in the past";
        }

        if (repairOrder.getTechnician() == null
                || repairOrder.getTechnician().getId() == null) {
            return "A technician must be selected";
        }

        boolean technicianBusy = repairOrderRepository
                .existsByTechnician_IdAndReceivedDateAndStatusNot(
                        repairOrder.getTechnician().getId(),
                        repairOrder.getReceivedDate(),
                        RepairStatus.DECLINED);

        if (technicianBusy) {
            return "Technician already has a repair order on that date";
        }

        repairOrder.setStatus(RepairStatus.LOGGED);
        repairOrderRepository.save(repairOrder);

        return "Repair order saved successfully";
    }

    public Optional<RepairOrder> findById(UUID id) {
        return repairOrderRepository.findById(id);
    }

    public String update(UUID id, RepairOrder updatedRepairOrder) {
        Optional<RepairOrder> existingRepairOrder = repairOrderRepository.findById(id);

        if (existingRepairOrder.isEmpty()) {
            return "The repair order with that id does not exist";
        }

        RepairOrder repairOrder = existingRepairOrder.get();

        if (updatedRepairOrder.getReceivedDate() != null) {
            repairOrder.setReceivedDate(updatedRepairOrder.getReceivedDate());
        }

        if (updatedRepairOrder.getDeviceModel() != null) {
            repairOrder.setDeviceModel(updatedRepairOrder.getDeviceModel());
        }

        if (updatedRepairOrder.getFaultDescription() != null) {
            repairOrder.setFaultDescription(updatedRepairOrder.getFaultDescription());
        }

        if (updatedRepairOrder.getCustomer() != null) {
            repairOrder.setCustomer(updatedRepairOrder.getCustomer());
        }

        if (updatedRepairOrder.getTechnician() != null) {
            repairOrder.setTechnician(updatedRepairOrder.getTechnician());
        }

        repairOrderRepository.save(repairOrder);

        return "Repair order updated successfully";
    }

    public String updateStatus(UUID id, RepairStatus newStatus) {
        Optional<RepairOrder> existingRepairOrder = repairOrderRepository.findById(id);

        if (existingRepairOrder.isEmpty()) {
            return "The repair order with that id does not exist";
        }

        RepairOrder repairOrder = existingRepairOrder.get();
        RepairStatus currentStatus = repairOrder.getStatus();

        boolean validTransition = (currentStatus == RepairStatus.LOGGED
                && (newStatus == RepairStatus.IN_REPAIR
                        || newStatus == RepairStatus.DECLINED))
                || (currentStatus == RepairStatus.IN_REPAIR
                        && (newStatus == RepairStatus.FIXED
                                || newStatus == RepairStatus.DECLINED));

        if (!validTransition) {
            return "Cannot change status from "
                    + currentStatus + " to " + newStatus;
        }

        repairOrder.setStatus(newStatus);
        repairOrderRepository.save(repairOrder);

        return "Repair order status updated successfully";
    }

    public String delete(UUID id) {
        if (repairOrderRepository.findById(id).isEmpty()) {
            return "The repair order with that id does not exist";
        }

        repairOrderRepository.deleteById(id);

        return "Repair order deleted successfully";
    }

    public List<RepairOrder> findByStatus(RepairStatus status) {
        return repairOrderRepository.findByStatusOrderByReceivedDateAsc(status);
    }

    public List<RepairOrder> findBetweenDates(
            LocalDate start, LocalDate end) {
        return repairOrderRepository
                .findByReceivedDateBetween(start, end);
    }

    public List<Object[]> countRepairOrdersByStatus() {
        return repairOrderRepository.countRepairOrdersByStatus();
    }

    public String cancelRepairOrdersByTechnicianAndDate(
            UUID technicianId, LocalDate date) {

        int updatedCount = repairOrderRepository
                .cancelRepairOrdersByTechnicianAndDate(technicianId, date);

        return updatedCount + " repair orders cancelled";
    }

    public List<RepairOrder> findAll() {
        return repairOrderRepository.findAll();
    }

    public Page<RepairOrder> findAll(Pageable pageable) {
        return repairOrderRepository.findAll(pageable);
    }
}
