package com.beautysalon.gate.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.concurrent.Task;

/**
 * Provides the application's shared thread pool for executing
 * background tasks that should not block the JavaFX Application Thread.
 */
public class ExecutorConfig {

    /**
     * Shared executor used for background operations such as
     * network requests and other blocking tasks.
     */
    public static final ExecutorService executor =
            Executors.newFixedThreadPool(2);

    /**
     * Submits a JavaFX task for background execution.
     *
     * @param task the task to execute
     */
    public static void submit(Task<?> task) {
        executor.submit(task);
    }

    /**
     * Shuts down the shared executor and prevents new tasks
     * from being submitted.
     */
    public static void close() {
        executor.shutdown();
    }

    /**
     * Returns the application's shared executor.
     *
     * @return the shared executor service
     */
    public static ExecutorService getExecutor() {
        return executor;
    }
}