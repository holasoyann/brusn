package ru.brusnika.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.LegalStructure;
import ru.brusnika.NauJava.repository.LegalStructureRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LegalStructureService {
    private final LegalStructureRepository repository;

    @Autowired
    public LegalStructureService(LegalStructureRepository repository) {
        this.repository = repository;
    }

    public List<LegalStructure> findAll() {
        return repository.findAll();
    }

    public Optional<LegalStructure> findById(Integer id) {
        return repository.findById(id);
    }

    public LegalStructure save(LegalStructure LegalStructure) {
        return repository.save(LegalStructure);
    }

    public LegalStructure update(Integer id, LegalStructure updateLegalStructure) {
        return repository.findById(id)
                .map(existingLegalStructure -> {
                    existingLegalStructure.setEmployeeGroup(updateLegalStructure.getEmployeeGroup());
                    existingLegalStructure.setDepartment(updateLegalStructure.getDepartment());
                    existingLegalStructure.setSubdivision(updateLegalStructure.getSubdivision());
                    existingLegalStructure.setLocation(updateLegalStructure.getLocation());
                    existingLegalStructure.setLegalEntity(updateLegalStructure.getLegalEntity());
                    return repository.save(existingLegalStructure);
                })
                .orElseThrow(() -> new IllegalArgumentException("LegalStructure with id " + id + " not found"));
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
