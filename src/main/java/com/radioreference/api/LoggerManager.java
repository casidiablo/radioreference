package com.radioreference.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cristian
 */
public class LoggerManager {

    private static final List<Logger> loggers = new ArrayList<Logger>();

    /**
     * Add a logger to the api wrapper
     *
     * @param logger
     */
    public static void addLogger(Logger logger) {
        if (!loggers.contains(logger)) {
            loggers.add(logger);
        }
    }

    static void log(String msg) {
        if (loggers.isEmpty()) {
            System.out.println(msg);
        } else {
            for (Logger logger : loggers) {
                logger.log(msg);
            }
        }
    }

    public interface Logger {
        void log(String msg);
    }
}
