package ru.hse.homework2;

import java.util.Random;

public class StudentSelector {
    Student[] students;
    Random Random = new Random();

    Student nextRandomStudent() {
        int pos = Random.nextInt(count());
        return students[pos];
    }

    boolean hasStudents() {
        return students.length > 0;
    }

    int count() {
        return students.length;
    }

    StudentSelector(Student[] people) {
        if (people == null) {
            System.out.println("Некоректный массив");
            throw new NullPointerException();
        }
        students = people;
    }
}
