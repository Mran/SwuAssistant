package com.swuos.ALLFragment.card;


import java.util.List;

/**
 * Created by codekk on 2016/5/13.
 * Email:  645326280@qq.com
 */

public class ConsmuePresenterImp implements IConsmuePresenter {
    private IConsumeView consumeView;
    private List<ConsumeInfo> consumeInfos;

    public ConsmuePresenterImp(IConsumeView iConsumeView){
        consumeView=iConsumeView;
    }

    @Override
    public void updateConsmueInfo(String index) {
        consumeInfos = EcardPresenterImp.ecardTools.GetConsumeInfos(index);
        if(consumeInfos!=null && !consumeInfos.isEmpty()){
            consumeView.onUpdateConsmueInfo(true,consumeInfos);
        }else{
            consumeView.onUpdateConsmueInfo(false,null);
        }
    }

    @Override
    public void setProgressDialogVisible(int visible) {
        consumeView.onSetProgressDialogVisible(visible);
    }

    @Override
    public void setErrorPageVisible(int visible) {
        consumeView.onSetErrorPageVisible(visible);
    }

    @Override
    public void setSwipeRefreshVisible(int visible) {
        consumeView.onSetSwipeRefreshVisible(visible);
    }
}
