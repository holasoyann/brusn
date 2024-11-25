package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.Department;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.DepartmentService;
import ru.brusnika.NauJava.service.SubdivisionService;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private final DepartmentService service;

    @Autowired
    private SubdivisionService subdivisionService;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Department> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Department getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department with id " + id + " not found"));
    }

    @PostMapping
    public Department create(@RequestBody NameModel.DepartmentModel departmentModel) {
        return service.save(CreateDepartment(departmentModel));
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable Integer id, @RequestBody NameModel.DepartmentModel departmentModel) {
        return service.update(id, CreateDepartment(departmentModel));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private Department CreateDepartment(NameModel.DepartmentModel departmentModel) {
        var subdivision = subdivisionService.findById(departmentModel.subdivisionId)
                .orElseThrow(() -> new IllegalArgumentException("Subdivision with id " + departmentModel.subdivisionId + " not found"));

        var department = new Department();
        department.setName(departmentModel.name);
        department.setSubdivision(subdivision);
        return department;
    }
}

