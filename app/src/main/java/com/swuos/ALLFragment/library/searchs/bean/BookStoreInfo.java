package com.swuos.ALLFragment.library.searchs.bean;

/**
 * Created by 张孟尧 on 2016/9/4.
 */
public class BookStoreInfo {

    //    馆藏地址
    private String address;
    //    在架
    private String frameState;

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    //    架位
    private String shelf;



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFrameState() {
        return frameState;
    }

    public void setFrameState(String frameState) {
        this.frameState = frameState;
    }
}
