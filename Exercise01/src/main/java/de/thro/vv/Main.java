package de.thro.vv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // Read environment variables
        Map<String, String> env = System.getenv();
        String startTime = env.get("START_TIME");
        String endTime = env.get("END_TIME");
        String maxCustomersPerHour = env.get("MAX_CUSTOMERS_PER_HOUR");
        String socketPort = env.get("SOCKET_PORT");
        String savePath = env.get("SAVE_PATH");

        // Check if all environment variables are set
        try {
            if (startTime == null) {
                throw new IllegalArgumentException("START_TIME environment variable not set");
            }
            if (endTime == null) {
                throw new IllegalArgumentException("END_TIME environment variable not set");
            }
            if (maxCustomersPerHour == null) {
                throw new IllegalArgumentException("MAX_CUSTOMERS_PER_HOUR environment variable not set");
            }
            if (socketPort == null) {
                throw new IllegalArgumentException("SOCKET_PORT environment variable not set");
            }
            if (savePath == null) {
                throw new IllegalArgumentException("SAVE_PATH environment variable not set");
            }

            // Parse String into the types int and Date
            int maxCustomers = Integer.parseInt(maxCustomersPerHour);
            int port = Integer.parseInt(socketPort);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);



            // Show all the inputted and parsed information
            logger.info("START_TIME: {}", startDate);
            logger.info("END_TIME: {}", endDate);
            logger.info("MAX_CUSTOMERS_PER_HOUR: {}", maxCustomers);
            logger.info("SOCKET_PORT: {}", port);
            logger.info("SAVE_PATH: {}", savePath);

        }

        catch (NumberFormatException e) {
            logger.error("NumberFormatException: {}", e.getMessage());
        }

        catch (ParseException e) {
            logger.error("ParseException: {}", e.getMessage());
        }

        catch (IllegalArgumentException e) {
            logger.error("IllegalArgumentException: {}", e.getMessage());
        }

    }
}
