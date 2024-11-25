package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.Gender;
import ru.brusnika.NauJava.repository.GenderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenderService {
    private final GenderRepository repository;

    public GenderService(GenderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Gender> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Gender> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public Gender save(Gender gender) {
        return repository.save(gender);
    }

    @Transactional
    public Gender update(Integer id, Gender updatedGender) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedGender.getName());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Gender with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Gender with id " + id + " not found");
        }
    }
}
