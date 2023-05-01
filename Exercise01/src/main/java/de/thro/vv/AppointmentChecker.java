package de.thro.vv;
import java.sql.Date;
import java.util.concurrent.BlockingQueue;
import static de.thro.vv.EnvironmentVariables.logger;

public class AppointmentChecker {
    private static final int[] DAY = new int[24];
    private final static int MAX_CUSTOMERS_PER_HOUR = EnvironmentVariables.MAX_CUSTOMERS_PER_HOUR;

    private BlockingQueue<AppointmentRequest> inputQueue;
    private BlockingQueue<AppointmentRequest> outputQueue;

    public AppointmentChecker(BlockingQueue<AppointmentRequest> inputQueue, BlockingQueue<AppointmentRequest> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
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
    void inputToOutputQueue(AppointmentRequest request) {
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

    // Check if appointment can be scheduled. Then, set its success status accordingly and return the appointment.
    static AppointmentRequest checkAppointment(AppointmentRequest appointment) {
        int requestHour = appointment.getAppointmentRequestHour();

        if ((requestHour < 0 || requestHour > 23) || DAY[requestHour] > MAX_CUSTOMERS_PER_HOUR) {
            return appointment;
        }

        DAY[requestHour] += 1;
        appointment.setSuccess(true);
        return appointment;
    }
}
