package de.thro.vv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int MAX_THREADS = 10;

    public static void main(String[] args) {
        Main m = new Main();
        m.logBasicDetails();

        EnvironmentVariable env = new EnvironmentVariable();
        try {
            Map<String, String> envs = System.getenv();
            env.setENVS(envs);
        } catch (IllegalArgumentException e) {
            System.exit(-1);
        }
        int port = env.getSocketPort();

        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        BlockingQueue<AppointmentRequest> outputQueue = new ArrayBlockingQueue<>(MAX_THREADS);

        // Create server
        Server serverListener = new Server(port, executor, inputQueue);
        Thread server = new Thread(serverListener::listen);
        server.start();

        // Appointment Checker erstellen und starten
        AppointmentChecker appointmentListener = new AppointmentChecker(inputQueue, outputQueue);
        Thread appointmentCheckerThread = new Thread(appointmentListener::listen);
        appointmentCheckerThread.start();

        // File archive manager
        FileArchiveService fileManager = new FileArchiveService(outputQueue);
        Thread fileArchiveserviceThread = new Thread(fileManager::listen);
        fileArchiveserviceThread.start();
    }

    public void logBasicDetails() {
        logger.info("Start program");
        logger.info("JAVA_HOME: {}", System.getenv("JAVA_HOME"));
        logger.info("Operating System: {}", System.getenv("os.name"));
        logger.info("Version: {}", System.getenv("os.version"));
        logger.info("User: {}", System.getenv("user.name"));
    }
}
