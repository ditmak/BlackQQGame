package com.csl.qq.card.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.csl.qq.card.domain.Card;
import com.csl.qq.card.domain.Formula;
import com.csl.qq.card.domain.Operator;
import com.csl.qq.card.domain.Theme;
import com.csl.qq.card.service.CardService;
import com.csl.qq.card.service.FormulaService;
import com.csl.qq.card.service.OperatorService;
import com.csl.qq.card.service.ThemeService;
import com.csl.util.io.ByteIOUtils;

public class MainClass {
    private static ApplicationContext context = new ClassPathXmlApplicationContext(
            "spring/application-context.xml");
    public static Integer times = 0;

    public static void main(String[] args) {
        // Properties prop = System.getProperties();
        // // 设置http访问要使用的代理服务器的地址
        // prop.setProperty("http.proxyHost", "127.0.0.1");
        // // 设置http访问要使用的代理服务器的端口
        // prop.setProperty("http.proxyPort", "8087");
        // getCurrentInfo();
        cala(230);
      /*  for (int i = 1; i < 6; i++)
            for (int j = 1;; j++) {
                int count = getThemesInfo(i, j);
                if (count < 1)
                    break;
            }*/
       // getCardInfo();
      //  getSuperCardInfo();
        //getSuperCardID();
        // getCurrentInfo();

    }

    public static void getCurrentInfo() {
        CardService cardService = (CardService) context.getBean("cardService");
        String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_b?sid=AS9AWzaHQVxDUuwj9QRq0beE&g_f=19011";
        String data = getUrlContent(url);
        Pattern pattern = Pattern.compile("card=(\\d+).*?slot=(\\d+)",
                Pattern.DOTALL);
        Matcher matcher = pattern.matcher(data);
        Card[] cards = new Card[16];
        while (matcher.find()) {
            String id = matcher.group(1);
            int slot = Integer.parseInt(matcher.group(2));
            Card card = cardService.findCardByID(id);
            cards[slot] = card;
        }
        for (Card card : cards) {
            if (card != null)
                System.out.println(card.getName());
        }
    }

    public static void getSuperCardID() {
        ThemeService service = (ThemeService) context.getBean("themeService");
        CardService cardService = (CardService) context.getBean("cardService");
        List<Theme> list = service.getALLTheme();
        Pattern pattern = Pattern.compile("\\d\\. (.*?)\\[.*?;id=(\\d+)",
                Pattern.DOTALL);
        for (Theme theme : list) {
            String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_strategy?sid=AS9AWzaHQVxDUuwj9QRq0beE&fuin=0&steal=0&themeid="
                    + theme.getThemeID();
            String content = getUrlContent(url);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                System.out.println(matcher.group(1));
                System.out.print(matcher.group(2));
                System.out.println(theme.getId());
                Card card = cardService.findCardByName(matcher.group(1),
                        theme.getId());
                card.setCardID(Integer.parseInt(matcher.group(2)));
                cardService.updateCard(card);

            }
        }

    }

    public static void cala(int themeId) {
        FormulaService formulaService = (FormulaService) context
                .getBean("formulaService");
        List<Formula> formulas = formulaService.getFormulaByThemeId(themeId);
        HashMap<String, Formula> mapf = new HashMap<String, Formula>();
        HashMap<String, Integer> maps = new HashMap<String, Integer>();
        for (Formula formula : formulas) {
            mapf.put(formula.getTarget(), formula);
            maps.put(formula.getTarget(), 0);
            maps.put(formula.getSource1(), 0);
            maps.put(formula.getSource2(), 0);
            maps.put(formula.getSource3(), 0);
        }
        Set<String> keys = maps.keySet();
        for (String key : keys) {
            int num = maps.get(key);
            if (num < 1) {
                add(mapf, maps, key);
            }
        }
        System.out.println(maps);
        System.out.println(times / 3600.0);
    }

    private static void add(HashMap<String, Formula> mapf,
            HashMap<String, Integer> maps, String key) {
        Formula f = mapf.get(key);
        if (f == null) {
            maps.put(key, maps.get(key) + 1);
        } else {
            add(mapf, maps, f.getSource1());
            add(mapf, maps, f.getSource2());
            add(mapf, maps, f.getSource3());
            times += f.getCost();
        }
    }

    public static void getSuperCardInfo() {
        // 1.把所有的主题拿出来
        ThemeService service = (ThemeService) context.getBean("themeService");
        CardService cardService = (CardService) context.getBean("cardService");
        FormulaService formulaService = (FormulaService) context
                .getBean("formulaService");
        List<Theme> list = service.getALLTheme();
        Pattern pattern = Pattern
                .compile(
                        "\\d+\\. (.*?):.*?=(.*?)\\[(\\d+)\\].*?\\+(.*?)\\[(\\d+)\\].*?\\+(.*?)\\[(\\d+)\\]",
                        Pattern.DOTALL);
        Pattern costPattern = Pattern.compile("耗时(\\d+):(\\d+):(\\d+)");
        Matcher matcher;
        for (Theme theme : list) {
            // 2.拼凑连接
            String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_theme_info?sid=AS9AWzaHQVxDUuwj9QRq0beE&themeid="
                    + theme.getThemeID();
            List<Element> elems = getATagListByURL(url);
            for (Element e : elems) {
                String data = e.attributeValue("href");
                if (data.startsWith("http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_theme_info")) {
                    String stringPrice = data.substring(
                            data.indexOf("price=") + 6, data.lastIndexOf("&"));
                    Integer price = Integer.parseInt(stringPrice);
                    if (price >= 40) {
                        String newUrl = url + "&price=" + stringPrice;
                        String content = getUrlContent(newUrl);
                        Matcher costMatcher = costPattern.matcher(content);
                        Integer cost = 0;
                        if (costMatcher.find()) {
                            String hours = costMatcher.group(1);
                            String minutes = costMatcher.group(2);
                            String seconds = costMatcher.group(3);
                            cost = Integer.parseInt(hours) * 3600
                                    + Integer.parseInt(minutes) * 60
                                    + Integer.parseInt(seconds);
                        }
                        matcher = pattern.matcher(content);
                        while (matcher.find()) {
                            Formula formula = new Formula();
                            formula.setCost(cost);
                            formula.setTheme(theme);
                            formula.setTarget(matcher.group(1));
                            formula.setSource1(matcher.group(2));
                            formula.setSource2(matcher.group(4));
                            formula.setSource3(matcher.group(6));
                            formulaService.saveFormula(formula);
                            Card card = new Card();
                            card.setName(matcher.group(1));
                            card.setPrice(price);
                            card.setTheme(theme);
                            cardService.saveCard(card);

                        }
                    }
                }
            }
        }
        // 3.发起请求，取得里面的价格信息
        // 4.取得里面的合成公式
        // 5.保存合成信息和卡片信息

    }

    // http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_theme_info?sid=AfYnBGlvxgtzr4um2cL44G-f&price=10&themeid=185
    public static void getCardInfo() {
        ThemeService service = (ThemeService) context.getBean("themeService");
        List<Theme> list = service.getALLTheme();
        CardService carddService = (CardService) context.getBean("cardService");
        for (Theme t : list) {
            String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_theme_info?sid=AS9AWzaHQVxDUuwj9QRq0beE&price=10&themeid="
                    + t.getThemeID();
            String content = getUrlContent(url);
            Pattern pattern = Pattern.compile(
                    "[1-9]\\. (.*?): 已有\\d+张.*?cardid=(\\d+)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                Card card = new Card();
                card.setCardID(Integer.parseInt(matcher.group(2)));
                card.setName(matcher.group(1));
                card.setTheme(t);
                card.setPrice(10);
                carddService.saveCard(card);
            }

        }
    }

    public static Integer getThemesInfo(Integer level, Integer pageno) {
        String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_theme_list?sid=AS9AWzaHQVxDUuwj9QRq0beE&fuin=1&steal=0&refine=0&pageno="
                + pageno + "&level=" + level;
        // http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_mainpage?sid=ARltUZg17dy3aN6LSTvOWXBZ&g_f=19011
        // http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_theme_info?sid=AfYnBGlvxgtzr4um2cL44G-f&amp;themeid=185
        List<Element> alist = getATagListByURL(url);
        ThemeService service = (ThemeService) context.getBean("themeService");
        int i = 0;
        for (Element a : alist) {
            String data = a.attributeValue("href");
            if (data.startsWith("http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_theme_info")) {
                Theme t = new Theme();
                t.setThemeID(Integer.parseInt(data.substring(97)));
                t.setLevel(level);
                t.setName(a.getTextTrim());
                service.saveTheme(t);
                i++;
            }
        }
        return i;

    }

    /*
     * http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_theme_list?
     * sid=AfYnBGlvxgtzr4um2cL44G-f& level=1& fuin=0& steal=0& refine=0&
     * pageno=1
     * 
     * http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_theme_list?sid=
     * AfYnBGlvxgtzr4um2cL44G-f&level=1&fuin=1&steal=1&refine=0&pageno=1
     */
    // "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_mainpage?sid=AfYnBGlvxgtzr4um2cL44G-f&g_f=19011"
    public static void init() throws Exception {
        String data = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_mainpage?sid=AfYnBGlvxgtzr4um2cL44G-f&g_f=19011";
        OperatorService serivce = (OperatorService) context
                .getBean("operatorService");
        Map<String, String> urlMap = new HashMap<String, String>();
        getNewUrl(data, urlMap);
        Operator operator;
        for (Map.Entry<String, String> a : urlMap.entrySet()) {
            operator = new Operator(a.getKey(), a.getValue());
            serivce.saveOperator(operator);
        }
    }

    public static void getNewUrl(String url, Map<String, String> urlMap)
            throws Exception {
        List<Element> list = getATagListByURL(url);
        for (Element e : list) {
            String data = e.attributeValue("href");

            if (data.startsWith("http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card")) {
                String newURL = data.substring(0, data.indexOf("?"));
                if (!urlMap.containsKey(newURL)) {
                    System.out.println(newURL);
                    urlMap.put(newURL, e.getTextTrim());
                    getNewUrl(data, urlMap);
                }

            }
        }
    }

    private static List<Element> getATagListByURL(String url) {
        try {
            URL temp = new URL(url);
            System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) temp
                    .openConnection();
            connection.connect();
            InputStream ips = connection.getInputStream();
            byte[] buf = ByteIOUtils.getInputSreamBytes(ips);
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            SAXReader reader = new SAXReader();
            reader.setEntityResolver(new EntityResolver() {

                @Override
                public InputSource resolveEntity(String publicId,
                        String systemId) throws SAXException, IOException {
                    return new InputSource(new StringBufferInputStream(""));

                }
            });
            Document doc = reader.read(bais);
            List<Element> list = doc.selectNodes("//a");
            return list;
        } catch (Exception e) {
            System.out.println(e + url);
            return getATagListByURL(url);
        }

    }

    private static String getUrlContent(String url) {
        try {
            URL temp = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) temp
                    .openConnection();
            connection.connect();
            InputStream ips = connection.getInputStream();
            byte[] buf = ByteIOUtils.getInputSreamBytes(ips);
            return new String(buf, "UTF-8");
        } catch (Exception e) {
            System.out.println(e + url);
            return getUrlContent(url);
        }
    }
}
