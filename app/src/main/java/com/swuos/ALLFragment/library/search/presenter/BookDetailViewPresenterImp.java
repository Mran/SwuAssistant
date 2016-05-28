package com.swuos.ALLFragment.library.search.presenter;


import com.swuos.ALLFragment.library.search.model.BookDetail;
import com.swuos.ALLFragment.library.search.model.LibHoldInfo;
import com.swuos.ALLFragment.library.search.utils.LibTools;
import com.swuos.ALLFragment.library.search.views.IBookDetailView;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/26.
 * Email:  645326280@qq.com
 */
public class BookDetailViewPresenterImp implements IBookDeatilPresenter {
    private IBookDetailView bookDetailView;
    private LibTools libTools;

    public BookDetailViewPresenterImp(IBookDetailView bookDetailView) {
        this.bookDetailView = bookDetailView;
        libTools = new LibTools();
    }

    @Override
    public void setProgressDialogVisible(int visible) {
        bookDetailView.onSetProgressDialogVisible(visible);
    }

    @Override
    public void finishThisView() {
        bookDetailView.onFinishThisView();
    }

    @Override
    public void updateBookDetails(String bookId) {
        BookDetail bookDetails = libTools.getBookDetails(bookId);
        if (bookDetails == null) {
            bookDetailView.onUpdateBookDetails(SearchPresenterImp.BOOK_NET_ERROR, null);
        } else {
            bookDetailView.onUpdateBookDetails(SearchPresenterImp.BOOK_SUCCEED, bookDetails);
        }
    }

    @Override
    public void updateLibHoldInfos(String bookId) {
        List<LibHoldInfo> libHoldInfos = libTools.getLibHoldInfos(bookId);
        if (libHoldInfos == null) {
            bookDetailView.onUpdateLibHoldInfos(SearchPresenterImp.BOOK_NET_ERROR, null);
        } else if (libHoldInfos.isEmpty()) {
            bookDetailView.onUpdateLibHoldInfos(SearchPresenterImp.BOOK_NO_INFO, null);
        } else {
            bookDetailView.onUpdateLibHoldInfos(SearchPresenterImp.BOOK_SUCCEED, libHoldInfos);
        }
    }

    @Override
    public void  setErrorTextVisible(int viewVisible,String text) {
       bookDetailView.onSetErrorTextVisible(viewVisible,text);
    }

    @Override
    public void setIndicatorAndPagerVisible(int visible) {
        bookDetailView.onSetIndicatorAndPagerVisible(visible);
    }
}
