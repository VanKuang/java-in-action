package cn.van.kuang.java.core.socket.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AioSocketServer {

    public void start(int port) throws IOException {
        CountDownLatch latch = new CountDownLatch(1);

        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));

        serverSocketChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AioSocketServer>() {
            @Override
            public void completed(AsynchronousSocketChannel channel, AioSocketServer attachment) {
                System.out.println("[" + Thread.currentThread().getName() + "] Received connection");

                serverSocketChannel.accept(attachment, this);

                ByteBuffer buffer = ByteBuffer.wrap("HI, I'm Server".getBytes());

                channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer byteBuffer) {
                        if (byteBuffer.hasRemaining()) {
                            channel.write(byteBuffer, byteBuffer, this);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        exc.printStackTrace();

                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void failed(Throwable exc, AioSocketServer attachment) {
                try {
                    serverSocketChannel.close();
                    latch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Started");

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new AioSocketServer().start(9990);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
