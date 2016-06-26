package cn.van.kuang.java.core.socket.oio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

    public void start(String host, int port) throws Exception {
        Socket socket = new Socket(host, port);

        System.out.println("Started Client");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("HI SERVER");

        String response;
        while ((response = in.readLine()) != null) {
            System.out.println("Received response=[" + response + "]");

            out.println("Hi again");
        }
    }

    public static void main(String[] args) throws Exception {
        new SocketClient().start("127.0.0.1", 8888);
    }

}
