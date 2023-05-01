package de.thro.vv;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import static de.thro.vv.EnvironmentVariables.logger;

public class FileArchiveService {
    private final BlockingQueue<AppointmentRequest> queue;
    private static final String PATH = EnvironmentVariables.getSavePath();

    public FileArchiveService(BlockingQueue<AppointmentRequest> queue) {
        this.queue = queue;
    }

    void listen() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                AppointmentRequest request = queue.take();
                logger.info("Following appointment has been taken out of output queue: {}", request);
                // logger.info("Taken appointment: {}", request);
                saveFile(request);
            } catch (InterruptedException e) {
                logger.error("Error: {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    void saveFile(AppointmentRequest request) {
        String folderName = request.isSuccess() ? PATH + "/success" : PATH + "/failed";
        File folder = new File(folderName + "/" + request.getAppointmentRequestHour());
        folder.mkdirs();

        String fileName = request.getCustomerName() + ".json";
        File file = new File(folder.getPath() + "/" + fileName);

        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(request, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}