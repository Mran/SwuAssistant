package com.swuos.allfragment.card;


import java.util.List;

/**
 * Created by codekk on 2016/5/13.
 * Email:  645326280@qq.com
 */
public interface IConsumeView {
    void onUpdateConsmueInfo(boolean status, List<ConsumeInfo> consumeInfos);
    void onSetProgressDialogVisible(int visible);
    void onSetErrorPageVisible(int visible);
    void onSetSwipeRefreshVisible(int visible);
}
