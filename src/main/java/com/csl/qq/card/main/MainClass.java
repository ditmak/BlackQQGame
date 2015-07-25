package com.csl.qq.card.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.csl.execute.TaskExecutor;
import com.csl.qq.card.task.SimpleCardTask;
import com.csl.qq.card.task.SimpleFarmerHelperTask;
import com.csl.qq.card.task.SimpleFarmerTask;
import com.csl.qq.card.task.SimplePastureTasker;
import com.csl.util.date.Main;

public class MainClass {
    public static Integer times = 0;
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Properties prop = System.getProperties();
        // // 设置http访问要使用的代理服务器的地址
        // prop.setProperty("http.proxyHost", "127.0.0.1");
        // // 设置http访问要使用的代理服务器的端口
        // prop.setProperty("http.proxyPort", "8087");

        File file = new File("cfg.properties");
        System.out.println(file.getAbsolutePath());
        Properties pro = new Properties();
        pro.load(new FileReader(file));
        String users = pro.getProperty("mainUser");
        String user[] = users.split(",");
        for (String u : user) {
            System.out.println("mainuser----" +u);
            TaskExecutor.addScheduledTask(new SimpleFarmerTask(u),5,TimeUnit.MINUTES);
            TaskExecutor.addScheduledTask(new SimpleFarmerHelperTask(u),25,TimeUnit.MINUTES);
            TaskExecutor.addScheduledTask(new SimpleCardTask(u, "393", "和风物语","541563154",false),5,TimeUnit.MINUTES);
            TaskExecutor.addScheduledTask(new SimplePastureTasker(u), 5, TimeUnit.MINUTES);
        }
        String secUserString = pro.getProperty("secUSer");
        String secUsers[] = secUserString.split(",");
        for (String sUser : secUsers) {
            System.out.println("secuser----"+sUser);
            TaskExecutor.addScheduledTask(new SimpleFarmerTask(sUser),5,TimeUnit.MINUTES);
           // TaskExecutor.addScheduledTask(new SimpleCardTask(sUser, "401", "澳洲之旅","1093695691",true),5,TimeUnit.MINUTES);
            TaskExecutor.addScheduledTask(new SimplePastureTasker(sUser), 5, TimeUnit.MINUTES);
        }
        while(true)
        try {
            Main.class.wait();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

   
}
