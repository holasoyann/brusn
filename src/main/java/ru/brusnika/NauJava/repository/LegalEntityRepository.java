package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.LegalEntity;

@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}
