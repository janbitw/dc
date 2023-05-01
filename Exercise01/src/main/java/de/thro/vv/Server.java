package de.thro.vv;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private int port;
    private Executor executor;
    private BlockingQueue<AppointmentRequest> inputQueue;


    public Server(int port, Executor executor, BlockingQueue<AppointmentRequest> inputQueue) {
        this.port = port;
        this.executor = executor;
        this.inputQueue = inputQueue;
    }

    // Starte den Server und die Quelle
    public void listen() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("Waiting for the next connection...");
                Socket client = server.accept();
                logger.info("New connection established!");
                executor.execute(()->handleClient(client));
            }
        }
        catch(Exception x) {
            logger.error("Ausnahme {}", x.getMessage(), x);
        }
    }

    // Lese Json files aus der Quelle
    private void handleClient(Socket source)  {
        try (JsonReader reader = new JsonReader(new InputStreamReader(source.getInputStream()))){
            reader.setLenient(true);

            while (!Thread.currentThread().isInterrupted()) {
                addRequest(reader);
            }
        } catch (Exception e) {
            logger.error("Fehler", e);
        }
    }

    // Nehme Json Files aus der Quelle und füge sie der inputQueue zu
    private void addRequest(JsonReader reader){
        try{
            AppointmentRequest request = new Gson().fromJson(reader, AppointmentRequest.class);
            inputQueue.put(request);
            logger.info("Added following request to queue: {}", request);
        }
        catch(Exception e){
            Thread.currentThread().interrupt();
        }
    }
}



