package com.csl.qq.card.main;

import com.csl.execute.TaskExecutor;
import com.csl.qq.card.task.SimpleCardTask;
import com.csl.qq.card.task.SimpleFarmerHelperTask;
import com.csl.qq.card.task.SimpleFarmerTask;
import com.csl.util.date.Main;

public class MainClass {
    public static Integer times = 0;
    public static void main(String[] args) {
        // Properties prop = System.getProperties();
        // // 设置http访问要使用的代理服务器的地址
        // prop.setProperty("http.proxyHost", "127.0.0.1");
        // // 设置http访问要使用的代理服务器的端口
        // prop.setProperty("http.proxyPort", "8087");
        String sid = "AS9AWzaHQVxDUuwj9QRq0beE";
        //SimpleCardTask cardTask = new SimpleCardTask(sid,"185","橡皮泥的记忆");
        SimpleFarmerHelperTask farmerHelperTask = new SimpleFarmerHelperTask(sid);
        SimpleFarmerTask farmerTask = new SimpleFarmerTask(sid);
        TaskExecutor.run();
        //TaskExecutor.addTaskNow(cardTask);
        TaskExecutor.addTaskNow(farmerHelperTask);
        TaskExecutor.addTaskNow(farmerTask);
        while(true)
        try {
            Main.class.wait();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

   
}
