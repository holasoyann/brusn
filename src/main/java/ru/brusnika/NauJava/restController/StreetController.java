package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.model.Street;
import ru.brusnika.NauJava.service.StreetService;

import java.util.List;

@RestController
@RequestMapping("/api/streets")
public class StreetController {
    @Autowired
    private final StreetService service;

    @Autowired
    public StreetController(StreetService service) {
        this.service = service;
    }

    @GetMapping
    public List<Street> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Street getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Street with id " + id + " not found"));
    }

    @PostMapping
    public Street create(@RequestBody NameModel streetName) {
        return service.save(CreateFromRequest(streetName));
    }

    @PutMapping("/{id}")
    public Street update(@PathVariable Integer id, @RequestBody NameModel streetName) {
        var street = CreateFromRequest(streetName);
        return service.update(id, street);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private Street CreateFromRequest(NameModel streetName) {
        var street = new Street();
        street.setName(streetName.name);
        return street;
    }
}

