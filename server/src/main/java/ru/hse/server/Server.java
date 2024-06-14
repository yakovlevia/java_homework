package ru.hse.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("Сервер запущен, порт 9999.");


            while (!serverSocket.isClosed()) {
                Thread thread = new Thread(() -> {
                    try (Socket socket = serverSocket.accept();
                         DataInputStream reader = new DataInputStream(socket.getInputStream());
                         DataOutputStream writer = new DataOutputStream(socket.getOutputStream())
                    ) {
                        try {
                            answerRequest(socket, reader, writer);
                        } catch (Exception e) {
                            System.out.println("Ответ закрыт");
                        }

                    } catch (Exception e) {
                        System.out.println("Ошибка сервера");
                    }
                });
                thread.start();

            }
        } catch (Exception e) {
            System.out.println("Ошибка сервера1");
        }
    }

    private static void answerRequest(Socket socket, DataInputStream reader, DataOutputStream writer) throws IOException {

        List<String> files = List.of("1.txt", "2.txt", "3.txt", "pict.png");

        writer.writeUTF("Допустимые файлы для скачивания, количесво файлов - 4");
        writer.writeInt(4);

        for (var q : files) {
            writer.writeUTF(q);
        }

        String line;
        line = reader.readUTF();

        if (line.equals("exit")) {
            socket.close();
            return;
        }

        System.out.println("Передается файл " + line);


        try (InputStream fileInputStream = Server.class.getResourceAsStream("/" + line);) {
            if (fileInputStream == null) {
                System.out.println("AAAA");
            }
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                writer.write(buffer, 0, bytesRead);
            }
            System.out.println("Файл успешно передан");
        }

    }


}
