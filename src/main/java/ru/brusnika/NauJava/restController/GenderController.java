package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.Gender;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.GenderService;

import java.util.List;

@RestController
@RequestMapping("/api/genders")
public class GenderController {
    @Autowired
    private final GenderService service;

    @Autowired
    public GenderController(GenderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Gender> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Gender getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gender with id " + id + " not found"));
    }

    @PostMapping
    public Gender create(@RequestBody NameModel genderName) {
        var gender = new Gender();
        gender.setName(genderName.name);
        return service.save(gender);
    }

    @PutMapping("/{id}")
    public Gender update(@PathVariable Integer id, @RequestBody NameModel genderName) {
        var gender = new Gender();
        gender.setName(genderName.name);
        return service.update(id, gender);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
