package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.model.Subdivision;
import ru.brusnika.NauJava.service.LocationService;
import ru.brusnika.NauJava.service.SubdivisionService;

import java.util.List;

@RestController
@RequestMapping("/api/subdivisions")
public class SubdivisionController {
    @Autowired
    private final SubdivisionService service;
    @Autowired
    private LocationService locationService;

    @Autowired
    public SubdivisionController(SubdivisionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Subdivision> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Subdivision getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subdivision with id " + id + " not found"));
    }

    @PostMapping
    public Subdivision create(@RequestBody NameModel.SubdivisionModel subdivisionModel) {
        return service.save(CreateFromRequest(subdivisionModel));
    }

    @PutMapping("/{id}")
    public Subdivision update(@PathVariable Integer id, @RequestBody NameModel.SubdivisionModel subdivisionModel) {
        var subdivision = CreateFromRequest(subdivisionModel);
        return service.update(id, subdivision);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private Subdivision CreateFromRequest(NameModel.SubdivisionModel subdivisionModel) {
        var location = locationService.findById(subdivisionModel.locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location entity with id " + subdivisionModel.locationId + " not found"));

        var subdivision = new Subdivision();
        subdivision.setName(subdivisionModel.name);
        subdivision.setLocation(location);
        return subdivision;
    }
}
