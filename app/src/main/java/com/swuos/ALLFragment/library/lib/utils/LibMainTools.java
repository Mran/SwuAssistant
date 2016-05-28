package com.swuos.ALLFragment.library.lib.utils;


import com.swuos.ALLFragment.library.lib.model.BookCell;
import com.swuos.ALLFragment.library.lib.model.BookItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by youngkaaa on 2016/5/27.
 * Email:  645326280@qq.com
 */
public class LibMainTools {
    private RequestBody requestBody;

    public LibMainTools(String name, String passwd) {
        GetMyLibraryInfo.Init();
        requestBody = new FormBody.Builder()
                .add("password", passwd)
                .add("username", name)
                .add("loginsubmit", "登录")
                .add("backUrl", "")
                .add("formhash", "2966b2fd")
                .add("refer", "spce.jsp?do=home")
                .build();
    }

    //在获取借阅历史等信息时，必须是在本操作执行成功之后才行，否则返回的数据中会包括 未登录 ：
    public boolean getUserInfo() {
        GetMyLibraryInfo.libraryLogin(requestBody);
        String history = GetMyLibraryInfo.ToMyBookShelf();
        if (history != "nothing") {
            List<BookCell> userInfo = HtmlParserTools.parserHtmlNormal(history, "td");
            if (userInfo == null || userInfo.isEmpty()) {
                return false;   //表示获得个人信息失败，后面的而操作不能进行
            } else {
                return true;
            }
        }
        return false;
    }

    public List<BookItem> getBookHistory() {
        List<BookItem> bookItems = new ArrayList<>();
        List<BookItem> bookItems1=new ArrayList<>();
        List<BookCell> bookCells;
        List<BookCell> bookCells1 = new ArrayList<>();
        GetMyLibraryInfo.libraryLogin(requestBody);
        String history = GetMyLibraryInfo.getMyBorrowHistory();
        if (history != "nothing") {
            bookCells = HtmlParserTools.parserHtml(history, "td");
            int j = 0;
            BookItem item = new BookItem();
            item.setBookName(bookCells.get(bookCells.size() - 1).getBookName());
            item.setAuthor(bookCells.get(bookCells.size() - 1).getAuthor());
            item.setTimeOut(bookCells.get(bookCells.size() - 1).getTime());
            bookItems.add(item);
            //将BookCell 转换为 BookItem
            for (int i = bookCells.size() - 2; i >= 0; i--) {
                if (bookItems.get(j).getBookName().equals(bookCells.get(i).getBookName())) {
                    bookItems.get(j).setTimeBack(bookCells.get(i).getTime());
                } else {
                    BookItem item1 = new BookItem();
                    item1.setBookName(bookCells.get(i).getBookName());
                    item1.setAuthor(bookCells.get(i).getAuthor());
                    item1.setTimeOut(bookCells.get(i).getTime());
                    bookItems.add(item1);
                    ++j;
                }
            }
            //初始化 status
            for (BookItem item1 : bookItems) {
                if (item1.getTimeBack() == null) {
                    item1.setTimeBack("Now");
                    item1.setStatus(BookItem.UNBACKED);
                } else {
                    item1.setStatus(BookItem.BACKED);
                }
            }

            for(int i=bookItems.size()-1;i>=0;i--){
                bookItems1.add(bookItems.get(i));
            }

        }
        return bookItems1;
    }


}
