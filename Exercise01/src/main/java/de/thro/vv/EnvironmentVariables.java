package de.thro.vv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class EnvironmentVariables {

    private EnvironmentVariables(){}

    static final String ILLEGAL_ARGUMENT_EXCEPTION = "IllegalArgumentException: {}";
    public static final Logger logger = LoggerFactory.getLogger(EnvironmentVariables.class);
    protected static final Map<String, String> env = getGetenv();

    public static final LocalTime START_TIME = readStartTime();
    public static final LocalTime END_TIME = readEndTime();
    public static final int MAX_CUSTOMERS_PER_HOUR = readMaxCustomersPerHour();
    public static final int SOCKET_PORT = readSocketPort();
    public static final String SAVE_PATH = readSavePath();

    public static LocalTime getStartTime(){
        return START_TIME;
    }

    public static LocalTime getEndTime(){
        return END_TIME;
    }

    public static int getMaxCustomersPerHour(){
        return MAX_CUSTOMERS_PER_HOUR;
    }

    public static int getSocketPort(){
        return SOCKET_PORT;
    }

    public static String getSavePath(){
        return SAVE_PATH;
    }
    // Mockito
    private static Map<String, String> getGetenv() {
        return System.getenv();
    }

    static LocalTime readStartTime(){
        LocalTime startDate = null;
        try {
            String startTime = env.get("START_TIME");
            if (startTime == null) {
                throw new IllegalArgumentException("START_TIME environment variable not set");
            }
            DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm");
            startDate = LocalTime.parse(startTime, format);
            logger.info("START_TIME: {}", startDate);
        }
        catch (DateTimeParseException e) {
            logger.error("ParseException: {}", e.getMessage());
        }

        catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return startDate;
    }

    static LocalTime readEndTime(){
        LocalTime endDate = null;
        try {
            String endTime = env.get("END_TIME");
            if (endTime == null) {
                throw new IllegalArgumentException("END_TIME environment variable not set");
            }
            DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm");
            endDate = LocalTime.parse(endTime, format);
            logger.info("END_TIME: {}", endDate);
        }
        catch (DateTimeParseException e) {
            logger.error("ParseException: {}", e.getMessage());
        }

        catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return endDate;
    }

    static int readMaxCustomersPerHour(){
        int maxCustomers = -1;
        try {
            String maxCustomersPerHour = env.get("MAX_CUSTOMERS_PER_HOUR");
            if (maxCustomersPerHour == null) {
                throw new IllegalArgumentException("MAX_CUSTOMERS_PER_HOUR environment variable not set");
            }
            maxCustomers = Integer.parseInt(maxCustomersPerHour);
            logger.info("MAX_CUSTOMERS_PER_HOUR: {}", maxCustomersPerHour);

        }
        catch (NumberFormatException e) {
            logger.error("NumberFormatException: {}", e.getMessage());
        }

        catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return maxCustomers;
    }

    static int readSocketPort(){
        int socketPortInt = -1;
        try {
            String socketPort = env.get("SOCKET_PORT");
            if (socketPort == null) {
                throw new IllegalArgumentException("SOCKET_PORT environment variable not set");
            }
            socketPortInt = Integer.parseInt(socketPort);
            logger.info("SOCKET_PORT: {}", socketPort);

        }
        catch (NumberFormatException e) {
            logger.error("NumberFormatException: {}", e.getMessage());
        }

        catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return socketPortInt;
    }

    static String readSavePath(){
        String savePath = null;
        try {
            savePath = env.get("SAVE_PATH");
            if (savePath == null) {
                throw new IllegalArgumentException("SAVE_PATH environment variable not set");
            }
            logger.info("SAVE_PATH: {}", savePath);
        }

        catch (IllegalArgumentException e) {
            logger.error(ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        }
        return savePath;
    }
}
