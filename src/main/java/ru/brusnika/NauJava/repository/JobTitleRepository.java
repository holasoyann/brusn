package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.JobTitle;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}
