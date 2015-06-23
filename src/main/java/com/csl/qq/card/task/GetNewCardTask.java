package com.csl.qq.card.task;

import com.csl.util.net.HTTPUtil;

public class GetNewCardTask extends BaseTask{
    public GetNewCardTask(String sid) {
        this.sid = sid;
    }
    @Override
    public void doSomeThing() {
       String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_random?sid="+sid; 
       HTTPUtil.getURLContent(url, null, "GET");
    }
}
