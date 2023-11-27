package ru.hse.homework2;

import java.io.Console;
import java.util.Scanner;

public class Main {

    static StudentSelector createNewStudentSelector() {
        Student[] group = new Student[]{
                new Student("Герман", "Альберштейн"),
                new Student("Георгий", "Беликов"),
                new Student("Никита", "Кислов"),
                new Student("Алексей", "Коротков"),
                new Student("Степан", "Кошевой"),
                new Student("Максим", "Леонидов"),
                new Student("Дмитрий", "Проскурин"),
                new Student("Дмитрий", "Сергеев"),
                new Student("Тимофей", "Синицын"),
                new Student("Александр", "Соколов"),
                new Student("Михаил", "Хритов"),
                new Student("Аркадий", "Щукин"),
                new Student("Иван", "Яковлев"),
                new Student("Богдан", "Чирков"),
        };

        return new StudentSelector(group);
    }

    static void about() {
        System.out.println("Автор: Яковлев Иван");
    }

    static void unknownCommand() {
        System.out.println("Неизвестная команда");
    }

    static void exit() {
        System.out.println("Завершение программы");
    }

    static void nextStudent(StudentSelector selector) {
        Student student = selector.nextRandomStudent();
        System.out.println("К доске идет " + student.getFirstName() + " " + student.getLastName());
    }

    public static void main(String[] args) {
        StudentSelector students = createNewStudentSelector();

        Scanner scanner = new Scanner(System.in);
        System.out.println("В группе " + students.count() + " студентов.\n");

        while (true) {
            System.out.println("Введите:\n" +
                    "* next, чтобы узнать, кто пойдёт к доске\n" +
                    "* about, чтобы узнать автора программы\n" +
                    "* exit, чтобы выйти\n" +
                    "Ваш выбор?");

            String command = scanner.nextLine();
            if (command.equals("next")) {
                nextStudent(students);
            } else if (command.equals("about")) {
                about();
            } else if (command.equals("exit")) {
                exit();
                break;
            } else {
                unknownCommand();
            }
        }

    }
}