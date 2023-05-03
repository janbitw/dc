package de.thro.vv;

import de.thro.vv.model.AppointmentRequest;
import de.thro.vv.processing_data.FileArchiveService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Check constructor and see if appointment requests are saved into the right directory by counting the files in the directory.
*/
class FileArchiveServiceTest {
    EnvironmentVariable env;
    BlockingQueue<AppointmentRequest> queue;

    @Test
    void constructor() {
        assertDoesNotThrow(() -> new FileArchiveService(env,queue));
    }


    @Test
    void saveFile() throws IOException {
        EnvironmentVariable env = new EnvironmentVariable();
        Map<String, String> envs = System.getenv();
        env.setENVS(envs);
        FileArchiveService fas = new FileArchiveService(env,queue);
        Date currentDate = new Date(1999);
        AppointmentRequest correct = new AppointmentRequest("Jan", 10, currentDate, true);
        AppointmentRequest wrong = new AppointmentRequest("Jan", 10, currentDate, false);
        fas.saveFile(correct);
        fas.saveFile(wrong);

        Path path = Paths.get(env.getSavePath() + "/success/10" + System.getProperty("file.separator"));
        if (Files.exists(path)) {
            try (Stream<Path> files = Files.list(path)) {
                assertEquals(1, files.count());
            }
        }

        Path path2 = Paths.get(env.getSavePath() + "/failed/10" + System.getProperty("file.separator"));
        if (Files.exists(path2)) {
            try (Stream<Path> files = Files.list(path2)) {
                assertEquals(1, files.count());
            }
        }

    }
}