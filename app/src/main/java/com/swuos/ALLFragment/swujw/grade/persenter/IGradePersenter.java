package com.swuos.allfragment.swujw.grade.persenter;

/**
 * Created by 张孟尧 on 2016/7/27.
 */
public interface IGradePersenter {
    void getGrades(String username, String password, String xqm, String xnm, boolean isFroceFromNet);

    void initData();

    String getUsername();

    String getPassword();

    String getXnm();

    void setXnm(String xnm);

    String getXqm();

    void setXqm(String xqm);

    int getXnmPosition();

    void setXnmPosition(int xnmPosition);

    int getXqmPosition();

    void setXqmPosition(int xqmPosition);

    void getGradeDetial(String username, String password,int Position);

    void saveUserLastCLick(int xnm, int xqm);

    int getLastxnmPosition();

    int getLastxqmPosition();

    String getGradesDataJsonFromCache(String xnm, String xqm);


}
