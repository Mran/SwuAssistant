package com.swuos.ALLFragment.swujw.schedule.view;

import com.swuos.ALLFragment.swujw.grade.model.GradeItem;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/8/3.
 */
public interface IScheduleView {
    void showDialog(Boolean isShow);

    void showResult();

    void showError(String error);
}
