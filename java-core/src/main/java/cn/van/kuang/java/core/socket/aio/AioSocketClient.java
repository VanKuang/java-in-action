package cn.van.kuang.java.core.socket.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AioSocketClient {

    public void start(String host, int port) throws IOException {
        CountDownLatch latch = new CountDownLatch(1);

        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        socketChannel.connect(
                new InetSocketAddress(host, port),
                socketChannel,
                new CompletionHandler<Void, AsynchronousSocketChannel>() {
                    @Override
                    public void completed(Void result, AsynchronousSocketChannel channel) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                        channel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                            @Override
                            public void completed(Integer result, ByteBuffer buffer) {
                                System.out.println("["
                                        + Thread.currentThread().getName()
                                        + "] Received: "
                                        + new String(buffer.array()).trim());
                            }

                            @Override
                            public void failed(Throwable exc, ByteBuffer buffer) {
                                exc.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void failed(Throwable exc, AsynchronousSocketChannel channel) {
                        exc.printStackTrace();
                        try {
                            channel.close();
                            latch.countDown();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new AioSocketClient().start("127.0.0.1", 9990);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
