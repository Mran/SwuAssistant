package com.swuos.ALLFragment.library.search.views;


import com.swuos.ALLFragment.library.search.model.BookDetail;
import com.swuos.ALLFragment.library.search.model.LibHoldInfo;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/26.
 * Email:  645326280@qq.com
 */
public interface IBookDetailView {
    void onSetProgressDialogVisible(int visible);
    void onUpdateBookDetails(int code, BookDetail bookDetail);
    void onUpdateLibHoldInfos(int code, List<LibHoldInfo> libHoldInfos);
    void onFinishThisView();
    void onSetErrorTextVisible(int visible, String text);
    void onSetIndicatorAndPagerVisible(int visible);
}
