package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}