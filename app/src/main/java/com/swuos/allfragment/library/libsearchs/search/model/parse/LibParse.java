package com.swuos.allfragment.library.libsearchs.search.model.parse;


import com.swuos.allfragment.library.libsearchs.search.model.bean.BookStoreInfo;
import com.swuos.allfragment.library.libsearchs.search.model.bean.SearchBookItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张孟尧 on 2016/9/3.
 */
public class LibParse {
    public static List<SearchBookItem> getSearchList(String resulthtml) {
        List<SearchBookItem> searchBookItemList = new ArrayList<>();
        Document document = Jsoup.parse(resulthtml);

        Elements elements = document.getElementsByAttributeValue("bordercolor", "#76BCD6");
        Elements ll = elements.get(0).getElementsByAttributeValueEnding("id", "LabeBookName");
        for (Element pp : ll) {
            SearchBookItem searchBookItem = new SearchBookItem();
            searchBookItem.setBookName(pp.text().replace("》        [点击查看详细信息]", "").replaceAll("《", ""));
            Element ee = pp.parent().parent();
            Elements rr = ee.siblingElements();
            String suoShuHao = rr.get(0).text();
            searchBookItem.setBookSuoshuhao(suoShuHao.substring(0, suoShuHao.indexOf("&nbsp")));
            searchBookItem.setISBN(suoShuHao.substring(suoShuHao.indexOf("ISBN/ISSN：")));
            searchBookItem.setPublisher(rr.get(1).getElementsByAttributeValueEnding("id", "Label1").text());
            searchBookItem.setSummary(rr.get(2).text());
            searchBookItem.setWriter(rr.get(3).text());
            searchBookItem.setBookNumber(rr.get(5).text());
            searchBookItemList.add(searchBookItem);
        }
        return searchBookItemList;
    }

    public static List<BookStoreInfo> getBookDetail(String resulthtml) {
        List<BookStoreInfo> bookStoreInfoList = new ArrayList<>();
        Document documen = Jsoup.parse(resulthtml);
        Elements elements = documen.getElementsByAttributeValue("id", "DataGrid1");
        Elements bro = elements.get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr").get(0).siblingElements();
        for (Element cc : bro) {
            BookStoreInfo bookStoreInfo = new BookStoreInfo();

            Elements dd = cc.getElementsByTag("td");
            bookStoreInfo.setAddress(dd.get(2).text());

            bookStoreInfo.setFrameState(dd.get(3).text());
            bookStoreInfo.setShelf(dd.get(7).getElementsByTag("a").get(0).attr("href"));
            bookStoreInfoList.add(bookStoreInfo);
        }
        return bookStoreInfoList;
    }

    public static String getBookLocation(String locationHtml) {
        String s = locationHtml.substring(locationHtml.indexOf("var strWZxxxxxx = \"") + 20, locationHtml.indexOf("\";"));
        return s;
    }
}
