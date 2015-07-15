package com.csl.execute;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.csl.qq.card.task.BaseTask;

public class TaskExecutor {
    private static ScheduledThreadPoolExecutor  executor = new ScheduledThreadPoolExecutor(8){
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("目前有任务---" +getTaskSize());
        };
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("目前有任务---" +getTaskSize());
        };
    };
    public static void addTask(Runnable task, int time, TimeUnit unit) {
        executor.schedule(task, time, unit);
    }
    public static void addScheduledTask(Runnable task ,int time, TimeUnit unit){
        executor.scheduleWithFixedDelay(task, 0, time, unit);
    }

    public static void addTaskNow(Runnable task) {
        addTask(task, 1, TimeUnit.SECONDS);
    }
    public static int getTaskSize(){
        return executor.getQueue().size();
    }
    public static BlockingQueue<Runnable> getTasks(){
        return executor.getQueue();
    }
}
