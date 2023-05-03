package de.thro.vv.processing_data;

import com.google.gson.Gson;
import de.thro.vv.model.AppointmentRequest;
import de.thro.vv.EnvironmentVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

/**
 FileArchiveService is the last step in processing data.
 It takes appointments from the output queue and puts them
 into success or failed folders
 */
public class FileArchiveService {
    private static final Logger logger = LoggerFactory.getLogger(FileArchiveService.class);
    private static final String DELIMITER = "/";
    private final BlockingQueue<AppointmentRequest> queue;
    private EnvironmentVariable env;


    /**
     * The constructor also sets environment variables.
     * @param queue Output queue
     */
    public FileArchiveService(EnvironmentVariable env, BlockingQueue<AppointmentRequest> queue) {
        this.env = env;
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
        String path = env.getSavePath();
        String folderName = request.isSuccess() ? path + "/success" : path + "/failed";
        File folder = new File(folderName + DELIMITER + request.getAppointmentRequestHour());
        try {
            Files.createDirectories(Paths.get(folderName + DELIMITER + request.getAppointmentRequestHour()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

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