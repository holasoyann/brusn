package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.model.JobTitle;
import ru.brusnika.NauJava.model.NameModel;
import ru.brusnika.NauJava.service.JobTitleService;

import java.util.List;

@RestController
@RequestMapping("/api/job-titles")
public class JobTitleController {
    @Autowired
    private final JobTitleService service;

    @Autowired
    public JobTitleController(JobTitleService service) {
        this.service = service;
    }

    @GetMapping
    public List<JobTitle> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public JobTitle getById(@PathVariable Integer id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JobTitle with id " + id + " not found"));
    }

    @PostMapping
    public JobTitle create(@RequestBody NameModel jobTitleName) {
        var jobTitle = new JobTitle();
        jobTitle.setName(jobTitleName.name);
        return service.save(jobTitle);
    }

    @PutMapping("/{id}")
    public JobTitle update(@PathVariable Integer id, NameModel jobTitleName) {
        var jobTitle = new JobTitle();
        jobTitle.setName(jobTitleName.name);
        return service.update(id, jobTitle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
