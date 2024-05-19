package ru.hse.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NumberClient {

     public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket socket = new Socket("localhost", 8888);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            int a = 10;
            outputStream.writeInt(a);
            int b = 20;
            Thread.sleep(1000);
            outputStream.writeInt(b);

            System.out.println(a + " + " + b + " = " + inputStream.readInt());

            a = 1;
            b = 2;
            Thread.sleep(2000);
            outputStream.writeInt(a);
            outputStream.writeInt(b);
            System.out.println(a + " + " + b + " = " + inputStream.readInt());

        }
    }

}
