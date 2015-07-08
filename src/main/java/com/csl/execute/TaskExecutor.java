package com.csl.execute;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

public class TaskExecutor {
    private static volatile boolean started=false;
    private static LinkedList<ExecuteTimeTask> taskList = new LinkedList<ExecuteTimeTask>();
    private static ExecutorService pool =  Executors.newFixedThreadPool(4);
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
          //  int i =0;
            @Override
            public void run() {
              /*  if(++i==10){
                    System.out.println("任务池有任务"+taskList.size()+"个");
                    System.out.println("下一个任务时间为"+taskList.peek().getDate());
                    i=0;
                }*/
               ExecuteTimeTask task = taskList.peek();
                if(task!=null&&task.getDate().before(new Date())){
                    taskList.remove(task);
                    try{
                    pool.execute(task.getTask());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
    public static void addTask(ExecuteTimeTask task){
        taskList.add(task);
        Collections.sort(taskList);
    }
    public static void addTask(Runnable task,int time ,TimeUnit unit){
        ExecuteTimeTask etask = new ExecuteTimeTask();
        etask.setTask(task);
        etask.setDate(calaDate(time, unit));
        addTask(etask);
    }
    public static void addTaskNow(Runnable task){
        addTask(task, 1, TimeUnit.SECONDS);
    }
    private static Date calaDate(int time,TimeUnit unit){
       DateTime dtime =  DateTime.now();
        switch(unit){
        case DAYS:
          dtime = dtime.plusDays(time);
          break;
        case HOURS:
            dtime =dtime.plusHours(time);
            break;
        case MINUTES:
            dtime =dtime.plusMinutes(time);
            break;
            
        case SECONDS:
            dtime =dtime.plusSeconds(time);
            break;
         default:
            
        }
        return dtime.toDate();
    }
    
}
