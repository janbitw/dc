package de.thro.vv;

import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.event.LoggingEvent;

import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    List<LoggingEvent> logsList;
    BlockingQueue<AppointmentRequest> inputQueue = new ArrayBlockingQueue<>(1);
    ExecutorService executor = Executors.newFixedThreadPool(1);
    Server server = new Server(1024, executor, inputQueue);

    @Test
    void constructor() {
        assertDoesNotThrow(() -> server);
        assertDoesNotThrow(() -> server);
    }

//    @BeforeEach
//    void setup(){
//        Logger mainLogger = (Logger) LoggerFactory.getLogger(Server.class);
//
//        ListAppender<LoggingEvent> listAppender = new ListAppender<>();
//        listAppender.start();
//
//        logsList = listAppender.list;
//    }
//
//    @AfterEach
//    void clear() {logsList.clear();}
//
//    @Test
//    void testLogs(){
//        //  server.logBasicDetails();
//        Server server = new Server(1024, executor, inputQueue);
//        assertTrue(logsList.get(1).getMessage().startsWith("Waiting for the next connection..."));
//
//    }

}