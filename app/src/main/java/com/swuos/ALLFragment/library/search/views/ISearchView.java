package com.swuos.ALLFragment.library.search.views;


import com.swuos.ALLFragment.library.search.model.BookInfoSearch;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/25.
 * Email:  645326280@qq.com
 */
public interface ISearchView {
    void onSetRecyclerViewVisible(int visible);
    void onSetSwipeRefreshVisible(int visible);
    void onSetSwipeRefreshRefreshing(int refreshable);
    void onUpdateBookInfoSearch(int code, List<BookInfoSearch> bookInfoSearches);
    void onFinishThisView();
    void onSetLinearTipVisible(int visible);
}
