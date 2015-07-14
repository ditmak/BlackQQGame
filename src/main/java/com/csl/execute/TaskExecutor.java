package com.csl.execute;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {
    private static ScheduledExecutorService  pool = Executors.newScheduledThreadPool(8);
    public static void addTask(Runnable task, int time, TimeUnit unit) {
        pool.schedule(task, time, unit);
    }

    public static void addTaskNow(Runnable task) {
        addTask(task, 1, TimeUnit.SECONDS);
    }
}
