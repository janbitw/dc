package de.thro.vv;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Read Environment Variables from the system that are used for the processing_data classes.
 */
public class EnvironmentVariable {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentVariable.class);

    @Getter
    @Setter
    private int startTime = -1;
    @Getter
    @Setter
    private int endTime = -1;
    @Getter
    @Setter
    private int maxCustomersPerHour = -1;
    @Getter
    @Setter
    private int socketPort = -1;
    @Getter
    @Setter
    private String savePath;

    /**
     * This method reads the environment variables from the system and checks
     * whether these are correct by calling other functions.
     * @param getEnv is used to get and set environment variables.
     * @throws IllegalArgumentException is thrown by every environment variable that is set accordingly.
     */
    public void setENVS(Map<String, String> getEnv) throws IllegalArgumentException {
        try {
            startTime = Integer.parseInt(getEnv.get("START_TIME"));
        } catch (NumberFormatException e) {
            logger.error("Wrong format for START_TIME");
            throw new IllegalArgumentException();
        }
        if (wrongStartTime()) throw new IllegalArgumentException();


        try {
            endTime = Integer.parseInt(getEnv.get("END_TIME"));
        } catch (NumberFormatException e) {
            logger.error("Wrong format for END_TIME");
            throw new IllegalArgumentException();
        }
        if (wrongEndTime()) throw new IllegalArgumentException();


        try {
            maxCustomersPerHour = Integer.parseInt(getEnv.get("MAX_CUSTOMERS_PER_HOUR"));
        } catch (NumberFormatException e) {
            logger.error("Wrong format for MAX_CUSTOMERS_PER_HOUR");
            throw new IllegalArgumentException();
        }
        if (wrongMaxCustomersPerHour()) throw new IllegalArgumentException();


        try {
            socketPort = Integer.parseInt(getEnv.get("SOCKET_PORT"));
        } catch (NumberFormatException e) {
            logger.error("Wrong format for SOCKET_PORT");
            throw new IllegalArgumentException();
        }

        savePath = getEnv.get("SAVE_PATH");
    }

    /**
     * Check whether StartTime is invalid.
     * @return If startTime is wrong, return true.
     *         If startTime is correct, return false.
     */
    public boolean wrongStartTime() {
        if (0 <= startTime && startTime <= 23) {
            return false;
        }
        logger.error("START_TIME is out of range");
        return true;
    }

    /**
     * Check whether endTime is invalid.
     * @return If endTime is wrong, return true.
     *         If endTime is correct, return false.
     */
    public boolean wrongEndTime() {
        if (0 <= endTime && endTime <= 23) {
            return false;
        }
        logger.error("END_TIME is out of range");
        return true;
    }

    /**
     * Check if MaxCustomersPerHour is invalid.
     * @return MaxCustomersPerHour returns false, if number is valid.
     *         MaxCustomersPerHour returns true, if number is invalid.
     */
    public boolean wrongMaxCustomersPerHour() {
        if (maxCustomersPerHour >= 0) {
            return false;
        }
        logger.error("MAX_CUSTOMERS_PER_HOUR needs to be positive");
        return true;
    }
}
