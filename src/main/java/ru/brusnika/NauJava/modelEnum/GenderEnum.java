package ru.brusnika.NauJava.modelEnum;

public enum GenderEnum {
    MALE("male"), FEMALE("female");

    private String title;

    GenderEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
