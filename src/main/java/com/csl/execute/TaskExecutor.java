package com.csl.execute;

import java.util.Date;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

public class TaskExecutor {
    private static volatile boolean started = false;
    private static ConcurrentSkipListSet<ExecuteTimeTask> taskList = new ConcurrentSkipListSet<ExecuteTimeTask>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void run() {
        if (started) {
            return;
        }
        synchronized (TaskExecutor.class) {
            if (started) {
                return;
            }
            started = true;
        }
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ExecuteTimeTask task = taskList.first();
                            if (task != null
                                    && task.getDate().before(new Date())) {
                                taskList.remove(task);
                                pool.execute(task.getTask());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 1, 1, TimeUnit.SECONDS);
    }

    public static synchronized void addTask(ExecuteTimeTask task) {
        // 多线程调用，不建议使用sort();
        taskList.add(task);
    }

    public static void addTask(Runnable task, int time, TimeUnit unit) {
        ExecuteTimeTask etask = new ExecuteTimeTask();
        etask.setTask(task);
        etask.setDate(calaDate(time, unit));
        addTask(etask);
    }

    public static void addTaskNow(Runnable task) {
        addTask(task, 1, TimeUnit.SECONDS);
    }

    private static Date calaDate(int time, TimeUnit unit) {
        DateTime dtime = DateTime.now();
        switch (unit) {
        case DAYS:
            dtime = dtime.plusDays(time);
            break;
        case HOURS:
            dtime = dtime.plusHours(time);
            break;
        case MINUTES:
            dtime = dtime.plusMinutes(time);
            break;
        case SECONDS:
            dtime = dtime.plusSeconds(time);
            break;
        default:

        }
        return dtime.toDate();
    }

}
