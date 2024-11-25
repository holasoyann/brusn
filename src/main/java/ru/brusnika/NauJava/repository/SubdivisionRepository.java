package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.Subdivision;

@Repository
public interface SubdivisionRepository extends JpaRepository<Subdivision, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}