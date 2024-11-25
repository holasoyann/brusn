package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.LegalAddress;
import ru.brusnika.NauJava.model.Location;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.LegalAddressService;
import ru.brusnika.NauJava.service.LegalEntityService;
import ru.brusnika.NauJava.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    private final LocationService service;
    @Autowired
    private LegalEntityService legalEntityService;
    @Autowired
    private LegalAddressService legalAddressService;

    @Autowired
    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Location> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Location getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location with id " + id + " not found"));
    }

    @PostMapping
    public Location create(@RequestBody NameModel.LocationModel locationModel) {
        return service.save(CreateFromRequest(locationModel));
    }

    @PutMapping("/{id}")
    public Location update(@PathVariable Integer id, @RequestBody NameModel.LocationModel locationModel) {
        var location = CreateFromRequest(locationModel);
        return service.update(id, location);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private Location CreateFromRequest(NameModel.LocationModel locationModel) {
        var legalEntity = legalEntityService.findById(locationModel.legalEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Legal entity with id " + locationModel.legalEntityId + " not found"));

        var legalAddress = legalAddressService.findById(locationModel.legalAddressId)
                .orElseThrow(() -> new IllegalArgumentException("Legal address with id " + locationModel.legalAddressId + " not found"));

        var location = new Location();
        location.setName(locationModel.name);
        location.setLegalAddress(legalAddress);
        location.setLegalEntity(legalEntity);
        return location;
    }
}
