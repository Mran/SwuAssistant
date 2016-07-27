package com.swuos.ALLFragment.swujw.grade.persenter;

/**
 * Created by 张孟尧 on 2016/7/27.
 */
public interface IGradePersenter {
    void getGrades(String username, String password, String xqm, String xnm);

    void initData();

    String getUsername();

    String getPassword();

    void getGradeDetial(String username, String password, String xqm, String xnm, int Position);

}
