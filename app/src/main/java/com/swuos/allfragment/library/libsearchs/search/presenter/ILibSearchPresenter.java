package com.swuos.allfragment.library.libsearchs.search.presenter;

import com.swuos.allfragment.library.libsearchs.search.model.bean.SearchBookItem;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/5.
 */

public interface ILibSearchPresenter {
    void SearchMore(String bookName);

    void firstSearch(String bookName);

    void cancelSearch();

    List<SearchBookItem> getSearchBookItemList();
}
