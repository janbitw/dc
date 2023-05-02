package de.thro.vv;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    List<ILoggingEvent> logsList;
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new Main());
    }

    @BeforeEach
    void setup(){
        Logger mainLogger = (Logger) LoggerFactory.getLogger(Main.class);

        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        mainLogger.addAppender(listAppender);

        logsList = listAppender.list;
    }

    @AfterEach
    void clear() {logsList.clear();}

    @Test
    void testLogBasicDetails(){
        Main m = new Main();
        m.logBasicDetails();
        assertTrue(logsList.get(1).getMessage().startsWith("JAVA_HOME: "));
        assertTrue(logsList.get(2).getMessage().startsWith("Operating System: "));
        assertTrue(logsList.get(3).getMessage().startsWith("Version: "));
        assertTrue(logsList.get(4).getMessage().startsWith("User: "));

    }

}