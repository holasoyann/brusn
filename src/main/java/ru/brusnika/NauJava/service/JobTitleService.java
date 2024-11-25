package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.JobTitle;
import ru.brusnika.NauJava.repository.JobTitleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JobTitleService {
    private final JobTitleRepository repository;

    public JobTitleService(JobTitleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<JobTitle> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<JobTitle> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public JobTitle save(JobTitle jobTitle) {
        return repository.save(jobTitle);
    }

    @Transactional
    public JobTitle update(Integer id, JobTitle updatedJobTitle) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedJobTitle.getName());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("JobTitle with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("JobTitle with id " + id + " not found");
        }
    }
}
