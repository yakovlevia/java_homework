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

    public static void main(String[] args) throws IOException {

        String dir = "C:/tmp/";
        String host = "localhost";
        int port = 9999;

        if (args.length >= 3) {
            dir = args[2];
            host = args[0];
            port = Integer.parseInt(args[1]);
        }


        while (true) {
            try (Socket socket = new Socket(host, port);
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
                    }
                    else if (myInput.hasNext()) {
                        String tmp = myInput.nextLine();
                        if (tmp.equals("exit")) {
                            flag = 1;
                            break;
                        }
                    }
                }

                if (flag == 0) {
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
                else {
                    writer.writeUTF("exit");
                    socket.close();
                    break;
                }

            }

        }
    }

}
