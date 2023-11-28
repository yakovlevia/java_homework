package ru.hse.homework2;

import java.util.Objects;

public class Student {
    private final String firstName;
    private final String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Student(String firstName, String lastName) {
        this.firstName = Objects.requireNonNull(firstName, "string must not be null");
        this.lastName = Objects.requireNonNull(lastName, "string must not be null");
    }

}
