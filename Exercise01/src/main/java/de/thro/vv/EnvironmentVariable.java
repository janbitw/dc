package de.thro.vv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Getter;
import lombok.Setter;


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


    public void setENVS(){
        try{
            startTime = Integer.parseInt(getenv("START_TIME"));
        }
        catch (NumberFormatException e){
            logger.error("Wrong format for START_TIME");
            throw new IllegalArgumentException();
        }
        if (wrongStartTime()) throw new IllegalArgumentException();


        try{
            endTime = Integer.parseInt(getenv("END_TIME"));
        }
        catch (NumberFormatException e){
            logger.error("Wrong format for END_TIME");
            throw new IllegalArgumentException();
        }
        if (wrongEndTime()) throw new IllegalArgumentException();


        try{
            maxCustomersPerHour = Integer.parseInt(getenv("MAX_CUSTOMERS_PER_HOUR"));
        }
        catch (NumberFormatException e){
            logger.error("Wrong format for MAX_CUSTOMERS_PER_HOUR");
            throw new IllegalArgumentException();
        }
        if (wrongMaxCustomersPerHour()) throw new IllegalArgumentException();


        try{
            socketPort = Integer.parseInt(getenv("SOCKET_PORT"));
        }
        catch (NumberFormatException e){
            logger.error("Wrong format for SOCKET_PORT");
            throw new IllegalArgumentException();
        }


        try{
            savePath = getenv("SAVE_PATH");
        }
        catch (IllegalArgumentException e){
            logger.error("Illegal Argument for SAVE_PATH");
            throw new IllegalArgumentException();
        }
    }

    public boolean wrongStartTime(){
        if (0 <= startTime && startTime <= 23){
            return false;
        }
        logger.error("START_TIME is out of range");
        return true;
    }

    public boolean wrongEndTime(){
        if (0 <= endTime && endTime <= 23){
            return false;
        }
        logger.error("END_TIME is out of range");
        return true;
    }

    public boolean wrongMaxCustomersPerHour(){
        if (maxCustomersPerHour >= 0){
            return false;
        }
        logger.error("MAX_CUSTOMERS_PER_HOUR needs to be positive");
        return true;
    }

    public String getenv(String key){
        return System.getenv(key);
    }
}
