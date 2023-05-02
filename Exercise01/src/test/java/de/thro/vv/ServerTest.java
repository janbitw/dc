package de.thro.vv;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

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
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(MAX_THREADS);
        Server server = new Server(1024, executor, inputQueue);

        Date currentDate = new Date(1999);
        String json = "{ \"customerName\": \"Jan\", \"appointmentRequestHour\": \"10\", \"createDate\": \"2012-04-23T18:25:43.511Z\"}";
        AppointmentRequest request = new Gson().fromJson(json, AppointmentRequest.class);
        assertEquals("Jan", request.getCustomerName());
        assertEquals(10, request.getAppointmentRequestHour());
        assertEquals(0, inputQueue.size());

        inputQueue.put(request);
        assertEquals(1, inputQueue.size());
    }
}