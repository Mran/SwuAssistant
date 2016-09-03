package com.swuos.allfragment.library.libsearchs.search.model.bean;

/**
 * Created by 张孟尧 on 2016/9/3.
 */
public class SearchBookItem {
    //    书名
    private String bookName;
    //    索书号
    private String bookSuoshuhao;
    private String ISBN;
    //    出版社
    private String publisher;
    //    摘要
    private String summary;
    //    作者
    private String writer;
    //    馆藏数
    private String bookNumber;
    //    可借数
    private String lendableNumber;
    //    馆藏地址
    private String address;
    //    在架
    private String frame;
    //    架位
    private String shelf;

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
public String getAllInfo()
{
    return String.format("%s\n%s\n%s\n%s\n",bookName,bookSuoshuhao,ISBN,publisher);
}

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookSuoshuhao() {
        return bookSuoshuhao;
    }

    public void setBookSuoshuhao(String bookSuoshuhao) {
        this.bookSuoshuhao = bookSuoshuhao;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getLendableNumber() {
        return lendableNumber;
    }

    public void setLendableNumber(String lendableNumber) {
        this.lendableNumber = lendableNumber;
    }
}
