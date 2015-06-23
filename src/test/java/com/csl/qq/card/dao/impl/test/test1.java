package com.csl.qq.card.dao.impl.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.util.Properties;

import org.junit.Test;

import com.csl.util.io.ByteIOUtils;
import com.csl.util.net.HTTPUtil;

public class test1 {

    @Test
    public void testURLContext() {
        String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_sell?sid=AS9AWzaHQVxDUuwj9QRq0beE&card=2663&slot=9";
        String data = HTTPUtil.getURLContent(url, null, "GET");
        System.out.println(data);
    }

    public void test() throws MalformedURLException, IOException,
            InterruptedException {
        Properties prop = System.getProperties();
        prop.setProperty("http.proxyHost", "127.0.01");
        prop.setProperty("http.proxyPort", "8087");
        String url = "http://pp.1024pp.com/register.php?";

        String pre = "65f829";
        String aft = "53fa0af3f";
        for (int i = 0; i < 10; i++) {
            String code = pre + i + aft;
            String reurl = url + "reginvcode=" + code + "&action=reginvcodeck";
            URLConnection conn = HTTPUtil.getConn(reurl, null, "GET");
            conn.connect();
            InputStream ips = conn.getInputStream();
            byte[] buf = ByteIOUtils.getInputSreamBytes(ips);
            System.out.println(new String(buf) + "------------" + code);
            Thread.sleep(2000);
        }
        for (char i = 'a'; i <= 'z'; i++) {
            String code = pre + i + aft;
            String reurl = url + "reginvcode=" + code + "&action=reginvcodeck";
            URLConnection conn = HTTPUtil.getConn(reurl, null, "GET");
            conn.connect();
            InputStream ips = conn.getInputStream();
            byte[] buf = ByteIOUtils.getInputSreamBytes(ips);
            System.out.println(new String(buf) + "------------" + code);
            Thread.sleep(2000);
        }
    }
}
