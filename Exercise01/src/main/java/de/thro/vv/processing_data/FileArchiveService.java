package de.thro.vv.processing_data;

import com.google.gson.Gson;
import de.thro.vv.model.AppointmentRequest;
import de.thro.vv.EnvironmentVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 FileArchiveService is the last step in processing data.
 It takes appointments from the output queue and puts them
 into success or failed folders
 */
public class FileArchiveService {
    private static final Logger logger = LoggerFactory.getLogger(FileArchiveService.class);
    private static final String DELIMITER = "/";
    private static final EnvironmentVariable env = new EnvironmentVariable();
    private static final String PATH = env.getSavePath();
    private final BlockingQueue<AppointmentRequest> queue;
    Map<String, String> envs = System.getenv();


    /**
     * The constructor also sets environment variables.
     * @param queue Output queue
     */
    public FileArchiveService(BlockingQueue<AppointmentRequest> queue) {
        env.setENVS(envs);
        this.queue = queue;
    }

    /**
     * This method takes appointments from the output queue and
     * saves the files into folders by calling the saveFile method.
     */
    public void listen() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                AppointmentRequest request = queue.take();
                logger.info("Following appointment has been taken out of output queue: {}", request);
                saveFile(request);
            } catch (InterruptedException e) {
                logger.error("Error: {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * This method takes the appointments and checks wether the appointment is legit.
     * If the appointment is legit, it saves the appointment into a folder with successful appointments,
     * if not in a failed directory.
     * @param request AppointmentRequest that needs to be saved.
     */
    public void saveFile(AppointmentRequest request) {
        String folderName = request.isSuccess() ? PATH + "/success" : PATH + "/failed";
        File folder = new File(folderName + DELIMITER + request.getAppointmentRequestHour());

        String fileName = request.getCustomerName() + ".json";
        File file = new File(folder.getPath() + DELIMITER + fileName);

        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(request, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}