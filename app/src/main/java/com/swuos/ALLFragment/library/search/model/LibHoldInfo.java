package com.swuos.ALLFragment.library.search.model;

import java.io.Serializable;

/**
 * Created by youngkaaa on 2016/5/26.
 * Email:  645326280@qq.com
 */
public class LibHoldInfo implements Serializable{
    private static final long serialVersionUID = 6826503247279416307L;
    /**
     * 虚拟库室
     */
    private int virtualLibraryRoom;
    /**
     * 刊价
     */
    private float issuePrice;
    /**
     * 是否可以借出,比如杂志就不能借出,exp: borrowstatus=流通部（可借）
     */
    private String borrowstatus;
    /**
     * 区分号
     */
    private String distinguishingNumber;
    /**
     * 索书号
     */
    private String isbn;
    /**
     * 外借状态,已借出就是1,未借出就是0
     */
    private String outState;
    /**
     * 未借出就是"在架",已借出就是"222014303240042 借于：2016-05-22 <br/>应还时间：2016-07-21"
     */
    private String bookstatus;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 年卷期,ps:不知道什么意思
     */
    private String volumePeriod;
    /**
     * 馆藏地址 一个数字
     */
    private String collectionAddress;
    /**
     * 登录号,ps:不知道什么意思
     */
    private String accessionNumber;
    /**
     * 单位名
     */
    private String deptName;
    /**
     * 不知道是意思
     */
    private boolean canExtracted;
    /**
     * 部门名称 exp:部门名称=(图书)中心图书馆
     */
    private String industryTitle;

    public int getVirtualLibraryRoom() {
        return this.virtualLibraryRoom;
    }

    public void setVirtualLibraryRoom(int VirtualLibraryRoom) {
        this.virtualLibraryRoom = VirtualLibraryRoom;
    }

    public float getIssuePrice() {
        return this.issuePrice;
    }

    public void setIssuePrice(float IssuePrice) {
        this.issuePrice = IssuePrice;
    }

    public String getBorrowstatus() {
        return this.borrowstatus;
    }

    public void setBorrowstatus(String borrowstatus) {
        this.borrowstatus = borrowstatus;
    }

    public String getdistinguishingNumber() {
        return this.distinguishingNumber;
    }

    public void setdistinguishingNumber(String distinguishingNumber) {
        this.distinguishingNumber = distinguishingNumber;
    }

    public String getisbn() {
        return this.isbn;
    }

    public void setisbn(String isbn) {
        this.isbn = isbn;
    }

    public String getoutState() {
        return this.outState;
    }

    public void setoutState(String outState) {
        this.outState = outState;
    }

    public String getBookstatus() {
        return this.bookstatus;
    }

    public void setBookstatus(String bookstatus) {
        this.bookstatus = bookstatus;
    }

    public String getbarCode() {
        return this.barCode;
    }

    public void setbarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getvolumePeriod() {
        return this.volumePeriod;
    }

    public void setvolumePeriod(String volumePeriod) {
        this.volumePeriod = volumePeriod;
    }

    public String getcollectionAddress() {
        return this.collectionAddress;
    }

    public void setcollectionAddress(String collectionAddress) {
        this.collectionAddress = collectionAddress;
    }

    public String getaccessionNumber() {
        return this.accessionNumber;
    }

    public void setaccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptname) {
        this.deptName = deptname;
    }

    public boolean getCanExtracted() {
        return this.canExtracted;
    }

    public void setCanExtracted(boolean canExtracted) {
        this.canExtracted = canExtracted;
    }

    public String getindustryTitle() {
        return this.industryTitle;
    }

    public void setindustryTitle(String industryTitle) {
        this.industryTitle = industryTitle;
    }
}
