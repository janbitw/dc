package de.thro.vv;

import de.thro.vv.model.AppointmentRequest;
import de.thro.vv.processing_data.AppointmentChecker;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test two methods and constructor of AppointmentChecker
 */
class AppointmentCheckerTest {
    BlockingQueue<AppointmentRequest> inputQueue;
    BlockingQueue<AppointmentRequest> outputQueue;
    EnvironmentVariable env = new EnvironmentVariable();

    @Test
    void constructor() {
        assertDoesNotThrow(() -> new AppointmentChecker(env, inputQueue, outputQueue));
    }

    /**
     * Test inputToOutputQueue by verifying whether AppointmentRequests are rightfully put into the inputQueue
     * @throws InterruptedException can be thrown by inputQueue.put(request)
     */
    @Test
    void inputToOutputQueueTest() throws InterruptedException {
        int MAX_THREADS = 10;
        BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        BlockingQueue<AppointmentRequest> outputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        AppointmentChecker transformer = new AppointmentChecker(env,inputQueue, outputQueue);
        Date currentDate = new Date(1999);

        AppointmentRequest request = new AppointmentRequest("Jan", 10, currentDate);
        inputQueue.put(request);
        assertEquals(0, outputQueue.size());
        transformer.inputToOutputQueue(request);
        assertEquals(1, outputQueue.size());
    }

    /**
     * Test checkAppointmentTest by verifying whether AppointmentRequest's success status is set accordingly.
     */
    @Test
    void checkAppointmentTest() {
        AppointmentChecker transformer = new AppointmentChecker(env,inputQueue, outputQueue);
        Date currentDate = new Date(1999);
        AppointmentRequest correct = new AppointmentRequest("Correcto", 10, currentDate);
        AppointmentRequest wrong = new AppointmentRequest("Wrongo", -10, currentDate);
        assertFalse(correct.isSuccess());
        assertFalse(wrong.isSuccess());
        assertFalse(transformer.checkAppointment(wrong).isSuccess());

    }
}