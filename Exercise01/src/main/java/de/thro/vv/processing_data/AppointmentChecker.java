package de.thro.vv.processing_data;

import de.thro.vv.model.AppointmentRequest;
import de.thro.vv.EnvironmentVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.BlockingQueue;

/**
 * AppointmentChecker is the second step in processing data.
 * The data at this point is in form of AppointmentRequests stored in a queue.
 * The goal of AppointmentChecker is to verify if those requests are valid (eg in the opening times)
 * and then move them from the input queue to the output queue.
 */
public class AppointmentChecker {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentChecker.class);
    private int[] day = new int[24];
    private EnvironmentVariable env;
    private final BlockingQueue<AppointmentRequest> inputQueue;
    private final BlockingQueue<AppointmentRequest> outputQueue;

    /**
     * The constructor sets the environment variables like opening times of the system.
     * @param inputQueue We take data from here.
     * @param outputQueue We store data into here.
     */
    public AppointmentChecker(EnvironmentVariable env, BlockingQueue<AppointmentRequest> inputQueue, BlockingQueue<AppointmentRequest> outputQueue) {
        this.env = env;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    /**
     * The listen method takes data from the inputQueue if available and puts into the output queue
     * by calling the inputToOutputQueue.
     */
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

    /**
     * This method receives an appointment from the listen method and
     * stores it in the output queue.
     * Before it puts data from the input queue into the output queue,
     * it calls the check appointment method to mark the appointments either as successful or not.
     * @param request AppointmentRequest that needs to be stored into the outputQueue.
     */
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

    /**
     * Receive an appointment and check if the appointment is valid (in the opening times and not overbooked )
     * Then, set the appointment's success status accordingly and return the appointment.
     * @param appointment AppointmentRequest that needs to be checked for its validity.
     * @return Return the same appointment with success attribute on true if the appointment is valid.
     */
    //
    public AppointmentRequest checkAppointment(AppointmentRequest appointment) {
        int requestHour = appointment.getAppointmentRequestHour();

        if (requestHour < 0 || requestHour > 23 || day[requestHour] >= env.getMaxCustomersPerHour()) {
            return appointment;
        }

        day[requestHour] += 1;
        appointment.setSuccess(true);
        return appointment;
    }
}
