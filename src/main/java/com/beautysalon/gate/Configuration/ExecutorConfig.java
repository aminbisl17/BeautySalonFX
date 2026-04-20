package com.beautysalon.gate.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.concurrent.Task;

public class ExecutorConfig {

    public final static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void submit(Task<?> task){
          executor.submit(task);
    }

    public static void close(){
        if (!executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

    public static ExecutorService getExecutor(){
        return executor;
    }

}
