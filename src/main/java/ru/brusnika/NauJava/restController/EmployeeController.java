package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.Employee;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private final EmployeeService service;
    @Autowired
    private GenderService genderService;
    @Autowired
    private EmployeeGroupService employeeGroupService;
    @Autowired
    private JobTitleService jobTitleService;
    @Autowired
    private LegalStructureService legalStructureService;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee with id " + id + " not found"));
    }

    @PostMapping
    public Employee create(@RequestBody NameModel.EmployeeModel employeeModel) {
        return service.save(CreateEmployee(employeeModel));
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody NameModel.EmployeeModel employeeModel) {
        return service.update(id, CreateEmployee(employeeModel));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private Employee CreateEmployee(NameModel.EmployeeModel employeeModel) {
        var gender = genderService.findById(employeeModel.genderId)
                .orElseThrow(() -> new IllegalArgumentException("Gender with id " + employeeModel.genderId + " not found"));
        var jobTitle = jobTitleService.findById(employeeModel.jobTitleId)
                .orElseThrow(() -> new IllegalArgumentException("Job title with id " + employeeModel.jobTitleId + " not found"));
        var legalStructure = legalStructureService.findById(employeeModel.legalStructureId)
                .orElseThrow(() -> new IllegalArgumentException("Legal structure with id " + employeeModel.legalStructureId + " not found"));

        var employee = new Employee();
        employee.setSurname(employeeModel.surname);
        employee.setName(employeeModel.name);
        employee.setPatronymic(employeeModel.patronymic);
        employee.setBirthday(employeeModel.birthday);
        employee.setGender(gender);
        employee.setJobTitle(jobTitle);
        employee.setCitizenship(employeeModel.citizenship);
        employee.setSalary(employeeModel.salary);
        employee.setPhotoLink(employeeModel.photoLink);
        employee.setStartedJob(employeeModel.startedJob);
        employee.setEducationCountry(employeeModel.educationCountry);
        employee.setLegalStructure(legalStructure);
        return employee;
    }
}
