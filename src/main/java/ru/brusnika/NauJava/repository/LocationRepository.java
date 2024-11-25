package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}
