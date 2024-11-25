package ru.brusnika.NauJava.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.brusnika.NauJava.helper.DataMigrationService;

@RestController
@RequestMapping("/api/migration")
public class MigrationController {
    @Autowired
    private DataMigrationService dataMigrationService;

    @PostMapping
    public void create(String path) throws Exception {
        dataMigrationService.migrateData(path);
    }
}
