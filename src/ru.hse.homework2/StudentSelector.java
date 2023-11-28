package ru.hse.homework2;

import java.util.Objects;
import java.util.Random;

public class StudentSelector {
    private final Student[] students;
    private final Random random = new Random();

    public Student nextRandomStudent() {
        if (hasStudents()) {
            int pos = random.nextInt(count());
            return students[pos];
        }
        return null;
    }

    public boolean hasStudents() {
        return students.length > 0;
    }

    public int count() {
        return students.length;
    }

    public StudentSelector(Student[] students) {
        this.students = Objects.requireNonNull(students, "array must not be null");
    }
}
