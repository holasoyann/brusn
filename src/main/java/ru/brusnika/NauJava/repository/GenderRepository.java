package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}
