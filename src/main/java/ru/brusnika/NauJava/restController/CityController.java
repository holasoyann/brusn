package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.City;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.CityService;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    @Autowired
    private final CityService service;

    @Autowired
    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    public List<City> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public City getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City with id " + id + " not found"));
    }

    @PostMapping
    public City create(@RequestBody NameModel cityName) {
        var city = new City();
        city.setName(cityName.name);
        return service.save(city);
    }

    @PutMapping("/{id}")
    public City update(@PathVariable Integer id, @RequestBody NameModel cityName) {
        var city = new City();
        city.setName(cityName.name);
        return service.update(id, city);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
