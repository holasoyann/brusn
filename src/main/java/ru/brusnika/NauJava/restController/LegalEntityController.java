package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.LegalEntity;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.LegalEntityService;

import java.util.List;

@RestController
@RequestMapping("/api/legal-entities")
public class LegalEntityController {
    @Autowired
    private final LegalEntityService service;

    @Autowired
    public LegalEntityController(LegalEntityService service) {
        this.service = service;
    }

    @GetMapping
    public List<LegalEntity> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public LegalEntity getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("LegalEntity with id " + id + " not found"));
    }

    @PostMapping
    public LegalEntity create(@RequestBody NameModel.LegalEntityModel entityNameInn) {
        var entity = new LegalEntity();
        entity.setName(entityNameInn.name);
        entity.setInn(entityNameInn.inn);
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public LegalEntity update(@PathVariable Integer id, NameModel.LegalEntityModel entityNameInn) {
        var entity = new LegalEntity();
        entity.setName(entityNameInn.name);
        entity.setInn(entityNameInn.inn);
        return service.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}

