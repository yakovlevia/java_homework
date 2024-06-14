package ru.hse.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.Duration;

public class NumberClient {

     public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket socket = new Socket("localhost", 8888);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            outputStream.writeByte(0);
            outputStream.writeByte(0);
            outputStream.writeByte(0);
            Thread.sleep(Duration.ofSeconds(1));
            outputStream.writeByte(0);

            int a = 0;
            int b = 20;
            outputStream.writeInt(b);

            System.out.println(a + " + " + b + " = " + inputStream.readInt());

            a = 5;
            b = 6;
            outputStream.writeInt(a);
            outputStream.writeInt(b);

            System.out.println(a + " + " + b + " = " + inputStream.readInt());

            a = 1;
            b = 2;
            outputStream.writeInt(a);
            Thread.sleep(1000);
            outputStream.writeInt(b);

            System.out.println(a + " + " + b + " = " + inputStream.readInt());


        }
    }

}
