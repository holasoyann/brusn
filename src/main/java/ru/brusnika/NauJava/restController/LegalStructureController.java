package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.LegalStructure;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/legal-structures")
public class LegalStructureController {
    private final LegalStructureService service;
    private final LegalEntityService legalEntityService;
    private final LocationService locationService;
    private final EmployeeGroupService employeeGroupService;
    private final SubdivisionService subdivisionService;
    private final DepartmentService departmentService;

    @Autowired
    public LegalStructureController(LegalStructureService service, LegalEntityService legalEntityService, LocationService locationService, EmployeeGroupService employeeGroupService, SubdivisionService subdivisionService, DepartmentService departmentService) {
        this.service = service;
        this.legalEntityService = legalEntityService;
        this.locationService = locationService;
        this.employeeGroupService = employeeGroupService;
        this.subdivisionService = subdivisionService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<LegalStructure> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public LegalStructure getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Legal structure with id " + id + " not found"));
    }

    @PostMapping
    public LegalStructure create(@RequestBody NameModel.LegalStructureModel legalStructureModel) {
        var legalStructure = CreateFromRequest(legalStructureModel);
        return service.save(legalStructure);
    }

    @PutMapping("/{id}")
    public LegalStructure update(@PathVariable Integer id, @RequestBody NameModel.LegalStructureModel legalStructureModel) {
        var legalStructure = CreateFromRequest(legalStructureModel);
        return service.update(id, legalStructure);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    private LegalStructure CreateFromRequest(NameModel.LegalStructureModel legalStructureModel)
    {
        var legalEntity = legalEntityService.findById(legalStructureModel.legalEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Legal entity with id " + legalStructureModel.legalEntityId + " not found"));
        var subdivision = subdivisionService.findById(legalStructureModel.subdivisionId)
                .orElseThrow(() -> new IllegalArgumentException("Subdivision with id " + legalStructureModel.subdivisionId + " not found"));
        var location = locationService.findById(legalStructureModel.locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location entity with id " + legalStructureModel.locationId + " not found"));
        var group = employeeGroupService.findById(legalStructureModel.employeeGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Employee group with id " + legalStructureModel.employeeGroupId + " not found"));
        var department = departmentService.findById(legalStructureModel.departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department with id " + legalStructureModel.departmentId + " not found"));

        var legalStructure = new LegalStructure();
        legalStructure.setLegalEntity(legalEntity);
        legalStructure.setSubdivision(subdivision);
        legalStructure.setDepartment(department);
        legalStructure.setEmployeeGroup(group);
        legalStructure.setLocation(location);
        return legalStructure;
    }
}
