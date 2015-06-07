package com.csl.qq.card.task;

public abstract class BaseTask implements Runnable {
    @Override
    public void run() {
        try {
            doSomeThing();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public abstract void doSomeThing();
}
