package de.thro.vv;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int MAX_THREADS = 10;

    public static void main(String[] args) {

        int port = EnvironmentVariables.getSocketPort();
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        BlockingQueue<AppointmentRequest> outputQueue = new ArrayBlockingQueue<>(MAX_THREADS);

        // Create server
        Server serverListener = new Server(1024, executor, inputQueue);
        Thread server = new Thread(serverListener::listen);
        server.start();

        // Appointment Checker erstellen und starten
        AppointmentChecker appointmentListener = new AppointmentChecker(inputQueue, outputQueue);
        Thread appointmentCheckerThread = new Thread(appointmentListener::listen);
        appointmentCheckerThread.start();

        // File archive manager
        FileArchiveService fileManager = new FileArchiveService(outputQueue);
        Thread fileArchiveserviceThread = new Thread(appointmentListener::listen);
        fileArchiveserviceThread.start();

    }
}
