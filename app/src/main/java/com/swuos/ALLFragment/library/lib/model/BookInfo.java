package com.swuos.ALLFragment.library.lib.model;

/**
 * Created by ASUS on 2016/3/12.
 */
public class BookInfo {
    private String bookName;
    private String renewTimes;
    private String dateOut;
    private String dateBack;

    public BookInfo(String bookName, String dateOut, String dateBack, String renewTimes){
        this.bookName=bookName;
        this.renewTimes=renewTimes;
        this.dateBack=dateBack;
        this.dateOut=dateOut;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getRenewTimes() {
        return renewTimes;
    }

    public void setRenewTimes(String renewTimes) {
        this.renewTimes = renewTimes;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getDateBack() {
        return dateBack;
    }

    public void setDateBack(String dateBack) {
        this.dateBack = dateBack;
    }
}
