package com.csl.execute;

import java.util.Date;

public class ExecuteTimeTask extends Task implements
        Comparable<ExecuteTimeTask> {
    private Date date;
    private Runnable task;

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(ExecuteTimeTask o) {
        return date.compareTo(o.getDate());
    }

}
