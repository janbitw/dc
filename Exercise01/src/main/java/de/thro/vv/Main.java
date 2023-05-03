package de.thro.vv;

import de.thro.vv.model.AppointmentRequest;
import de.thro.vv.processing_data.AppointmentChecker;
import de.thro.vv.processing_data.FileArchiveService;
import de.thro.vv.processing_data.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The main method calls all the processing data classes, sets EnvironmentVariables and logs basic details.
 * @author Jan Tamas
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int MAX_THREADS = 10;

    public static void main(String[] args) {
        Main m = new Main();
        m.logBasicDetails();

        // Set environment variables
        // These are the ones that I use:
        // END_TIME=17;MAX_CUSTOMERS_PER_HOUR=1;SAVE_PATH=appointments;SOCKET_PORT=1024;START_TIME=8
        EnvironmentVariable env = new EnvironmentVariable();
        try {
            Map<String, String> envs = System.getenv();
            env.setENVS(envs);
        } catch (IllegalArgumentException e) {
            System.exit(-1);
        }

        // These are the variables that are used to process data
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        BlockingQueue<AppointmentRequest> outputQueue = new ArrayBlockingQueue<>(MAX_THREADS);

        // First step: Server -> InputQueue
        Server serverListener = new Server(env, executor, inputQueue);
        Thread server = new Thread(serverListener::listen);
        server.start();


        // Second step: InputQueue -> OutputQueue
        AppointmentChecker appointmentListener = new AppointmentChecker(env, inputQueue, outputQueue);
        Thread appointmentCheckerThread = new Thread(appointmentListener::listen);
        appointmentCheckerThread.start();

        // Third step: OutputQueue -> Folder
        FileArchiveService fileManager = new FileArchiveService(env, outputQueue);
        Thread fileArchiveserviceThread = new Thread(fileManager::listen);
        fileArchiveserviceThread.start();
    }

    /**
     * Log basic details for the user to check
     */
    public void logBasicDetails() {
        logger.info("Start program");
        logger.info("JAVA_HOME: {}", System.getenv("JAVA_HOME"));
        logger.info("Operating System: {}", System.getenv("os.name"));
        logger.info("Version: {}", System.getenv("os.version"));
        logger.info("User: {}", System.getenv("user.name"));
    }
}
