package com.swuos.ALLFragment.card;

/**
 * Created by codekk on 2016/5/13.
 * Email:  645326280@qq.com
 */
public interface IEcardPresenter  {
    void updateEcardInfo(String id, String pd);
    String getLastIndex();
    void setProgressDialogVisible(int visible);
    void setErrorPageVisible(int visible);
    void setSwipeRefreshVisible(int visible);
    void setInputDialogVisible(int visible);
    void savePassWord(String id,String pd);
    boolean checkPdSaved(String swuId);
    String getSwuId();
    String getPd(String swuId);
    void checkPdVailed(String id,String pd);
}
