package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.LegalEntity;
import ru.brusnika.NauJava.repository.LegalEntityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LegalEntityService {
    private final LegalEntityRepository repository;

    public LegalEntityService(LegalEntityRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<LegalEntity> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<LegalEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public LegalEntity save(LegalEntity legalEntity) {
        return repository.save(legalEntity);
    }

    @Transactional
    public LegalEntity update(Integer id, LegalEntity updatedEntity) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedEntity.getName());
                    existing.setInn(updatedEntity.getInn());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("LegalEntity with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("LegalEntity with id " + id + " not found");
        }
    }
}
