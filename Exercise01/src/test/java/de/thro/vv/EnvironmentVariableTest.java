package de.thro.vv;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EnvironmentVariableTest {
    @SystemStub
    private EnvironmentVariables environment = new EnvironmentVariables();
    EnvironmentVariable env = new EnvironmentVariable();
    static HashMap<String, String> envs = new HashMap<>();

    @BeforeAll
    static void setup(){
        envs.put("START_TIME","1");
        envs.put("END_TIME","10");
        envs.put("MAX_CUSTOMERS_PER_HOUR","3");
        envs.put("SOCKET_PORT","1024");
        envs.put("SAVE_PATH","Out of the dark");
    }
    @Test
    void testStartTime() {

        envs.put("START_TIME","super-duper-wrong");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("START_TIME","-1");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("START_TIME","25");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("START_TIME","10");
        env.setENVS(envs);
        assertEquals(10, env.getStartTime());
    }

    @Test
    void testEndTime() {

        envs.put("END_TIME","wrong");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("END_TIME","-1");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("END_TIME","25");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("END_TIME","10");
        env.setENVS(envs);
        assertEquals(10,env.getEndTime());
    }

    @Test
    void testMaxCustomersPerHour() {
        envs.put("MAX_CUSTOMERS_PER_HOUR","wrong");
        assertThrows(RuntimeException.class, () -> env.setENVS(envs));

        envs.put("MAX_CUSTOMERS_PER_HOUR","-1");
        env.setMaxCustomersPerHour(-1);
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("MAX_CUSTOMERS_PER_HOUR","3");
        env.setENVS(envs);
        assertEquals(3, env.getMaxCustomersPerHour());
    }

    @Test
    void testSocketPort() {
        envs.put("SOCKET_PORT", "wrong");
        assertThrows(RuntimeException.class, () -> env.setENVS(envs));

        envs.put("SOCKET_PORT", "1024");
        env.setENVS(envs);
        assertEquals(1024, env.getSocketPort());
    }

    @Test
    void testReadSavePath() {
        envs.put("SAVE_PATH", "");
        assertDoesNotThrow(() -> env.setENVS(envs));

        envs.put("SAVE_PATH","output/appointments");
        env.setENVS(envs);
        assertEquals("output/appointments", env.getSavePath());
    }

    @Test
    void wrongMaxCustomersPerHour() {
        env.setMaxCustomersPerHour(10);
        assertFalse(env.wrongMaxCustomersPerHour());

        env.setMaxCustomersPerHour(-1);
        assertTrue(env.wrongMaxCustomersPerHour());
    }

    @Test
    void getMaxCustomersPerHour() {
        env.setMaxCustomersPerHour(10);
        assertEquals(10, env.getMaxCustomersPerHour());
    }

    @Test
    void getSocketPort() {
        env.setSocketPort(10);
        assertEquals(10, env.getSocketPort());
    }

    @Test
    void getSavePath() {
        env.setSavePath("appointments");
        assertEquals("appointments",env.getSavePath());
    }

    @Test
    void setStartTime() {
        env.setStartTime(10);
        assertEquals(10, env.getStartTime());
    }

    @Test
    void setEndTime() {
        env.setEndTime(10);
        assertEquals(10, env.getEndTime());
    }

    @Test
    void setMaxCustomersPerHour() {
        env.setMaxCustomersPerHour(10);
        assertEquals(10, env.getMaxCustomersPerHour());
    }

    @Test
    void setSocketPort() {
        env.setSocketPort(10);
        assertEquals(10, env.getSocketPort());
    }

    @Test
    void setSavePath() {
        env.setSavePath("appointments");
        assertEquals("appointments",env.getSavePath());
    }
}

