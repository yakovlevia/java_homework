package ru.hse.homework2;

import java.util.Scanner;

public class Main {

    private static StudentSelector createNewStudentSelector() {
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

    private static void about() {
        System.out.println("Автор: Яковлев Иван");
    }

    private static void unknownCommand() {
        System.out.println("Неизвестная команда");
    }

    private static void exit() {
        System.out.println("Завершение программы");
    }

    private static void nextStudent(StudentSelector selector) {
        Student student = selector.nextRandomStudent();
        if (student != null) {
            System.out.println("К доске идет " + student.getFirstName() + " " + student.getLastName());
        } else {
            System.out.println("В классе нет людей");
        }

    }

    public static void main(String[] args) {
        StudentSelector students = createNewStudentSelector();

        Scanner scanner = new Scanner(System.in);
        System.out.println("В группе " + students.count() + " студентов.");

        while (true) {
            System.out.println("""
                    Введите:
                    * next, чтобы узнать, кто пойдёт к доске
                    * about, чтобы узнать автора программы
                    * exit, чтобы выйти
                    Ваш выбор?""");

            if (scanner.hasNext()) {
                String command;
                command = scanner.nextLine();
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
}