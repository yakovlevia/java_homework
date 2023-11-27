package ru.hse.homework2;

public class Student {
    private final String firstName;
    private final String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    Student(String name, String surname) {
        firstName = name;
        lastName = surname;
    }

}
