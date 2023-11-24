package ru.hse.homework1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("If you want to convert centimeters to inches enter 1");
        System.out.println("If you want to convert inches to centimeters enter 2");

        int flag = scanner.nextInt();

        if (flag == 1) {
            System.out.println("Enter centimeters:");
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                value /= 2.54d;
                System.out.println("In inches: " + value);
            } else {
                System.out.println("Number is incorrect");
            }
        } else if (flag == 2) {
            System.out.println("Enter inches:");
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                value *= 2.54d;
                System.out.println("In centimeters: " + value);
            } else {
                System.out.println("Number is incorrect");
            }
        } else {
            System.out.println("Number is incorrect");
        }

    }
}