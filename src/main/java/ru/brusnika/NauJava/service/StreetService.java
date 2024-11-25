package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.Street;
import ru.brusnika.NauJava.repository.StreetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StreetService {
    private final StreetRepository repository;

    public StreetService(StreetRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Street> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Street> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public Street save(Street street) {
        return repository.save(street);
    }

    @Transactional
    public Street update(Integer id, Street updatedStreet) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedStreet.getName());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Street with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Street with id " + id + " not found");
        }
    }
}
