package com.csl.qq.card.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.csl.execute.TaskExecutor;
import com.csl.qq.card.task.SimpleCardTask;
import com.csl.qq.card.task.SimpleFarmerHelperTask;
import com.csl.qq.card.task.SimpleFarmerTask;
import com.csl.qq.card.task.SimplePastureTasker;
import com.csl.util.date.Main;
import com.csl.util.io.ByteIOUtils;

public class MainClass {
    public static Integer times = 0;

    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        File file = new File("cfg.properties");
                        List<String> strs = null;
                        try {
                           strs = ByteIOUtils.getStrFromFile(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return ;
                        }
                        for (String str : strs) {
                            String[] args = str.split(",");
                            if (args.length < 6) {
                                System.out.println("参数个数不够");
                                continue;
                            }
                            System.out.println(args[5]
                                            .equals("true"));
                            String sid = args[0];
                            TaskExecutor.addTaskNow(new SimpleFarmerTask(sid));
                            TaskExecutor.addTaskNow(new SimplePastureTasker(sid));
                            if (args[1].equals("true")) {
                                TaskExecutor
                                        .addTaskNow(new SimpleFarmerHelperTask(
                                                sid));
                            }
                            
                            TaskExecutor.addTaskNow(new SimpleCardTask(sid,
                                    args[2], args[3], args[4], args[5]
                                            .equals("true"),args[6]==null?1:Integer.parseInt(args[6])));
                        }
                    }
                }, 0, 5, TimeUnit.MINUTES);
        while (true)
            try {
                Main.class.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }
    }

}
