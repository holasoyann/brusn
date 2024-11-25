package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.EmployeeGroup;
import ru.brusnika.NauJava.repository.EmployeeGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeGroupService {
    private final EmployeeGroupRepository repository;

    public EmployeeGroupService(EmployeeGroupRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<EmployeeGroup> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<EmployeeGroup> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public EmployeeGroup save(EmployeeGroup group) {
        return repository.save(group);
    }

    @Transactional
    public EmployeeGroup update(Integer id, EmployeeGroup updatedGroup) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedGroup.getName());
                    existing.setDepartment(updatedGroup.getDepartment());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("EmployeeGroup with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("EmployeeGroup with id " + id + " not found");
        }
    }
}
