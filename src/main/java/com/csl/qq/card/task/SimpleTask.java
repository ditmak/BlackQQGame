package com.csl.qq.card.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csl.manager.ServiceManager;
import com.csl.qq.card.domain.Card;
import com.csl.qq.card.domain.Formula;
import com.csl.qq.card.domain.Theme;
import com.csl.qq.card.service.CardService;
import com.csl.qq.card.service.FormulaService;
import com.csl.qq.card.service.ThemeService;
import com.csl.util.net.HTTPUtil;

public class SimpleTask extends BaseTask {
    private int themeid;
    private static Pattern exBox = Pattern.compile(
            "\\d\\. (.*?)\\-(.*?)\\[(\\d+)\\].*?card=(\\d+).*?slot=(\\d+)\">",
            Pattern.DOTALL);
    private static Pattern safeBox = Pattern
            .compile(
                    "\\d\\. (.*?)\\-(.*?)\\[(\\d+)\\].*?slot=(\\d+).*?card=(\\d+).*?\">",
                    Pattern.DOTALL);

    public SimpleTask(String sid, int themeid) {
        this.sid = sid;
        this.themeid = themeid;
    }

    @Override
    public void doSomeThing() {
        Card[] cardsInBox = new Card[60];
        Card[] cards = new Card[20];
        getSafeCard(cardsInBox);
        getExCard(cards);
        List<Card> list = new ArrayList<Card>();
        for(Card card:cards){
            if(card!=null)
            list.add(card);
        }
        for (Card card : cardsInBox) {
            if(card!=null)
            list.add(card);
        }
        Card card = getNextCard(themeid, list);
        System.out.println(card.getName());
        
    }

    private Card getNextCard(int themeId, List<Card> cards) {
        FormulaService formulaService = ServiceManager
                .getService(ServiceManager.FORMULASERVICE);
        CardService cardService = ServiceManager
                .getService(ServiceManager.CARDSERVICE);
        // 这里的list里面的时间应该从小到大
        List<Formula> formulas = formulaService.getFormulaByThemeId(themeId);
        Integer time = formulaService.getShortestTime(themeId);
        System.out.println(time);
        for (Formula formula : formulas) {
            Card card = cardService.findCardByName(formula.getTarget(), themeId);
            if (listContain(cards, card.getId(), themeId)) {
                continue;
            }
            Card card1 = cardService.findCardByName(formula.getSource1(), themeId);
            Card card2 = cardService.findCardByName(formula.getSource2(), themeId);
            Card card3 = cardService.findCardByName(formula.getSource3(), themeId);
            if ((card1.getPrice()==10||listContain(cards, card1.getId(), themeId))
                    && (card2.getPrice()==10||listContain(cards, card2.getId(), themeId))
                    &&(card3.getPrice()==10|| listContain(cards,card3.getId(), themeId))) {
                return card;
            }
        }
        return null;
    }

    private boolean listContain(List<Card> cards, int id, int themeId) {
        for (Card card : cards) {
           if(card.getId()==id)
               return true;
        }
        return false;
    }

    private void getExCard(Card[] cards) {
        String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid="
                + sid;
        String data = HTTPUtil.getURLContent(url, null, "GET");
        Pattern pattern = exBox;
        Matcher matcher = pattern.matcher(data);
        CardService cardService = ServiceManager
                .getService(ServiceManager.CARDSERVICE);
        ThemeService themeService = ServiceManager
                .getService(ServiceManager.THEMESERVICE);
        while (matcher.find()) {
            String id = matcher.group(4);
            int slot = Integer.parseInt(matcher.group(5));
            Card card = cardService.findCardByID(id);
            if (card == null) {
                Theme theme = themeService.findByName(matcher.group(1));
                if (theme == null) {
                    theme = new Theme();
                    theme.setName(matcher.group(1));
                    themeService.saveTheme(theme);
                    card = new Card();
                    card.setCardID(Integer.parseInt(id));
                    card.setName(matcher.group(2));
                    card.setTheme(theme);
                    card.setPrice(Integer.parseInt(matcher.group(3)));
                    cardService.saveCard(card);
                } else {
                    card = cardService.findCardByName(matcher.group(2),
                            theme.getId());
                    if (card == null) {
                        card = new Card();
                        card.setCardID(Integer.parseInt(id));
                        card.setName(matcher.group(2));
                        card.setTheme(theme);
                        card.setPrice(Integer.parseInt(matcher.group(3)));
                        cardService.saveCard(card);
                    } else {
                        card.setCardID(Integer.parseInt(id));
                        cardService.updateCard(card);
                    }
                }
            }
            cards[slot] = card;
        }
    }

    private void getSafeCard(Card[] cards) {
        String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?t=1&sid="
                + sid;
        String data = HTTPUtil.getURLContent(url, null, "GET");
        Pattern pattern = safeBox;
        Matcher matcher = pattern.matcher(data);
        CardService cardService = ServiceManager
                .getService(ServiceManager.CARDSERVICE);
        ThemeService themeService = ServiceManager
                .getService(ServiceManager.THEMESERVICE);
        while (matcher.find()) {
            String id = matcher.group(5);
            int slot = Integer.parseInt(matcher.group(4));
            Card card = cardService.findCardByID(id);
            if (card == null) {
                Theme theme = themeService.findByName(matcher.group(1));
                if (theme == null) {
                    theme = new Theme();
                    theme.setName(matcher.group(1));
                    themeService.saveTheme(theme);
                    card = new Card();
                    card.setCardID(Integer.parseInt(id));
                    card.setName(matcher.group(2));
                    card.setTheme(theme);
                    card.setPrice(Integer.parseInt(matcher.group(3)));
                    cardService.saveCard(card);
                } else {
                    card = cardService.findCardByName(matcher.group(2),
                            theme.getId());
                    if (card == null) {
                        card = new Card();
                        card.setCardID(Integer.parseInt(id));
                        card.setName(matcher.group(2));
                        card.setTheme(theme);
                        card.setPrice(Integer.parseInt(matcher.group(3)));
                        cardService.saveCard(card);
                    } else {
                        card.setCardID(Integer.parseInt(id));
                        cardService.updateCard(card);
                    }
                }
            }
            cards[slot] = card;
        }
    }
}
