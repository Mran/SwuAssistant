package com.swuos.ALLFragment.library.lib.presenter;


import com.swuos.ALLFragment.library.lib.model.BookItem;
import com.swuos.ALLFragment.library.lib.utils.LibMainTools;
import com.swuos.ALLFragment.library.lib.views.ILibView;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/27.
 * Email:  645326280@qq.com
 */
public class LibPresenterImp implements ILibPresenter {
    private ILibView iLibView;
    private LibMainTools libMainTools;
    private boolean isLogined = false;
    public static final int FAILED = 0;
    public static final int SUCCEED = 1;

    public LibPresenterImp(ILibView iLibView) {
        this.iLibView = iLibView;

    }

    public boolean getUserInfos(String name, String pd) {
        libMainTools = new LibMainTools(name, pd);
        isLogined= libMainTools.getUserInfo();
        return isLogined;
    }

    @Override
    public void setRecyclerViewVisible(int visible) {
        iLibView.onSetRecyclerViewVisible(visible);
    }

    @Override
    public void setSwipeRefreshVisible(int visible) {
        iLibView.onSetSwipeRefreshVisible(visible);
    }

    @Override
    public void updateBookItems() {
        if (!isLogined) {
            iLibView.onUpdateBookItems(FAILED, null);
        } else {
            List<BookItem> bookHistory = libMainTools.getBookHistory();
            if (bookHistory == null || bookHistory.isEmpty()) {
                iLibView.onUpdateBookItems(FAILED, null);
            } else {
                iLibView.onUpdateBookItems(SUCCEED, bookHistory);
            }
        }
    }

    @Override
    public void setProgressDialogVisible(int visible) {
        iLibView.onSetProgressDialogVisible(visible);
    }

    @Override
    public void setTipDialogVisible(int visible) {
        iLibView.onSetTipDialogVisible(visible);
    }

    @Override
    public void setErrorLayoutVisible(int visible) {
        iLibView.onSetErrorLayoutVisible(visible);
    }
}
