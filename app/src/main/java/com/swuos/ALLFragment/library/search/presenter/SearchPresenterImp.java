package com.swuos.ALLFragment.library.search.presenter;


import com.swuos.ALLFragment.library.search.model.BookInfoSearch;
import com.swuos.ALLFragment.library.search.utils.LibTools;
import com.swuos.ALLFragment.library.search.views.ISearchView;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/25.
 * Email:  645326280@qq.com
 */
public class SearchPresenterImp implements ISearchPresenter {
    public static final int BOOK_NET_ERROR = 1;  //网络可能有问题导致从服务器获取数据失败
    public static final int BOOK_NO_INFO = 2;  //查询的书籍没有相关信息
    public static final int BOOK_SUCCEED = 3;   //查询成功
    private ISearchView iSearchView;
    private LibTools libTools;
    private List<BookInfoSearch> bookInfos;


    public SearchPresenterImp(ISearchView iSearchView) {
        this.iSearchView = iSearchView;
        libTools = new LibTools();
    }

    @Override
    public void setRecyclerViewVisible(int viewVisible) {
        iSearchView.onSetRecyclerViewVisible(viewVisible);
    }

    @Override
    public void setSwipeRefreshVisible(int visible) {
        iSearchView.onSetSwipeRefreshVisible(visible);
    }

    @Override
    public void setSwipeRefreshRefreshing(int refreshable) {
        iSearchView.onSetSwipeRefreshRefreshing(refreshable);
    }

    @Override
    public void updateBookInfoSearch(final String bookName, final int pageIndex) {
        bookInfos = libTools.getBookInfos(bookName, pageIndex);
        if (bookInfos == null) {
            iSearchView.onUpdateBookInfoSearch(BOOK_NET_ERROR, null);
        } else if (bookInfos.size() == 0) {
            iSearchView.onUpdateBookInfoSearch(BOOK_NO_INFO, bookInfos);
        } else {
            iSearchView.onUpdateBookInfoSearch(BOOK_SUCCEED, bookInfos);
        }
    }

    @Override
    public void finishThisView() {
        iSearchView.onFinishThisView();
    }

    @Override
    public void setLinearTipVisible(int visible) {
        iSearchView.onSetLinearTipVisible(visible);
    }
}
