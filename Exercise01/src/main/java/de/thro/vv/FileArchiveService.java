package de.thro.vv;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class FileArchiveService {
    private static final Logger logger = LoggerFactory.getLogger(FileArchiveService.class);
    private static EnvironmentVariable env = new EnvironmentVariable();
    Map<String, String> envs = System.getenv();

    private final BlockingQueue<AppointmentRequest> queue;

    private static final String PATH = env.getSavePath();
    private static final String DELIMITER = "/";

    public FileArchiveService(BlockingQueue<AppointmentRequest> queue) {
        env.setENVS(envs);
        this.queue = queue;
    }

    void listen() {
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

    void saveFile(AppointmentRequest request) {
        String folderName = request.isSuccess() ? PATH + "/success" : PATH + "/failed";
        File folder = new File(folderName + DELIMITER + request.getAppointmentRequestHour());
        folder.mkdirs();

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