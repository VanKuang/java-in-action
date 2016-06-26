package cn.van.kuang.java.core.socket.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioSocketClient {

    public void start(String host, int port) {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(host, port);
            SocketChannel socketChannel = SocketChannel.open(socketAddress);
            socketChannel.configureBlocking(false);

            System.out.println("Connected to server");

            while (Thread.currentThread().isAlive()) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }

                byte[] bytes = "Hi Server".getBytes();
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                socketChannel.write(byteBuffer);

                ByteBuffer buffer = ByteBuffer.allocate(256);
                socketChannel.read(buffer);
                String message = new String(buffer.array()).trim();
                System.out.println("Received message: [" + message + "]");

                Thread.sleep(3000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioSocketClient().start("localhost", 8888);
    }
}
