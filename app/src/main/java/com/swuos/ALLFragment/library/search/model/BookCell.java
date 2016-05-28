package com.swuos.ALLFragment.library.search.model;

/**
 * Created by ASUS on 2016/3/11.
 */
public class BookCell{
    private String bookName;
    private String press;
    private String author;
    private String time;

    //设置为空格是为了更好地储存用户信息。因为用户信息页只是类似于键值对。而再去创建一个类专门存储用户信息的键值对
    //的话就显得多余。所以当储存用户信息键值对时，只使用BookCell的bookName属性和time属性（因为这两者在item布局上面刚好在一横列上）
    //设置为空格的话就不用去修改RecyclerViewAdapter中的逻辑了。直接给其他两个变量显示为空格
    public BookCell(){
        this.bookName=" ";
        this.press=" ";
        this.author=" ";
        this.time=" ";
    }

    public BookCell(String bookName, String press, String author, String time){
        this.bookName=bookName;
        this.press=press;
        this.author=author;
        this.time=time;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
