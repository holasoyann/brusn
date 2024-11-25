package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.Founder;
import ru.brusnika.NauJava.repository.FounderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FounderService {
    private final FounderRepository repository;

    public FounderService(FounderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Founder> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Founder> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public Founder save(Founder founder) {
        return repository.save(founder);
    }

    @Transactional
    public Founder update(Integer id, Founder updatedFounder) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setFounder(updatedFounder.getFounder());
                    existing.setLegalEntity(updatedFounder.getLegalEntity());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Founder with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Founder with id " + id + " not found");
        }
    }
}
