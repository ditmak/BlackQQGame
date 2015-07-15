package com.csl.qq.card.task;

public abstract class BaseTask implements Runnable {
    protected String sid;
    @Override
    public void run() {
        try {
            doSomeThing();
        } catch (Exception e) {
            //TODO:发邮件,说明是什么程序挂了
           e.printStackTrace();
           System.out.println("这是什么鬼");
        }
    }

    public abstract void doSomeThing();
}
