package ru.brusnika.NauJava.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String educationCountry;
    private String citizenship;
    private String surname;
    private String name;
    private String patronymic;
    private String photoLink;
    private LocalDate birthday;
    private LocalDate startedJob;
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;

    @ManyToOne
    @JoinColumn(name = "legal_structure_id")
    private LegalStructure legalStructure;
}
