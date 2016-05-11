package com.swuos.ALLFragment.card;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codekk on 2016/5/3.
 * Email:  645326280@qq.com
 */
public class ParserTools {
    public static List<EcardInfo> parserHtmlToEcardInfos(String data, String selector){
        List<String> items = new ArrayList<>();
        List<EcardInfo> ecardInfos = new ArrayList<>();
        Document document = Jsoup.parse(data);
        Elements elements = document.select(selector);
        for (Element item : elements) {
            items.add(item.text());
        }
        for(int i=11;i<items.size()-3;i+=2){
            EcardInfo info=new EcardInfo(items.get(i),items.get(i+1));
            ecardInfos.add(info);
        }
        return ecardInfos;
    }

    public static List<ConsumeInfo> parserHtmlToConsumeInfos(String data, String selector){
        List<String> items = new ArrayList<>();
        List<ConsumeInfo> consumeInfos = new ArrayList<>();
        Document document = Jsoup.parse(data);
        Elements elements = document.select(selector);
        for (Element item : elements) {
            items.add(item.text());
        }
        for(int i=items.size()-6;i>=27;i-=7){
            ConsumeInfo info=new ConsumeInfo();
            info.setTime(items.get(i-6));
            info.setKind(items.get(i-5));
            info.setTimes(items.get(i-4));
            info.setBefore(items.get(i-3));
            info.setDelta(items.get(i-2));
            info.setAfter(items.get(i-1));
            info.setAddress(items.get(i));
            consumeInfos.add(info);
        }
        return consumeInfos;
    }
}
