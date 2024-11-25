package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.Founder;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.FounderService;
import ru.brusnika.NauJava.service.LegalEntityService;

import java.util.List;

@RestController
@RequestMapping("/api/founders")
public class FounderController {
    @Autowired
    private final FounderService service;

    @Autowired
    private final LegalEntityService legalEntityService;

    @Autowired
    public FounderController(FounderService service, LegalEntityService legalEntityService) {
        this.service = service;
        this.legalEntityService = legalEntityService;
    }

    @GetMapping
    public List<Founder> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Founder getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Founder with id " + id + " not found"));
    }

    @PostMapping
    public Founder create(@RequestBody NameModel.FounderModel founderModel) {
        return service.save(CreateFromRequest(founderModel));
    }

    @PutMapping("/{id}")
    public Founder update(@PathVariable Integer id, @RequestBody NameModel.FounderModel founderModel) {
        return service.update(id, CreateFromRequest(founderModel));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private Founder CreateFromRequest(NameModel.FounderModel founderModel) {
        var legalEntity = legalEntityService.findById(founderModel.legalEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Legal entity with id " + founderModel.legalEntityId + " not found"));

        var founder = new Founder();
        founder.setFounder(founderModel.name);
        founder.setLegalEntity(legalEntity);
        return founder;
    }
}
