package de.thro.vv;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
        BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        BlockingQueue<AppointmentRequest> outputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        AppointmentChecker transformer = new AppointmentChecker(inputQueue, outputQueue);
        Date currentDate = new Date(1999);

        AppointmentRequest request = new AppointmentRequest("Jan", 10, currentDate);
        inputQueue.put(request);
        assertEquals(0, outputQueue.size());
        transformer.inputToOutputQueue(request);
        assertEquals(1, outputQueue.size());
    }


    @Test
    void checkAppointmentTest() {
        Date currentDate = new Date(1999);
        AppointmentRequest correct = new AppointmentRequest("Correcto", 10, currentDate);
        AppointmentRequest wrong = new AppointmentRequest("Wrongo", -10, currentDate);
        assertFalse(correct.isSuccess());
        assertFalse(wrong.isSuccess());
        // assertTrue(AppointmentChecker.checkAppointment(correct).isSuccess());
        assertFalse(AppointmentChecker.checkAppointment(wrong).isSuccess());

    }
}