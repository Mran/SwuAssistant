package com.swuos.ALLFragment.card;

/**
 * Created by codekk on 2016/5/13.
 * Email:  645326280@qq.com
 */
public interface IConsmuePresenter {
    void updateConsmueInfo(String index);
    void setProgressDialogVisible(int visible);
    void setErrorPageVisible(int visible);
    void setSwipeRefreshVisible(int visible);
}
