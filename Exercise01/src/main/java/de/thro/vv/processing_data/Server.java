package de.thro.vv.processing_data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import de.thro.vv.EnvironmentVariable;
import de.thro.vv.model.AppointmentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * Server is the first step in processing information.
 * It listens to a server and reads Json files from it to store them in a queue.
 */
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final int port;
    private final Executor executor;
    private final BlockingQueue<AppointmentRequest> inputQueue;

    /**
     * @param executor Thread that listens to potential input constantly
     * @param inputQueue The storage where we save the user input for further processing
     */
    public Server(EnvironmentVariable env, Executor executor, BlockingQueue<AppointmentRequest> inputQueue) {
        this.port = env.getSocketPort();
        this.executor = executor;
        this.inputQueue = inputQueue;
    }

    /**
     * The listen method connects to a client.
     * If a client connects, the handleClient method will be called.
     */
    public void listen() {
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Server started on port {}", port);
            while (!Thread.currentThread().isInterrupted()) {
                Socket client = server.accept();
                logger.info("New client connected: {}", client.getInetAddress());
                executor.execute(() -> handleClient(client));
            }
        } catch (Exception e) {
            logger.error("Error starting server on port {}: {}", port, e.getMessage(), e);
        }
    }


    /**
     * This method is the one that reads input from the source.
     * It calls the writer method addRequest to store the input.
     * @param source refers to a Socket object that represents the connection to the client.
     */
    public void handleClient(Socket source) {
        try (JsonReader reader = new JsonReader(new InputStreamReader(source.getInputStream()))) {
            logger.info("Received request: {}", reader);
            reader.setLenient(true);

            while (!Thread.currentThread().isInterrupted()) {
                addRequest(reader);
            }
        } catch (Exception e) {
            logger.error("Error when trying to read from the InputStream", e);
        }
    }

    /**
     * addRequest takes input from the handleClient method and transforms this
     * input into a template model in the form of an AppointmentRequest.
     * It stores this AppointmentRequest into a queue.
     * @param reader refers to a JsonReader object that is used to read JSON data from the input source.
     */
    public void addRequest(JsonReader reader) {
        try {
            AppointmentRequest request = new Gson().fromJson(reader, AppointmentRequest.class);
            inputQueue.put(request);
            logger.info("Added following request to queue: {}", request);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}



