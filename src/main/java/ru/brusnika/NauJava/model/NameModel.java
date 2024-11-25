package ru.brusnika.NauJava.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NameModel {
    public String name;

    public static class LegalEntityModel extends NameModel{
        public String inn;
    }

    public static class LegalStructureModel extends NameModel{
        public Integer employeeGroupId;

        public Integer departmentId;

        public Integer subdivisionId;

        public Integer locationId;

        public Integer legalEntityId;
    }

    public static class FounderModel extends NameModel{
        public Integer legalEntityId;
    }

    public static class LocationModel extends NameModel{
        public Integer legalEntityId;
        public Integer legalAddressId;
    }

    public static class SubdivisionModel extends NameModel{
        public Integer locationId;
    }

    public static class LegalAddressModel extends NameModel{
        public Integer cityId;
        public Integer streetId;
        public String buildingNumber;
    }

    public static class DepartmentModel extends NameModel{
        public Integer subdivisionId;
    }

    public static class EmployeeModel extends NameModel{
        public String educationCountry;
        public String citizenship;
        public String surname;
        public String name;
        public String patronymic;
        public String photoLink;
        public LocalDate birthday;
        public LocalDate startedJob;
        public BigDecimal salary;

        public Integer genderId;
        public Integer jobTitleId;
        public Integer legalStructureId;
    }

    public static class EmployeeGroupModel extends NameModel{
        public Integer departmentId;
    }
}
