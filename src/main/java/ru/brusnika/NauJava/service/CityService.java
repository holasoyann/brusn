package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.City;
import ru.brusnika.NauJava.repository.CityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<City> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<City> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public City save(City city) {
        return repository.save(city);
    }

    @Transactional
    public City update(Integer id, City updatedCity) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedCity.getName());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("City with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("City with id " + id + " not found");
        }
    }
}
