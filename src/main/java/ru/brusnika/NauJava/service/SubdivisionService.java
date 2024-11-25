package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.Subdivision;
import ru.brusnika.NauJava.repository.SubdivisionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubdivisionService {
    private final SubdivisionRepository repository;

    public SubdivisionService(SubdivisionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Subdivision> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Subdivision> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public Subdivision save(Subdivision subdivision) {
        return repository.save(subdivision);
    }

    @Transactional
    public Subdivision update(Integer id, Subdivision updatedSubdivision) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedSubdivision.getName());
                    existing.setLocation(updatedSubdivision.getLocation());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Subdivision with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Subdivision with id " + id + " not found");
        }
    }
}
