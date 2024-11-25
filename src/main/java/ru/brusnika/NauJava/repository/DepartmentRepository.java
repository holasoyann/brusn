package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}