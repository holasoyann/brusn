package ru.brusnika.NauJava.restController;

import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.LegalAddress;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.CityService;
import ru.brusnika.NauJava.service.LegalAddressService;
import ru.brusnika.NauJava.service.StreetService;

import java.util.List;

@RestController
@RequestMapping("/api/legal-addresses")
public class LegalAddressController {
    private final LegalAddressService service;
    private final CityService cityService;
    private final StreetService streetService;

    public LegalAddressController(LegalAddressService service, CityService cityService, StreetService streetService) {
        this.service = service;
        this.cityService = cityService;
        this.streetService = streetService;
    }

    @GetMapping
    public List<LegalAddress> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public LegalAddress getById(@PathVariable Integer id) {
        return service.searchById(id);
    }

    @PostMapping
    public LegalAddress create(@RequestBody NameModel.LegalAddressModel addressModel) {
        return service.save(CreateFromRequest(addressModel));
    }

    @PutMapping("/{id}")
    public LegalAddress update(@PathVariable Integer id, @RequestBody NameModel.LegalAddressModel addressModel) {
        var address = CreateFromRequest(addressModel);
        return service.update(id, address);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private LegalAddress CreateFromRequest(NameModel.LegalAddressModel addressModel) {
        var city = cityService.findById(addressModel.cityId)
                .orElseThrow(() -> new IllegalArgumentException("City with id " + addressModel.cityId + " not found"));

        var street = streetService.findById(addressModel.streetId)
                .orElseThrow(() -> new IllegalArgumentException("Street with id " + addressModel.streetId + " not found"));

        var address = new LegalAddress();
        address.setCity(city);
        address.setStreet(street);
        address.setBuildingNumber(addressModel.buildingNumber);
        return address;
    }
}
