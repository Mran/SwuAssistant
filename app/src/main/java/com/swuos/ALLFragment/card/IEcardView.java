package com.swuos.ALLFragment.card;


import com.swuos.ALLFragment.card.EcardInfo;

import java.util.List;

/**
 * Created by codekk on 2016/5/13.
 * Email:  645326280@qq.com
 */
public interface IEcardView {
    void onUpdateEcardInfo(boolean status, List<EcardInfo> ecardInfos);
    void onSetProgressDialogVisible(int visible);
    void onSetErrorPageVisible(int visible);
    void onSetSwipeRefreshVisible(int visible);
    void onSetInputDialogVisible(int visible);
    void onCheckPdVaild(boolean flag);
}
