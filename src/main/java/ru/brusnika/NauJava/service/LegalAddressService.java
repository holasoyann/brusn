package ru.brusnika.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.brusnika.NauJava.model.LegalAddress;
import ru.brusnika.NauJava.repository.LegalAddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LegalAddressService {
    private final LegalAddressRepository repository;
    private final CityService cityService;
    private final StreetService streetService;

    public LegalAddressService(LegalAddressRepository repository, CityService cityService, StreetService streetService) {
        this.repository = repository;
        this.cityService = cityService;
        this.streetService = streetService;
    }

    @Transactional
    public List<LegalAddress> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<LegalAddress> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public LegalAddress searchById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("LegalAddress with id " + id + " not found"));
    }

    @Transactional
    public LegalAddress save(LegalAddress legalAddress) {
        return repository.save(legalAddress);
    }

    @Transactional
    public LegalAddress update(Integer id, LegalAddress updatedAddress) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setCity(updatedAddress.getCity());
                    existing.setStreet(updatedAddress.getStreet());
                    existing.setBuildingNumber(updatedAddress.getBuildingNumber());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("LegalAddress with id " + id + " not found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("LegalAddress with id " + id + " not found");
        }
    }
}
