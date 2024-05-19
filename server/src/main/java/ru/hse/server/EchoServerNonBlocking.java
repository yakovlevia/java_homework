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
                int val = 0;
                int flag = 0;
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
                                ByteBuffer byteBuffer = ByteBuffer.allocate(8);
                                var channel = (SocketChannel) key.channel();

                                int read = channel.read(byteBuffer);
                                if (read == -1) {
                                    //System.out.println("A");
                                    key.cancel();
                                    channel.close();
                                }
                                else if (read == 4) {
                                    //System.out.println("B");
                                    int a = byteBuffer.getInt(0);
                                    if (flag == 0) {
                                        val = a;
                                        flag = 1;
                                    }
                                    else {
                                        result.asIntBuffer().put(a + val);
                                        key.interestOps(SelectionKey.OP_WRITE);
                                        byteBuffer.flip();
                                        flag = 0;
                                    }
                                } else {
                                    //System.out.println("C");
                                    int a = byteBuffer.getInt(0);
                                    int b = byteBuffer.getInt(4);
                                    result.asIntBuffer().put(a + b);
                                    key.interestOps(SelectionKey.OP_WRITE);
                                    byteBuffer.flip();
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
