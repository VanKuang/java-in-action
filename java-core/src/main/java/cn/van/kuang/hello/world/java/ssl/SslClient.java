package cn.van.kuang.hello.world.java.ssl;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

public class SslClient {

    public static void main(String[] args) throws Exception {
        System.setProperty("javax.net.ssl.trustStore", "/Users/VanKuang/Development/workspace/hello-world-in-java/src/main/resources/keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "1234@qwer");

        SocketFactory socketFactory = SSLSocketFactory.getDefault();
        Socket socket = socketFactory.createSocket("localhost", 9999);

        System.out.println("Started SSL Client");

        InputStream inputStream = System.in;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        String content;
        while ((content = bufferedReader.readLine()) != null) {
            bufferedWriter.write(content + "\n");
            bufferedWriter.flush();
        }
    }

}
