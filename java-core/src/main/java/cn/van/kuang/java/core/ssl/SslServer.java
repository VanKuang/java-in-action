package cn.van.kuang.java.core.ssl;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SslServer {

    public static void main(String[] args) throws Exception {
        System.setProperty("javax.net.ssl.keyStore", "/Users/VanKuang/Development/workspace/hello-world-in-java/src/main/resources/keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "*******");

        ServerSocketFactory socketFactory = SSLServerSocketFactory.getDefault();
        ServerSocket serverSocket = socketFactory.createServerSocket(9999);
        Socket socket = serverSocket.accept();

        System.out.println("Started SSL Server");

        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String content;
        while ((content = bufferedReader.readLine()) != null) {
            System.out.println(content);
            System.out.flush();
        }
    }

}
