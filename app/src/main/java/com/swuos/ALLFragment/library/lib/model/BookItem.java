package com.swuos.ALLFragment.library.lib.model;

/**
 * Created by youngkaaa on 2016/5/27.
 * Email:  645326280@qq.com
 */
public class BookItem {
    private String bookName;
    private String author;
    private String timeOut;   //书本借出时间
    private String timeBack;  //书本应还日期
    private int status;   //该书是否已经被还了
    public static final int BACKED=1;   //已还
    public static final int UNBACKED=0; //未还

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getTimeBack() {
        return timeBack;
    }

    public void setTimeBack(String timeBack) {
        this.timeBack = timeBack;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
