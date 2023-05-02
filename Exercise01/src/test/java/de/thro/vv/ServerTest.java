package de.thro.vv;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {
    BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(1);
    ExecutorService executor = Executors.newFixedThreadPool(1);

    @Test
    void constructor() {
        assertDoesNotThrow(() -> new Server(1024, executor, inputQueue));
    }


    @Test
    void addRequestTest() throws InterruptedException {
        int MAX_THREADS = 10;
        BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(MAX_THREADS);

        String json = "{ \"customerName\": \"Jan\", \"appointmentRequestHour\": \"10\", \"createDate\": \"2012-04-23T18:25:43.511Z\"}";
        AppointmentRequest request = new Gson().fromJson(json, AppointmentRequest.class);
        assertEquals("Jan", request.getCustomerName());
        assertEquals(10, request.getAppointmentRequestHour());

        inputQueue.put(request);
        assertEquals(1, inputQueue.size());
    }
}