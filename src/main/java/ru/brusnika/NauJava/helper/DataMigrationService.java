package ru.brusnika.NauJava.helper;

import org.apache.poi.ss.usermodel.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.brusnika.NauJava.model.*;
import ru.brusnika.NauJava.service.*;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataMigrationService {

    private final LegalEntityService legalEntityService;
    private final LocationService locationService;
    private final SubdivisionService subdivisionService;
    private final DepartmentService departmentService;
    private final EmployeeGroupService employeeGroupService;
    private final JobTitleService jobTitleService;
    private final EmployeeService employeeService;
    private final LegalStructureService legalStructureService;

    public DataMigrationService(LegalEntityService legalEntityService, LocationService locationService, SubdivisionService subdivisionService, DepartmentService departmentService, EmployeeGroupService employeeGroupService, JobTitleService jobTitleService, EmployeeService employeeService, LegalStructureService legalStructureService) {
        this.legalEntityService = legalEntityService;
        this.locationService = locationService;
        this.subdivisionService = subdivisionService;
        this.departmentService = departmentService;
        this.employeeGroupService = employeeGroupService;
        this.jobTitleService = jobTitleService;
        this.employeeService = employeeService;
        this.legalStructureService = legalStructureService;
    }

    @Transactional
    public void migrateData(String excelFilePath) throws Exception {
        // Словарь для хранения объектов и их ID
        Map<Object, Integer> idMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Первая страница Excel
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Пропустить заголовок

                // Считать значения из столбцов
                String legalEntityName = cleanValueOrNull(getCellValueAsString(row.getCell(1)), "ЮЛ");
                String locationName = cleanValueOrNull(getCellValueAsString(row.getCell(2)), "Локация");
                String subdivisionName = cleanValueOrNull(getCellValueAsString(row.getCell(3)), "Подразделение");
                String departmentName = cleanValueOrNull(getCellValueAsString(row.getCell(4)), "Отдел");
                String groupName = cleanValueOrNull(getCellValueAsString(row.getCell(5)), "Группа");
                String jobTitleName = cleanValueOrNull(getCellValueAsString(row.getCell(6)), "Должность");
                String fullName = getCellValueAsString(row.getCell(7));

                // Разделить ФИО
                String[] nameParts = fullName != null ? fullName.split(" ") : new String[0];
                String surname = nameParts.length > 0 ? nameParts[0] : null;
                String name = nameParts.length > 1 ? nameParts[1] : null;
                String patronymic = nameParts.length > 2 ? nameParts[2] : null;

                // 1. LegalEntity
                LegalEntity legalEntity = idMap.keySet().stream()
                        .filter(obj -> obj instanceof LegalEntity && ((LegalEntity) obj).getName().equals(legalEntityName))
                        .map(obj -> (LegalEntity) obj)
                        .findFirst()
                        .orElseGet(() -> {
                            LegalEntity entity = new LegalEntity();
                            entity.setName(legalEntityName);
                            idMap.put(entity, legalEntityService.save(entity).getId());
                            return entity;
                        });

                // 2. Location
                Location location = idMap.keySet().stream()
                        .filter(obj -> obj instanceof Location && ((Location) obj).getName().equals(locationName))
                        .map(obj -> (Location) obj)
                        .findFirst()
                        .orElseGet(() -> {
                            Location loc = new Location();
                            loc.setName(locationName);
                            loc.setLegalEntity(legalEntity);
                            idMap.put(loc, locationService.save(loc).getId());
                            return loc;
                        });

                // 3. Subdivision
                Subdivision subdivision = subdivisionName != null
                        ? idMap.keySet().stream()
                        .filter(obj -> obj instanceof Subdivision && ((Subdivision) obj).getName().equals(subdivisionName))
                        .map(obj -> (Subdivision) obj)
                        .findFirst()
                        .orElseGet(() -> {
                            Subdivision sub = new Subdivision();
                            sub.setName(subdivisionName);
                            sub.setLocation(location);
                            idMap.put(sub, subdivisionService.save(sub).getId());
                            return sub;
                        })
                        : null;

                // 4. Department (может быть null)
                Department department = departmentName != null
                        ? idMap.keySet().stream()
                        .filter(obj -> obj instanceof Department && ((Department) obj).getName().equals(departmentName))
                        .map(obj -> (Department) obj)
                        .findFirst()
                        .orElseGet(() -> {
                            Department dept = new Department();
                            dept.setName(departmentName);
                            dept.setSubdivision(subdivision);
                            idMap.put(dept, departmentService.save(dept).getId());
                            return dept;
                        })
                        : null;

                // 5. EmployeeGroup (может быть null)
                EmployeeGroup group = groupName != null
                        ? idMap.keySet().stream()
                        .filter(obj -> obj instanceof EmployeeGroup && ((EmployeeGroup) obj).getName().equals(groupName))
                        .map(obj -> (EmployeeGroup) obj)
                        .findFirst()
                        .orElseGet(() -> {
                            EmployeeGroup grp = new EmployeeGroup();
                            grp.setName(groupName);
                            grp.setDepartment(department);
                            idMap.put(grp, employeeGroupService.save(grp).getId());
                            return grp;
                        })
                        : null;

                // 6. JobTitle
                JobTitle jobTitle = idMap.keySet().stream()
                        .filter(obj -> obj instanceof JobTitle && jobTitleMatches((JobTitle) obj, jobTitleName))
                        .map(obj -> (JobTitle) obj)
                        .findFirst()
                        .orElseGet(() -> {
                            JobTitle jt = new JobTitle();
                            jt.setName(jobTitleName); // Устанавливаем значение имени
                            idMap.put(jt, jobTitleService.save(jt).getId());
                            return jt;
                        });


                LegalStructure legalStructure = new LegalStructure();
                legalStructure.setLocation(location);
                legalStructure.setSubdivision(subdivision);
                legalStructure.setDepartment(department);
                legalStructure.setEmployeeGroup(group);
                legalStructure.setLegalEntity(legalEntity);
                idMap.put(legalStructure, legalStructureService.save(legalStructure).getId());

                // 7. Employee
                Employee employee = new Employee();
                employee.setSurname(surname);
                employee.setName(name);
                employee.setPatronymic(patronymic);
                employee.setJobTitle(jobTitle);
                employee.setLegalStructure(legalStructure);
                employeeService.save(employee);
            }
        }
    }

    private String cleanValueOrNull(String value, String prefix) {
        if (value == null || value.isBlank()) {
            return null; // Вернуть null, если значение пустое
        }
        return cleanValue(value, prefix);
    }

    private String cleanValue(String value, String prefix) {
        if (value.startsWith(prefix)) {
            value = value.substring(prefix.length()).trim();
        }
        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }
        return value.trim();
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null; // Пустая ячейка возвращает null
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue()); // Преобразование числовых данных в строку
        }
        return cell.toString().trim(); // Другие типы (на всякий случай)
    }

    private boolean jobTitleMatches(JobTitle jobTitle, String name) {
        return name != null && name.equals(jobTitle.getName());
    }

    private boolean matchesName(Object obj, String name) {
        try {
            Method getName = obj.getClass().getMethod("getName");
            String objName = (String) getName.invoke(obj);
            return name.equals(objName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to compare names for " + obj.getClass().getSimpleName(), e);
        }
    }

    private <T> T createNullObject(Class<T> clazz, int id) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            Method setName = clazz.getMethod("setName", String.class);
            setName.invoke(obj, "null" + id); // Устанавливаем имя "null{id}"
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create null object for " + clazz.getSimpleName(), e);
        }
    }

}
