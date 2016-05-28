package com.swuos.ALLFragment.library.lib.utils;

import com.swuos.ALLFragment.library.lib.model.BookCell;
import com.swuos.ALLFragment.library.lib.model.BookInfo;
import com.swuos.util.SALog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2016/3/11.
 */
public class HtmlParserTools {

    public static List<BookCell> parserHtml(String data, String selector){
        SALog.d("kklog","HtmlParserTools parserHtml==>parserHtml()");
        List<BookCell> books=new ArrayList<>();
        List<String> items = new ArrayList<>();
        Document document = Jsoup.parse(data);
        Elements elements = document.select(selector);
        for (Element item : elements) {
            items.add(item.text());
//            SALog.d("kklog","HtmlParserTools parserHtml==>"+item.text());
        }
        //将数据存入BookCell对象中去
        for(int i=4;i<items.size()-1;i+=4){
            BookCell cell=new BookCell(items.get(i),items.get(i+1),items.get(i+2),items.get(i+3));
            books.add(cell);
        }
        return books;
    }

    public static List<BookCell> parserHtmlNormal(String data, String selector){
        List<String> items = new ArrayList<>();
        List<BookCell> books = new ArrayList<>();
        Document document = Jsoup.parse(data);
        Elements elements = document.select(selector);
        for (Element item : elements) {
            items.add(item.text());
        }
        for(int i=2;i<items.size()-1;i+=2){
            BookCell cell=new BookCell();
            cell.setBookName(items.get(i));
            cell.setTime(items.get(i+1));
            books.add(cell);
        }
        return books;
    }

    public static List<BookInfo> parserHtmlForBookInfo(String data, String selector){
        List<String> items = new ArrayList<>();
        List<BookInfo> bookInfos = new ArrayList<>();
        Document document = Jsoup.parse(data);
        Elements elements = document.select(selector);
        for (Element item : elements) {
            items.add(item.text());
        }
        for(int i=5;i<items.size()-1;i+=5){
            BookInfo info=new BookInfo(items.get(i),items.get(i+1),items.get(i+2),items.get(i+3));
            bookInfos.add(info);
        }
        return bookInfos;
    }
}
