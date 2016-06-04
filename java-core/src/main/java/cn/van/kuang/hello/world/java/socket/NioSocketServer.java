package cn.van.kuang.hello.world.java.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NioSocketServer {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void start(int port) {
        try {
            AbstractSelector selector = SelectorProvider.provider().openSelector();

            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);

            InetSocketAddress address = new InetSocketAddress("localhost", port);
            socketChannel.socket().bind(address);
            socketChannel.register(selector, socketChannel.validOps(), null);

            System.out.println("Started server");

            while (Thread.currentThread().isAlive()) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }

                int readyChannels = selector.select();

                if (readyChannels == 0) {
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {
                    final SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {
                        final SocketChannel clientChannel = socketChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        System.out.println("Accepted new connection from client: " + clientChannel);

                        executorService.scheduleAtFixedRate(new Runnable() {
                            public void run() {
                                ByteBuffer byteBuffer = ByteBuffer.wrap("Hi Client".getBytes());
                                try {
                                    clientChannel.write(byteBuffer);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                byteBuffer.clear();
                            }
                        }, 3000, 3000, TimeUnit.MILLISECONDS);
                    } else if (key.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) key.channel();

                        int bytesRead;
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        if ((bytesRead = clientChannel.read(buffer)) > 0) {
                            String message = new String(buffer.array()).trim();
                            buffer.flip();
                            System.out.println("Received message: [" + message + "]");
                            buffer.clear();
                        }
                        if (bytesRead < 0) {
                            clientChannel.close();
                        }
                    }
                }

                iterator.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioSocketServer().start(8888);
    }

}
