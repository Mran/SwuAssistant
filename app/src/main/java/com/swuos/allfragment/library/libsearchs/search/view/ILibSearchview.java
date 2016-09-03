package com.swuos.allfragment.library.libsearchs.search.view;

import com.swuos.allfragment.library.libsearchs.search.model.bean.SearchBookItem;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/5.
 */

public interface ILibSearchView {
    void ShowResult(List<SearchBookItem> searchBookItemList);
    void ShowError(String message);

}
