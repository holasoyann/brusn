package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.Department;
import ru.brusnika.NauJava.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Department> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Department> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public Department save(Department department) {
        return repository.save(department);
    }

    @Transactional
    public Department update(Integer id, Department updatedDepartment) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedDepartment.getName());
                    existing.setSubdivision(updatedDepartment.getSubdivision());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Department with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Department with id " + id + " not found");
        }
    }
}
