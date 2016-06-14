package cn.van.kuang.hello.world.java.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public void start(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Going to accept request from client");

        while (Thread.currentThread().isAlive()) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }

            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String request, response;
            while ((request = in.readLine()) != null) {
                System.out.println("Received request=[" + request + "]");

                response = processRequest(request);
                out.println(response);
            }
        }
    }

    private String processRequest(String request) throws Exception {
        Thread.sleep(1000L);

        return "Response of " + request;
    }

    public static void main(String[] args) throws Exception {
        new SocketServer().start(8888);
    }

}
