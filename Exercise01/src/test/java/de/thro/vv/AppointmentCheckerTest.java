package de.thro.vv;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentCheckerTest {
    BlockingQueue<AppointmentRequest> inputQueue;
    BlockingQueue<AppointmentRequest> outputQueue;

    @Test
    void constructor() {
        assertDoesNotThrow(() -> new AppointmentChecker(inputQueue, outputQueue));
    }

    @Test
    void inputToOutputQueueTest() throws InterruptedException {
        int MAX_THREADS = 10;
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        BlockingQueue<AppointmentRequest> outputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        Server server = new Server(1024, executor, inputQueue);
        AppointmentChecker transformer = new AppointmentChecker(inputQueue, outputQueue);
        Date currentDate = new Date(1999);

        AppointmentRequest request = new AppointmentRequest("Jan", 10, currentDate);
        inputQueue.put(request);
        assertEquals(0, outputQueue.size());
        transformer.inputToOutputQueue(request);
        assertEquals(1, outputQueue.size());
    }


    @Test
    void checkAppointmentTest() throws InterruptedException {
        int MAX_THREADS = 10;
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        BlockingQueue<AppointmentRequest> outputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        Server server = new Server(1024, executor, inputQueue);
        AppointmentChecker transformer = new AppointmentChecker(inputQueue, outputQueue);

        Date currentDate = new Date(1999);
        AppointmentRequest correct = new AppointmentRequest("Jan", 10, currentDate);
        AppointmentRequest wrong = new AppointmentRequest("Jan", -10, currentDate);
        assertEquals(false, correct.isSuccess());
        assertEquals(false, wrong.isSuccess());
        assertEquals(true, AppointmentChecker.checkAppointment(correct).isSuccess());
        assertEquals(false, AppointmentChecker.checkAppointment(wrong).isSuccess());

    }
}