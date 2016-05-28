package com.swuos.ALLFragment.library.search.presenter;

/**
 * Created by youngkaaa on 2016/5/26.
 * Email:  645326280@qq.com
 */
public interface IBookDeatilPresenter {
    void setProgressDialogVisible(int visible);
    void finishThisView();
    void updateBookDetails(String bookId);
    void updateLibHoldInfos(String bookId);
    void setErrorTextVisible(int viewVisible, String text);
    void setIndicatorAndPagerVisible(int visible);
}
