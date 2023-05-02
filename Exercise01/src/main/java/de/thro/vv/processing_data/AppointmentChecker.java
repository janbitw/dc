package de.thro.vv.processing_data;

import de.thro.vv.model.AppointmentRequest;
import de.thro.vv.EnvironmentVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class AppointmentChecker {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentChecker.class);
    private static final int[] DAY = new int[24];
    private static final EnvironmentVariable env = new EnvironmentVariable();
    private final BlockingQueue<AppointmentRequest> inputQueue;
    private final BlockingQueue<AppointmentRequest> outputQueue;
    Map<String, String> envs = System.getenv();

    public AppointmentChecker(BlockingQueue<AppointmentRequest> inputQueue, BlockingQueue<AppointmentRequest> outputQueue) {
        env.setENVS(envs);
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    // Check if appointment can be scheduled. Then, set its success status accordingly and return the appointment.
    public static AppointmentRequest checkAppointment(AppointmentRequest appointment) {
        int requestHour = appointment.getAppointmentRequestHour();

        if ((requestHour < 0 || requestHour > 23) || DAY[requestHour] > env.getMaxCustomersPerHour()) {
            return appointment;
        }

        DAY[requestHour] += 1;
        appointment.setSuccess(true);
        return appointment;
    }

    // Read AppointmentRequest from global inputQueue.
    // Take this appointmentRequest and
    public void listen() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                AppointmentRequest appointment = inputQueue.take();
                logger.info("Appointment that has been taken out of the inputQueue: {}", appointment);
                inputToOutputQueue(appointment);
            } catch (InterruptedException e) {
                logger.error("Error: {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    // Take the request you listened to and check first if it is valid by calling checkAppointment,
    // and then save this processed request into outputQueue.
    public void inputToOutputQueue(AppointmentRequest request) {
        AppointmentRequest processedRequest = checkAppointment(request);
        try {
            logger.info("Following appointment will be added to outputQueue: {}", processedRequest);
            outputQueue.put(processedRequest);
            logger.info("Following appointment has be added to outputQueue: {}", processedRequest);
        } catch (InterruptedException e) {
            logger.error("Couldn't put new AppointmentRequest into : {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }
}
