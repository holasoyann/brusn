package ru.brusnika.NauJava.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ru.brusnika.NauJava.model.Employee;
import ru.brusnika.NauJava.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Employee> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Transactional
    public Employee update(Integer id, Employee updatedEmployee) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedEmployee.getName());
                    existing.setSurname(updatedEmployee.getSurname());
                    existing.setPatronymic(updatedEmployee.getPatronymic());
                    existing.setGender(updatedEmployee.getGender());
                    existing.setJobTitle(updatedEmployee.getJobTitle());
                    existing.setStartedJob(updatedEmployee.getStartedJob());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Employee with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Employee with id " + id + " not found");
        }
    }
}
