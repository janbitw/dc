package de.thro.vv;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EnvironmentVariableTest {
    @SystemStub
    private EnvironmentVariables environment = new EnvironmentVariables();
    EnvironmentVariable env = new EnvironmentVariable();

    @Test
    void testSetENVS(){
        env.setStartTime(1);
        assertEquals(1, env.getStartTime());
        env.setEndTime(2);
        assertEquals(2, env.getEndTime());
        env.setMaxCustomersPerHour(3);
        assertEquals(3, env.getMaxCustomersPerHour());
        env.setSocketPort(4);
        assertEquals(4, env.getSocketPort());
        env.setSavePath("5");
        assertEquals("5", env.getSavePath());
    }

    @Test
    void testStartTime() {
        environment.set("START_TIME", "wrong");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS());

        env.setStartTime(-1);
        assertThrows(IllegalArgumentException.class, () -> env.setENVS());

        env.setStartTime(25);
        assertThrows(IllegalArgumentException.class, () -> env.setENVS());

        env.setStartTime(10);
        assertEquals(10, env.getStartTime());
    }

    @Test
    void testEndTime() {
        environment.set("END_TIME", "wrong");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS());

        env.setEndTime(-1);
        assertThrows(IllegalArgumentException.class, () -> env.setENVS());

        env.setEndTime(25);
        assertThrows(IllegalArgumentException.class, () -> env.setENVS());

        env.setEndTime(10);
        assertEquals(10,env.getEndTime());
    }

    @Test
    void testMaxCustomersPerHour() {
        environment.set("MAX_CUSTOMERS_PER_HOUR", "wrong");
        assertThrows(RuntimeException.class, () -> env.setENVS());

        env.setMaxCustomersPerHour(-1);
        assertThrows(IllegalArgumentException.class, () -> env.setENVS());

        env.setMaxCustomersPerHour(3);
        assertEquals(3, env.getMaxCustomersPerHour());
    }

    @Test
    void testSocketPort() {
        environment.set("SOCKET_PORT", "wrong");
        assertThrows(RuntimeException.class, () -> env.setENVS());

        env.setSocketPort(1024);
        assertEquals(1024, env.getSocketPort());
    }

    @Test
    void testReadSavePath() {
        environment.set("SAVE_PATH", "");
        assertThrows(RuntimeException.class, () -> env.setENVS());

        env.setSavePath("output/appointments");
        assertEquals("output/appointments", env.getSavePath());
    }

    @Test
    void wrongStartTime() {
        env.setStartTime(10);
        assertFalse(env.wrongStartTime());

        env.setStartTime(-1);
        assertTrue(env.wrongStartTime());

        env.setStartTime(30);
        assertTrue(env.wrongStartTime());


        env.setStartTime(0);
        env.setEndTime(20);
        env.setSocketPort(1024);
        env.setMaxCustomersPerHour(3);
        env.setSavePath("appointments");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS());

        env.setStartTime(20);
        assertDoesNotThrow(() -> env.setENVS());
    }

    @Test
    void wrongEndTime() {
        env.setEndTime(10);
        assertFalse(env.wrongEndTime());

        env.setEndTime(-1);
        assertTrue(env.wrongEndTime());

        env.setEndTime(30);
        assertTrue(env.wrongEndTime());

        env.setEndTime(0);
        assertThrows(IllegalArgumentException.class, () -> env.setENVS());

        env.setEndTime(20);
        assertDoesNotThrow(() -> env.setENVS());
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

