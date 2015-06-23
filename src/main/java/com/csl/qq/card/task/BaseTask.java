package com.csl.qq.card.task;

public abstract class BaseTask implements Runnable {
    protected String sid;
    @Override
    public void run() {
        try {
            doSomeThing();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public abstract void doSomeThing();
}
