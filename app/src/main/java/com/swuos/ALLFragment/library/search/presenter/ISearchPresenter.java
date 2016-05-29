package com.swuos.ALLFragment.library.search.presenter;

/**
 * Created by youngkaaa on 2016/5/25.
 * Email:  645326280@qq.com
 */
public interface ISearchPresenter {
    void setRecyclerViewVisible(int viewVisible);
    void setSwipeRefreshVisible(int visible);
    void setSwipeRefreshRefreshing(int refreshable);
    void updateBookInfoSearch(String bookName, int pageIndex);
    void finishThisView();
    void setLinearTipVisible(int visible);
}
