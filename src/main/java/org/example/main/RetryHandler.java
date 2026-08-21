package org.example.main;

import java.util.concurrent.Callable;

public class RetryHandler {
    private static final int MAX_RETRIES = 3;

    public <T> T withRetry(Callable<T> operation) {
        int attempts = 0;
        Exception lastException = null;
        
        while (attempts < MAX_RETRIES) {
            try {
                if (attempts > 0) {
                    Thread.sleep(1000L * attempts); // Exponential backoff
                }
                return operation.call();
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(interruptedException);
            } catch (Exception e) {
                lastException = e;
                attempts++;
                System.err.println("Attempt " + attempts + " failed: " + e.getMessage());
            }
        }
        
        throw new RuntimeException("Operation failed after " + MAX_RETRIES + " attempts", lastException);
    }
}
