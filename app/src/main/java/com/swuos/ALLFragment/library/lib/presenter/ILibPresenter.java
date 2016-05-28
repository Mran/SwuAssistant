package com.swuos.ALLFragment.library.lib.presenter;

/**
 * Created by youngkaaa on 2016/5/27.
 * Email:  645326280@qq.com
 */
public interface ILibPresenter {
    void setRecyclerViewVisible(int visible);
    void setSwipeRefreshVisible(int visible);
    void updateBookItems();
    void setProgressDialogVisible(int visible);
    void setTipDialogVisible(int visible);
    void setErrorLayoutVisible(int visible);
}
