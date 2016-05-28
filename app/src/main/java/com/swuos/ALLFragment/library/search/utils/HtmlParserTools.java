package com.swuos.ALLFragment.library.search.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swuos.ALLFragment.library.lib.model.BookInfo;
import com.swuos.ALLFragment.library.search.model.BookCell;
import com.swuos.ALLFragment.library.search.model.BookDetail;
import com.swuos.ALLFragment.library.search.model.BookInfoSearch;
import com.swuos.ALLFragment.library.search.model.LibHoldInfo;
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

    public static List<BookCell> parserHtml(String data, String selector) {
        List<BookCell> books = new ArrayList<>();
        List<String> items = new ArrayList<>();
        Document document = Jsoup.parse(data);
        Elements elements = document.select(selector);
        for (Element item : elements) {
            items.add(item.text());
        }
        //将数据存入BookCell对象中去
        for (int i = 4; i < items.size() - 1; i += 4) {
            BookCell cell = new BookCell(items.get(i), items.get(i + 1), items.get(i + 2), items.get(i + 3));
            books.add(cell);
        }
        return books;
    }

    public static List<BookCell> parserHtmlNormal(String data, String selector) {
        List<String> items = new ArrayList<>();
        List<BookCell> books = new ArrayList<>();
        Document document = Jsoup.parse(data);
        Elements elements = document.select(selector);
        for (Element item : elements) {
            items.add(item.text());
        }
        for (int i = 2; i < items.size() - 1; i += 2) {
            BookCell cell = new BookCell();
            cell.setBookName(items.get(i));
            cell.setTime(items.get(i + 1));
            books.add(cell);
        }
        return books;
    }

    public static List<BookInfo> parserHtmlForBookInfo(String data, String selector) {
        List<String> items = new ArrayList<>();
        List<BookInfo> bookInfos = new ArrayList<>();
        Document document = Jsoup.parse(data);
        Elements elements = document.select(selector);
        for (Element item : elements) {
            items.add(item.text());
        }
        for (int i = 5; i < items.size() - 1; i += 5) {
            BookInfo info = new BookInfo(items.get(i), items.get(i + 1), items.get(i + 2), items.get(i + 3));
            bookInfos.add(info);
        }
        return bookInfos;
    }

    //只能通过LibTools.getBookInfos()方法来调用，因为此方法会保证传进来的data一定是服务器正常传过来的数据
    //所以在此方法中就不用判断网络错误这些东西了（这些都在上层方法中得到了保证的）
    //所以此方法返回值两种：一种为null 表示没有改查询结果为空，即没有这本书，另一种不为空则表示正常
    public static List<BookInfoSearch> parserHtmlForBookInfoSearch(String data) {
//        SALog.d("kklog", "parserHtmlForBookInfoSearch");
        List<Integer> creatorIndexs = new ArrayList<>();
        List<BookInfoSearch> bookInfoSearches = new ArrayList<>();
        String author = null;
        String callNumber = null;
        String isbn = null;
        String publisher = null;
        String bookKind = null;
        Document document = Jsoup.parse(data);
        //设置图书名和图书id 因为图书名和图书信息是在被不同的tag包起来的，所以要分开处理
        Elements elements1 = document.select("h2");
        Elements elements = document.select("p");
        //先根据 作者 （即creator属性）来将每本书的属性分开，因为有些书并没有标准编码之类的属性，即每组书籍的数据都是不规则的
        //但是每组书籍的数据中都有 creator这个属性，所以根据这个属性来将其分开，然后再 分开解析
        for (int i = 0; i < elements.size() - 2; i++) {
//            SALog.d("kklog", "element.text(" + i + ")==>" + elements.get(i).text());
            String s = elements.get(i).className();
            if (s.equals("creator")) {
                creatorIndexs.add(i);
//                SALog.d("kklog", "element.classname(" + i + ")==>" + s);
            }
        }
//        for (int i : creatorIndexs) {
//            SALog.d("kklog", "i===>" + i);
//        }
        int j = 0;
//        SALog.d("kklog", "creatorIndexs.size()==>" + creatorIndexs.size());
        if (creatorIndexs.size() != 0) {   //如果传进来的是正确的数据的话，则此时 size（）不会为空
            creatorIndexs.add(elements.size() - 3);
            //使用 i、j 两个变量共同控制可以将两层for循环变成一层for循环，增加效率
            for (int i = creatorIndexs.get(j); j + 1 < creatorIndexs.size(); i++) {
                if (i >= creatorIndexs.get(j + 1)) {
//                    SALog.d("kklog", "################################################################");
                    BookInfoSearch bookInfoSearch = new BookInfoSearch();
                    bookInfoSearch.setAuthor(author);
                    bookInfoSearch.setCallNumber(callNumber);
                    bookInfoSearch.setIsbn(isbn);
                    bookInfoSearch.setPublisher(publisher);
                    bookInfoSearch.setBookKind(bookKind);
                    bookInfoSearches.add(bookInfoSearch);
                    //每个循环执行完，将其初始化，免得上一次的数据影响下一次的数据（因为有些数据不完整）
                    author = " ";
                    callNumber = " ";
                    isbn = " ";
                    publisher = " ";
                    bookKind = " ";
                    j++;
                    i = creatorIndexs.get(j);
                }
                switch (elements.get(i).className()) {
                    case "creator":
                        author = elements.get(i).text();
//                        SALog.d("kklog", "element.classname(" + i + ") createor ==>" + elements.get(i).text());
                        break;
                    case "call_number":
                        callNumber = elements.get(i).text();
//                        SALog.d("kklog", "element.classname(" + i + ") call_number ==>" + elements.get(i).text());
                        break;
                    case "isbn":
                        isbn = elements.get(i).text();
//                        SALog.d("kklog", "element.classname(" + i + ") isbn ==>" + elements.get(i).text());
                        break;
                    case "publisher":
                        publisher = elements.get(i).text();
//                        SALog.d("kklog", "element.classname(" + i + ") publisher ==>" + elements.get(i).text());
                        break;
                    case "libraryCount":
                        bookKind = elements.get(i).text();
//                        SALog.d("kklog", "element.classname(" + i + ") libraryCount ==>" + elements.get(i).text());
                        break;

                }
            }
            //来设置图书名和图书编号
            for (int i = 0; i < elements1.size(); i++) {
//                SALog.d("qqlog", "element.text(" + i + ")==>" + elements1.get(i).text());
                String html = elements1.get(i).html();
//                SALog.d("qqlog", "element.html(" + i + ")==>" + elements1.get(i).html());
                int endIndex = html.indexOf("target");
                String bookId = html.substring(20, endIndex - 2);
                String bookName = elements1.get(i).text();
//                SALog.d("qqlog", "id" + bookId);
                bookInfoSearches.get(i).setBookName(bookName);
                bookInfoSearches.get(i).setBookId(bookId);
            }
        } else {   //有可能是查询结果为空.
//            SALog.d("kklog", "!!!!!!!!!!!!parserHtmlForBookInfoSearch  creatorIndexs.size()==0!!!!!!!!!!!!!!!!");
            return null;
        }
        return bookInfoSearches;
    }

    public static BookDetail parserHtmlForBookDetail(String data) {
        Document document = Jsoup.parse(data);
        Elements elements1 = document.select("tr");
        BookDetail detail = new BookDetail();
        for (int i = 0; i < elements1.size(); i++) {
            //因为数据有可能不全 所以应该是有什么数据就显示什么数据
            if (elements1.get(i).html().contains("价格")) {
                detail.setIsbn_price(elements1.get(i).text());
            } else if (elements1.get(i).html().contains("作品语种")) {
                detail.setLanguage(elements1.get(i).text());
            } else if (elements1.get(i).html().contains("题名责任者项")) {
                detail.setBookName_author(elements1.get(i).text());
            } else if (elements1.get(i).html().contains("出版发行项")) {
                detail.setPublisher(elements1.get(i).text());
            } else if (elements1.get(i).html().contains("载体形态项")) {
                detail.setPages(elements1.get(i).text());
            } else if (elements1.get(i).html().contains("个人名称等同")) {
                detail.setPersonNameMajor(elements1.get(i).text());
            } else if (elements1.get(i).html().contains("中图分类")) {
                detail.setCl_kind(elements1.get(i).text());
            } else if (elements1.get(i).html().contains("个人名称次要")) {
                detail.setPersonNameMinor(elements1.get(i).text());
            } else if (elements1.get(i).html().contains("记录来源")) {
                detail.setRecord_source(elements1.get(i).text());
            }
        }

        detail.showLogInfo();

        return detail;
    }

    public static List<LibHoldInfo> parserJsonForLibHoldInfo(String data) {
        String dataTemp = replaceJson(data);
        SALog.d("kklog", "dataTemp==>" + dataTemp);
        Gson gson = new Gson();
        List<LibHoldInfo> libHoldInfos = gson.fromJson(dataTemp, new TypeToken<List<LibHoldInfo>>() {
        }.getType());
        if (libHoldInfos == null || libHoldInfos.isEmpty()) {
            return new ArrayList<LibHoldInfo>();   //返回为empty的表示解析数据失败
        }
        SALog.d("kklog", "libHoldInfos.size()==》" + libHoldInfos.size());
        for (LibHoldInfo info : libHoldInfos) {
            SALog.d("kklog", "libHoldInfo.getDeptName()==>" + info.getDeptName());
            SALog.d("kklog", "libHoldInfo.getindustryTitle()==>" + info.getindustryTitle());
        }
        return libHoldInfos;
    }

    private static String replaceJson(String data) {
        String s1;
        s1 = data.replace("单位名", "deptName");
        data = s1.replace("索书号", "isbn");
        s1 = data.replace("年卷期", "volumePeriod");
        data = s1.replace("外借状态", "outState");
        s1 = data.replace("条形码", "barCode");
        data = s1.replace("虚拟库室", "virtualLibraryRoom");
        s1 = data.replace("登录号", "accessionNumber");
        data = s1.replace("馆藏地址", "collectionAddress");
        s1 = data.replace("区分号", "distinguishingNumber");
        data = s1.replace("刊价", "issuePrice");
        s1 = data.replace("部门名称", "industryTitle");
        return s1;
    }

    public static String parserCollectAddress(String data) {
        SALog.d("kklog", "parserCollectAddress data.len==>" + data.length());
        int startIndex1 = data.indexOf("|") + 1;
        if (startIndex1 > 1200) {   //因为返回数据中只有两种可能：要么是 非自助借还  要么是 正常的馆藏位置
            return "非自助借还(RFID)图书，无法定位！";
        } else {
            int endIndex1 = data.indexOf("strMsg") - 18;
            if (endIndex1 < 0) {
                return "查询失败，请重试!";
            } else {
                SALog.d("kklog", "parserCollectAddress startIndex1==>" + startIndex1);
                SALog.d("kklog", "parserCollectAddress endIndex1==>" + endIndex1);
                String strContent = data.substring(startIndex1, endIndex1);
                SALog.d("kklog", "parserCollectAddress strContent==>" + strContent);
                return strContent;
            }
        }
    }
}
