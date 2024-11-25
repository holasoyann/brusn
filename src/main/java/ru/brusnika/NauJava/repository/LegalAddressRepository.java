package ru.brusnika.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.brusnika.NauJava.model.LegalAddress;

@Repository
public interface LegalAddressRepository extends JpaRepository<LegalAddress, Integer> {
    // Дополнительные пользовательские запросы можно определить здесь
}
