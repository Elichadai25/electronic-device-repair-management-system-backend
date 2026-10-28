package auca.example.electronic_device_repair_management_syste.service;

import java.util.UUID;
import java.util.List;
import org.springframework.stereotype.Service;

import auca.example.electronic_device_repair_management_syste.model.SparePart;
import auca.example.electronic_device_repair_management_syste.repository.SparePartRepository;

@Service
public class SparePartService {
    private final SparePartRepository sparePartRepository;

    public SparePartService(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    public SparePart saveSparePart(SparePart sparePart) {
        return sparePartRepository.save(sparePart);
    }

    public List<SparePart> getAllSpareParts() {
        return sparePartRepository.findAll();
    }

    public SparePart getSparePartById(UUID id) {
        return sparePartRepository.findById(id).orElse(null);
    }

    public void deleteSparePart(UUID id) {
        sparePartRepository.deleteById(id);
    }
}
