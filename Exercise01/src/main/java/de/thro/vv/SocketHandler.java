package de.thro.vv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler {

    private int socketPort;
    private ServerSocket serverSocket;
    private boolean running;

    public SocketHandler(int socketPort) {
        this.socketPort = socketPort;
        this.running = true;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(socketPort);
            System.out.println("Socket started on port " + socketPort);

            while (running) {
                Socket socket = serverSocket.accept();
                handleRequest(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(Socket socket) {
        // Parse JSON object and add to queue
    }

    public void stop() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

