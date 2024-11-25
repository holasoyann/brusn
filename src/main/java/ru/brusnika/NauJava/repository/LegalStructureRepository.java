package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.LegalStructure;

@Repository
public interface LegalStructureRepository extends JpaRepository<LegalStructure, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}
