package com.swuos.ALLFragment.library.search.utils;


import com.swuos.ALLFragment.library.search.model.BookDetail;
import com.swuos.ALLFragment.library.search.model.BookInfoSearch;
import com.swuos.ALLFragment.library.search.model.LibHoldInfo;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngkaaa on 2016/5/25.
 * Email:  645326280@qq.com
 */
public class LibTools {
    private LibSearch libSearch;

    public LibTools() {
        libSearch = new LibSearch();
    }

    //返回null时表示网络不好，获取数据失败了
    //返回List<BookInfoSearch> 并且 其size 为0 时表示搜索内容为空 即未查询到相应书籍信息
    //返回List<BookInfoSearch> 并且 其size不为0时表示一切正常
    public List<BookInfoSearch> getBookInfos(String bookName, int pageIndex) {
        String s = libSearch.bookSearchMore(bookName, 0);
        if (checkResponseDataVaild(s)) {
            SALog.d("kklog", "LibTools 数据获取成功");
            List<BookInfoSearch> bookInfoSearches = HtmlParserTools.parserHtmlForBookInfoSearch(s);
            if (bookInfoSearches == null) {
                bookInfoSearches = new ArrayList<>();
                SALog.d("kklog", "LibTools bookInfoSearches==NULL and then Size=>" + bookInfoSearches.size());
            }
            return bookInfoSearches;
        } else {   //表示并没有从服务器获得到信息数据，有可能是网络原因
            SALog.d("kklog", "LibTools 表示并没有从服务器获得到信息数据，有可能是网络原因");
            return null;
        }
    }

    //用来检查服务器是否正常返回了数据给我们而不是出错信息
    private boolean checkResponseDataVaild(String data) {
        if (data.equals(Constant.CLIENT_ERROR) || data.equals(Constant.NO_NET) || data.equals(Constant.CLIENT_TIMEOUT)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkResponseDataVaild1(String data) {
        if (data.equals(Constant.CLIENT_TIMEOUT)) {
            return false;
        } else {
            return true;
        }
    }

    public BookDetail getBookDetails(String bookId) {
        BookDetail bookDetail = null;
        String s = libSearch.bookDetail(bookId);
        SALog.d("kklog", "bookDetail s==>" + s);
        if (checkResponseDataVaild(s)) {
            SALog.d("kklog", "LibTools getBookDetails 数据获取成功");
            bookDetail = HtmlParserTools.parserHtmlForBookDetail(s);
        } else {
            SALog.d("kklog", "LibTools getBookDetails 表示并没有从服务器获得到信息数据，有可能是网络原因");
            return null;
        }
        return bookDetail;
    }

    public List<LibHoldInfo> getLibHoldInfos(String bookId) {
        String s = libSearch.holdingsInformation(bookId);
        SALog.d("kklog", "getLibHoldInfos s==>" + s);
        if (checkResponseDataVaild(s)) {
            SALog.d("kklog", "LibTools getLibHoldInfos 数据获取成功");
            List<LibHoldInfo> libHoldInfos = HtmlParserTools.parserJsonForLibHoldInfo(s);
            return libHoldInfos;
        } else {
            SALog.d("kklog", "LibTools getLibHoldInfos 表示并没有从服务器获得到信息数据，有可能是网络原因");
            return null;
        }
    }

    public String getCollectAddress(String barCode) {
        String result = null;
        SALog.d("kklog", "LibTools getCollectAddress barCode==>" + barCode);
        if (barCode.isEmpty() || barCode.equals("")) {
            SALog.d("kklog", "LibTools getCollectAddress barCode equals \"\" ");
            result = "非自助借还(RFID)图书，无法定位";
        } else {
            String s = libSearch.getCollectAddress(barCode);
            SALog.d("kklog", "LibTools getCollectAddress s==>" + s);
            if(checkResponseDataVaild1(s)){
               result = HtmlParserTools.parserCollectAddress(s);
            }else{
                result="图书馆现在很忙，请稍候再试吧 ╮(╯_╰)╭ ";
            }
            SALog.d("kklog", "getCollectAddress result==>" + result);
        }
        return result;
    }
}
