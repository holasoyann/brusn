package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.Location;
import ru.brusnika.NauJava.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Location> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Location> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public Location save(Location location) {
        return repository.save(location);
    }

    @Transactional
    public Location update(Integer id, Location updatedLocation) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedLocation.getName());
                    existing.setLegalEntity(updatedLocation.getLegalEntity());
                    existing.setLegalAddress(updatedLocation.getLegalAddress());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Location with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Location with id " + id + " not found");
        }
    }
}
