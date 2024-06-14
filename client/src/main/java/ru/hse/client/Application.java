package ru.hse.client;


import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws IOException {

        String dir = "C:/tmp/";
        String host = "localhost";
        int port = 9999;

        if (args.length >= 3) {
            dir = args[2];
            host = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Bad port");
                return;
            }
        }

        while (true) {
            try (Socket socket = new Socket(host, port);
                 DataInputStream reader = new DataInputStream(socket.getInputStream());
                 DataOutputStream writer = new DataOutputStream(socket.getOutputStream())) {


                String str = reader.readUTF();
                System.out.println(str + ':');
                int cnt = reader.readInt();

                ArrayList<String> files = new ArrayList<>();

                for (int i = 0; i < cnt; i++) {
                    String fileName = reader.readUTF();
                    files.add(fileName);
                }

                for (int i = 0; i < cnt; i++) {
                    System.out.println(i + 1 + ") " + files.get(i));
                }
                System.out.println("Напишите номер выбранного файла или напишите exit для выхода");

                int num = 1;
                int flag = 0;

                while (true) {
                    Scanner myInput = new Scanner(System.in);
                    if (myInput.hasNextInt()) {
                        num = myInput.nextInt();

                        if (num < 1 || num > cnt) {
                            System.out.println("Некорректный номер. Введите число от 1 до " + cnt + " или exit для выхода");
                        } else {
                            break;
                        }
                    } else if (myInput.hasNext()) {
                        String tmp = myInput.nextLine();
                        if (tmp.equals("exit")) {
                            flag = 1;
                            break;
                        }
                    }
                }
                if (flag == 1) {
                    break;
                }


                writer.writeUTF(files.get(num - 1));

                Path dirPath = Paths.get(dir);

                try {
                    Files.createDirectories(dirPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (FileOutputStream fileOutputStream = new FileOutputStream(dir + files.get(num - 1))) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = reader.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Файл записан");
                } catch (Exception e) {
                    System.out.println("Ошибка записи");
                    e.printStackTrace();
                }

            }
        }

    }

}
