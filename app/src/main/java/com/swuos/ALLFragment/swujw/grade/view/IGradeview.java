package com.swuos.ALLFragment.swujw.grade.view;

import com.swuos.ALLFragment.swujw.grade.model.GradeItem;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/7/27.
 */
public interface IGradeview {
    void showDialog(Boolean isShow);

    void showResult(List<GradeItem> gradeItemList);

    void showError(String error);
}
