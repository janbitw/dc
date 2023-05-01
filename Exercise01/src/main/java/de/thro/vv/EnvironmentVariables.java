package de.thro.vv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeParseException;
import java.util.Map;

public class EnvironmentVariables {
    public static final Logger logger = LoggerFactory.getLogger(EnvironmentVariables.class);
    static final Map<String, String> env = getGetenv();
    private static final String ILLEGAL_ARGUMENT_EXCEPTION = "IllegalArgumentException: {}";
    public static final int START_TIME = readStartTime();
    public static final int END_TIME = readEndTime();
    public static final int MAX_CUSTOMERS_PER_HOUR = readMaxCustomersPerHour();
    public static final int SOCKET_PORT = readSocketPort();
    public static final String SAVE_PATH = readSavePath();
    private EnvironmentVariables() {

    }

    // Mockito
    private static Map<String, String> getGetenv() {
        return System.getenv();
    }

    public static int getStartTime() {
        return START_TIME;
    }

    public static int getEndTime() {
        return END_TIME;
    }

    public static int getMaxCustomersPerHour() {
        return MAX_CUSTOMERS_PER_HOUR;
    }

    public static int getSocketPort() {
        return SOCKET_PORT;
    }

    public static String getSavePath() {
        return SAVE_PATH;
    }

    public static int readStartTime() {
        int startDate = -1;
        try {
            String startTime = env.get("START_TIME");
            if (startTime == null) {
                throw new IllegalArgumentException("START_TIME environment variable not set");
            }
            startDate = Integer.parseInt(startTime);
            logger.info("START_TIME: {}", startDate);
        } catch (NumberFormatException e) {
            logger.error("ParseException: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return startDate;
    }

    public static int readEndTime() {
        int endDate = -1;
        try {
            String endTime = env.get("START_TIME");
            if (endTime == null) {
                throw new IllegalArgumentException("START_TIME environment variable not set");
            }
            endDate = Integer.parseInt(endTime);
            logger.info("START_TIME: {}", endDate);
        } catch (DateTimeParseException e) {
            logger.error("ParseException: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return endDate;
    }

    // Warum nicht public?
    static int readMaxCustomersPerHour() {
        int maxCustomers = -1;
        try {
            String maxCustomersPerHour = env.get("MAX_CUSTOMERS_PER_HOUR");
            if (maxCustomersPerHour == null) {
                throw new IllegalArgumentException("MAX_CUSTOMERS_PER_HOUR environment variable not set");
            }
            maxCustomers = Integer.parseInt(maxCustomersPerHour);
            logger.info("MAX_CUSTOMERS_PER_HOUR: {}", maxCustomersPerHour);

        } catch (NumberFormatException e) {
            logger.error("NumberFormatException: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return maxCustomers;
    }

    public static int readSocketPort() {
        int socketPortInt = -1;
        try {
            String socketPort = env.get("SOCKET_PORT");
            if (socketPort == null) {
                throw new IllegalArgumentException("SOCKET_PORT environment variable not set");
            }
            socketPortInt = Integer.parseInt(socketPort);
            logger.info("SOCKET_PORT: {}", socketPort);

        } catch (NumberFormatException e) {
            logger.error("NumberFormatException: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return socketPortInt;
    }

    public static String readSavePath() {
        String savePath = null;
        try {
            savePath = env.get("SAVE_PATH");
            if (savePath == null) {
                throw new IllegalArgumentException("SAVE_PATH environment variable not set");
            }
            logger.info("SAVE_PATH: {}", savePath);
        } catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return savePath;
    }
}
