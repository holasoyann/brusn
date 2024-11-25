package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.EmployeeGroup;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.DepartmentService;
import ru.brusnika.NauJava.service.EmployeeGroupService;

import java.util.List;

@RestController
@RequestMapping("/api/employee-groups")
public class EmployeeGroupController {
    @Autowired
    private final EmployeeGroupService service;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    public EmployeeGroupController(EmployeeGroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmployeeGroup> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeGroup getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("EmployeeGroup with id " + id + " not found"));
    }

    @PostMapping
    public EmployeeGroup create(@RequestBody NameModel.EmployeeGroupModel groupModel) {
        return service.save(CreateEmployeeGroup(groupModel));
    }

    @PutMapping("/{id}")
    public EmployeeGroup update(@PathVariable Integer id, @RequestBody NameModel.EmployeeGroupModel groupModel) {
        return service.update(id, CreateEmployeeGroup(groupModel));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private EmployeeGroup CreateEmployeeGroup(NameModel.EmployeeGroupModel groupModel) {
        var department = departmentService.findById(groupModel.departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department with id " + groupModel.departmentId + " not found"));

        var group = new EmployeeGroup();
        group.setDepartment(department);
        group.setName(groupModel.name);
        return group;
    }
}

