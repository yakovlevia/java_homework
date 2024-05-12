package ru.hse.client;


import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Application {

    private void endClient(){
        System.out.println("Напишите номер выбранного файла");
    }

    public static void main(String[] args) throws IOException {

        String dir = "C:/Users/79162/Desktop/java/java_homework/tmp/";

        while (true) {
            try (Socket socket = new Socket("localhost", 9999);
                 DataInputStream reader = new DataInputStream(socket.getInputStream());
                 DataOutputStream writer = new DataOutputStream(socket.getOutputStream())) {


                String str = reader.readUTF();
                System.out.println(str + ':');

                int cnt = -1;
                for (int i = 0; i < str.length(); i++) {
                    if (cnt == -1 && str.charAt(i) == '-') {
                        cnt = 0;
                        i++;
                    } else if (cnt != -1) {
                        cnt *= 10;
                        cnt += Character.getNumericValue(str.charAt(i));
                    }
                }

                List<String> files = new Vector<String>();

                for (int i = 0; i < cnt; i++) {
                    String fileName = reader.readUTF();
                    files.add(fileName);
                }

                for (int i = 0; i < cnt; i++) {
                    System.out.println(i + 1 + ") " + files.get(i));
                }
                System.out.println("Напишите номер выбранного файла");

                int num;

                while (true) {
                    Scanner myInput = new Scanner(System.in);
                    num = myInput.nextInt();

                    if (num < 1 || num > cnt) {
                        System.out.println("Некорректный номер. Введите число от 1 до " + cnt);
                    } else {
                        break;
                    }
                }

                writer.writeUTF(files.get(num - 1));

                Path dirPath = Paths.get(dir);

                try {
                    Files.createDirectories(dirPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (FileOutputStream fos = new FileOutputStream(dir + files.get(num - 1))) {
                    byte[] array = reader.readAllBytes();
                    fos.write(array, 0, array.length);
                    System.out.println("The file has been written");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        }
    }

}
