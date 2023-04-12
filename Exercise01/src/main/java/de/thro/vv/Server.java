package de.thro.vv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private int port;
    private Executor executor;
    private BlockingQueue<String> queue;

    public Server(int port, Executor executor, BlockingQueue<String> queue) {
        this.port = port;
        this.executor = executor;
        this.queue = queue;
    }

    public void listen() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("Vor verbindung");
                Socket client = server.accept();
                logger.info("Nachverbindung");

                executor.execute(() -> {
                    logger.info("Thread {}", Thread.currentThread().getName());
                    handleClient(client);
                });
            }
        }
        catch(Exception x) {
            logger.error("Ausnahme {}", x.getMessage(), x);
        }

    }

    private void handleClient(Socket client)  {
        try {
            Scanner scan = new Scanner(client.getInputStream());
            PrintStream print = new PrintStream(client.getOutputStream());

            while (!Thread.currentThread().isInterrupted()) {
                if (scan.hasNext()) {
                    String zeile = scan.nextLine();
                    queue.put(zeile);
                    logger.info("Zeile: {}", zeile);
                    print.println("Huhu:  " + zeile);
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.error("Fehler", e);
            Thread.currentThread().interrupt();
        }
    }
}



