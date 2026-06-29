package com.arenapay.arenapay.service.LoggerPerformance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

public class LogPerformance {
    private static final Logger log = LoggerFactory.getLogger(LogPerformance.class);

    private LogPerformance() {
        throw new UnsupportedOperationException("Classe utilitária: LogPerformance.");
    }

    public static void logPerformanceTwoSeconds(String className, String methodName, long timeTaken, int resultCount) {
        if (timeTaken > 2000) {
            log.warn("PERFORMANCE WARNING: Class: {}, method '{}' took {}ms to respond!", className, methodName, timeTaken);
        } else {
            log.info("Class: {}, Method '{}' finished. Time taken: {}ms. Records found: {}", className, methodName, timeTaken, resultCount);
        }
    }

    public static long calculateTimeTaken(Instant start) {
        return Duration.between(start, Instant.now()).toMillis();
    }
}
