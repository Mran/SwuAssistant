package com.swuos.ALLFragment.library.search.model;


import com.swuos.util.SALog;

/**
 * Created by youngkaaa on 2016/5/26.
 * Email:  645326280@qq.com
 */
public class BookDetail {
    private String isbn_price="";   //ISBN/价格
    private String language="";  //作品语种
    private String bookName_author="";   //题名责任者项：
    private String publisher="";   //出版发行项
    private String pages="";    //载体形态项
    private String cl_kind="";  //中图分类
    private String personNameMajor="";  //个人名称等同
    private String personNameMinor="";  //个人名称次要
    private String record_source="";  //记录来源


    public String getIsbn_price() {
        return isbn_price;
    }

    public void setIsbn_price(String isbn_price) {
        this.isbn_price = isbn_price;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBookName_author() {
        return bookName_author;
    }

    public void setBookName_author(String bookName_author) {
        this.bookName_author = bookName_author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getCl_kind() {
        return cl_kind;
    }

    public void setCl_kind(String cl_kind) {
        this.cl_kind = cl_kind;
    }

    public String getPersonNameMajor() {
        return personNameMajor;
    }

    public void setPersonNameMajor(String personNameMajor) {
        this.personNameMajor = personNameMajor;
    }

    public String getPersonNameMinor() {
        return personNameMinor;
    }

    public void setPersonNameMinor(String personNameMinor) {
        this.personNameMinor = personNameMinor;
    }

    public String getRecord_source() {
        return record_source;
    }

    public void setRecord_source(String record_source) {
        this.record_source = record_source;
    }

    public void showLogInfo(){
        SALog.d("kklog", "###############################BookDetail toString() start ######################################");
        SALog.d("kklog", "isbn_price =>" + isbn_price);
        SALog.d("kklog", "language =>" + language);
        SALog.d("kklog", "bookName_author =>" + bookName_author);
        SALog.d("kklog", "publisher =>" + publisher);
        SALog.d("kklog", "pages =>" + pages);
        SALog.d("kklog", "cl_kind =>" + cl_kind);
        SALog.d("kklog", "personNameMajor =>" + personNameMajor);
        SALog.d("kklog", "personNameMinor =>" + personNameMinor);
        SALog.d("kklog", "record_source =>" + record_source);
        SALog.d("kklog", "###############################BookDetail toString() end ######################################");
    }

}
