package com.csl.qq.card.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csl.manager.ServiceManager;
import com.csl.qq.card.domain.Card;
import com.csl.qq.card.service.CardService;
import com.csl.util.net.HTTPUtil;

/**
 * 
 * @author ditamrk
 * @Time   2015年6月7日下午7:55:31
 * 刷新个人主页数据
 */
public class FlushBoxCardInof extends BaseTask {
    private String sid;
   private static Pattern pattern = Pattern.compile("\\d (.*?)\\[(\\d+)\\].*?card=(\\d+).*?slot=(\\d+)\">出售</a>",Pattern.DOTALL);
    public FlushBoxCardInof(String sid) {
        this.sid = sid;
    }
    @Override
    public void doSomeThing() {
        String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid="+sid;
        String data = HTTPUtil.getURLContent(url, null, "GET");
        Matcher matcher = pattern.matcher(data);
        CardService cardService = ServiceManager.getService(ServiceManager.CARDSERVICE);
        while(matcher.find()){
            String id = matcher.group(3);
            int slot = Integer.parseInt(matcher.group(4));
        
            Card card = cardService.findCardByID(id);
            if(card ==null){
                
                card.setCardID(Integer.parseInt(id));
                card.setName(matcher.group());
                cardService.saveCard(card);
            }
            
            System.out.println(id +"--------"+slot);
        }
    }
}
