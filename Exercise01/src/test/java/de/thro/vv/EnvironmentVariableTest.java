package de.thro.vv;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EnvironmentVariableTest {
    static HashMap<String, String> envs = new HashMap<>();
    @SystemStub
    EnvironmentVariable env = new EnvironmentVariable();

    @BeforeAll
    static void setup() {
        envs.put("START_TIME", "1");
        envs.put("END_TIME", "10");
        envs.put("MAX_CUSTOMERS_PER_HOUR", "3");
        envs.put("SOCKET_PORT", "1024");
        envs.put("SAVE_PATH", "Out of the dark");
    }

    @Test
    void testStartTime() {

        envs.put("START_TIME", "super-duper-wrong");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("START_TIME", "-1");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("START_TIME", "25");
        assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("START_TIME", "10");
        env.setENVS(envs);
        Assertions.assertEquals(10, env.getStartTime());
    }

    @Test
    void testEndTime() {

        envs.put("END_TIME", "wrong");
        Assert.assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("END_TIME", "-1");
        Assert.assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("END_TIME", "25");
        Assert.assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("END_TIME", "10");
        env.setENVS(envs);
        Assertions.assertEquals(10, env.getEndTime());
    }

    @Test
    void testMaxCustomersPerHour() {
        envs.put("MAX_CUSTOMERS_PER_HOUR", "wrong");
        Assert.assertThrows(RuntimeException.class, () -> env.setENVS(envs));

        envs.put("MAX_CUSTOMERS_PER_HOUR", "-1");
        env.setMaxCustomersPerHour(-1);
        Assert.assertThrows(IllegalArgumentException.class, () -> env.setENVS(envs));

        envs.put("MAX_CUSTOMERS_PER_HOUR", "3");
        env.setENVS(envs);
        Assertions.assertEquals(3, env.getMaxCustomersPerHour());
    }

    @Test
    void testSocketPort() {
        envs.put("SOCKET_PORT", "wrong");
        Assert.assertThrows(RuntimeException.class, () -> env.setENVS(envs));

        envs.put("SOCKET_PORT", "1024");
        env.setENVS(envs);
        Assertions.assertEquals(1024, env.getSocketPort());
    }

    @Test
    void testReadSavePath() {
        envs.put("SAVE_PATH", "");
        assertDoesNotThrow(() -> env.setENVS(envs));

        envs.put("SAVE_PATH", "output/appointments");
        env.setENVS(envs);
        Assertions.assertEquals("output/appointments", env.getSavePath());
    }

    @Test
    void wrongMaxCustomersPerHour() {
        env.setMaxCustomersPerHour(10);
        Assertions.assertFalse(env.wrongMaxCustomersPerHour());

        env.setMaxCustomersPerHour(-1);
        Assertions.assertTrue(env.wrongMaxCustomersPerHour());
    }

    @Test
    void getMaxCustomersPerHour() {
        env.setMaxCustomersPerHour(10);
        Assertions.assertEquals(10, env.getMaxCustomersPerHour());
    }

    @Test
    void getSocketPort() {
        env.setSocketPort(10);
        Assertions.assertEquals(10, env.getSocketPort());
    }

    @Test
    void getSavePath() {
        env.setSavePath("appointments");
        Assertions.assertEquals("appointments", env.getSavePath());
    }

    @Test
    void setStartTime() {
        env.setStartTime(10);
        Assertions.assertEquals(10, env.getStartTime());
    }

    @Test
    void setEndTime() {
        env.setEndTime(10);
        Assertions.assertEquals(10, env.getEndTime());
    }

    @Test
    void setMaxCustomersPerHour() {
        env.setMaxCustomersPerHour(10);
        Assertions.assertEquals(10, env.getMaxCustomersPerHour());
    }

    @Test
    void setSocketPort() {
        env.setSocketPort(10);
        Assertions.assertEquals(10, env.getSocketPort());
    }

    @Test
    void setSavePath() {
        env.setSavePath("appointments");
        Assertions.assertEquals("appointments", env.getSavePath());
    }
}

