package ru.hse.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    public static long copy (Reader input, Writer output) throws IOException {

        char[] buffer = new char[8192];
        long count = 0;
        int n;
        while ((n = input.read( buffer )) != -1) {
            output.write( buffer, 0, n );
            count += n;
        }
        return count;
    }



    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("Сервер запущен, порт 9999.");

            while (true) {


                try (Socket socket = serverSocket.accept();
                     DataInputStream reader = new DataInputStream(socket.getInputStream());
                     DataOutputStream writer = new DataOutputStream(socket.getOutputStream())) {

                    List<String> files = List.of(new String[]{"1.txt", "2.txt", "3.txt"});

                    writer.writeUTF("Допустимые файлы для скачивания, количесво файлов - 3");

                    for (var q : files) {
                        writer.writeUTF(q);
                    }

                    String line;
                    line = reader.readUTF();
                    System.out.println("Передается файл " + line);



                    try (InputStream is = Server.class.getResourceAsStream("/" + line)) {
                        assert is != null;
                        System.out.println(line);
                        is.transferTo(writer);
                    }

                }
            }

        }
    }

}
