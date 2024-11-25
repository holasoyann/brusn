package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.EmployeeGroup;

@Repository
public interface EmployeeGroupRepository extends JpaRepository<EmployeeGroup, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}
