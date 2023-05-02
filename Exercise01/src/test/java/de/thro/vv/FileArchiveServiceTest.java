package de.thro.vv;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class FileArchiveServiceTest {
    BlockingQueue<AppointmentRequest> queue;

    @Test
    void constructor() {
        assertDoesNotThrow(() -> new FileArchiveService(queue));
    }


    @Test
    void saveFile() {
        EnvironmentVariable env = new EnvironmentVariable();
        Map<String, String> envs = System.getenv();
        env.setENVS(envs);
        FileArchiveService fas = new FileArchiveService(queue);
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
            catch (IOException e){}
        }

        Path path2 = Paths.get(env.getSavePath() + "/failed/10" + System.getProperty("file.separator"));
        if (Files.exists(path2)) {
            try (Stream<Path> files = Files.list(path2)) {
                assertEquals(1, files.count());
            }
            catch (IOException e){}
        }

    }
}