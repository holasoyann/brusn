package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.Street;

@Repository
public interface StreetRepository extends JpaRepository<Street, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}
