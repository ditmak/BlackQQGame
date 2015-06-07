package com.csl.execute;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {
    private static volatile boolean started=false;
    private static LinkedList<ExecuteTimeTask> taskList = new LinkedList<ExecuteTimeTask>();
    private static ExecutorService pool =  Executors.newFixedThreadPool(8);
    public static void run(){
        if(started){
            return ;
        }
        synchronized (TaskExecutor.class) {
            if(started){
                return;
            }
            started=true;
        }
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
               ExecuteTimeTask task = taskList.peek();
                if(task!=null&&task.getDate().after(new Date())){
                    taskList.remove(task);
                    pool.execute(task.getTask());
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
    public static void addTask(ExecuteTimeTask task){
        taskList.add(task);
    }
    
}
