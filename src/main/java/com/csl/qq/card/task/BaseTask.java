package com.csl.qq.card.task;

import com.csl.util.net.HTTPUtil;

public abstract class BaseTask implements Runnable {
    protected String sid;
    protected String taskName="";
    protected String prefix_uri;
    @Override
    public void run() {
        try {
            System.out.println(toString()+"开始");
            doSomeThing();
            System.out.println(toString()+"结束");
        } catch (Exception e) {
            //TODO:发邮件,说明是什么程序挂了
           e.printStackTrace();
           System.out.println("这是什么鬼");
        }
    }

    public abstract void doSomeThing();
    public  String toString(){
        return sid+"-------"+taskName;
    };
    protected String getContent(String uri){
        uri = uri.replaceAll("&amp;", "&");
        if(uri.startsWith("."))
            uri = uri.replace(".", prefix_uri);
        return HTTPUtil.getURLContent(uri);
    }
}
