package ru.hse.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServerNonBlocking {

    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(8888));
            serverSocketChannel.configureBlocking(false);
            try (Selector selector = Selector.open()) {
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                ByteBuffer result = ByteBuffer.allocate(4);
                ByteBuffer byteBuffer = ByteBuffer.allocate(8);
                int size = 0;

                while (true) {
                    int select = selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();

                    while (iterator.hasNext()) {
                        try {
                            SelectionKey key = iterator.next();

                            if (key.isAcceptable()) {
                                SocketChannel socketChannel = serverSocketChannel.accept();
                                socketChannel.configureBlocking(false);
                                socketChannel.register(selector, SelectionKey.OP_READ);
                            }

                            if (key.isReadable()) {

                                var channel = (SocketChannel) key.channel();

                                int read = channel.read(byteBuffer);
                                //System.out.println(read);
                                if (read == -1) {
                                    System.out.println('A');
                                    key.cancel();
                                    channel.close();
                                }
                                else  {
                                    size += read;
                                    System.out.println("B" + size);
                                    if (size >= 8) {
                                        size -= 8;
                                        int a = byteBuffer.getInt(0);
                                        int b = byteBuffer.getInt(4);
                                        System.out.println("C: " + a + '+' + b);
                                        result.asIntBuffer().put(a + b);
                                        key.interestOps(SelectionKey.OP_WRITE);
                                        byteBuffer.flip();
                                    }
                                }
                            }
                            else if (key.isWritable()) {
                                var channel = (SocketChannel) key.channel();
                                channel.write(result);
                                result.flip();
                                key.interestOps(SelectionKey.OP_READ);
                            }

                        } finally {
                            iterator.remove();
                        }

                    }
                }
            }
        }
    }

}
