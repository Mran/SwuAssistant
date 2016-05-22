package com.swuos.ALLFragment.card;

import android.content.Context;
import android.content.SharedPreferences;

import com.swuos.swuassistant.MainActivity;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codekk on 2016/5/13.
 * Email:  645326280@qq.com
 */
public class EcardPresenterImp implements IEcardPresenter {
    public static EcardTools ecardTools;
    private IEcardView iEcardView;
    private List<EcardInfo> ecardInfos;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public EcardPresenterImp(IEcardView ecardView, Context context) {
        this.iEcardView = ecardView;
        this.mContext = context;
        ecardTools = new EcardTools();
        ecardInfos = new ArrayList<>();
        sharedPreferences = mContext.getSharedPreferences("eCardInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void updateEcardInfo(String id, String pd) {
        ecardInfos = ecardTools.GetEcardInfos(id, pd);
        if (ecardInfos != null && !ecardInfos.isEmpty()) {
            iEcardView.onUpdateEcardInfo(true, ecardInfos);
        } else {
            iEcardView.onUpdateEcardInfo(false, null);
        }
    }

    @Override
    public String getLastIndex() {
        String index = ecardTools.GetLastIndex();
        if (index != null && !index.isEmpty()) {
            return index;
        } else {
            return "1";  //默认的话就显示第一页，而不是提示出错
        }
    }

    @Override
    public void setProgressDialogVisible(int visible) {
        iEcardView.onSetProgressDialogVisible(visible);
    }

    @Override
    public void setErrorPageVisible(int visible) {
        iEcardView.onSetErrorPageVisible(visible);
    }

    @Override
    public void setSwipeRefreshVisible(int visible) {
        iEcardView.onSetSwipeRefreshVisible(visible);
    }

    @Override
    public void setInputDialogVisible(int visible) {
        iEcardView.onSetInputDialogVisible(visible);
    }

    @Override
    public void savePassWord(String id, String pd) {
        editor.putString(id, pd);
        editor.commit();
        SALog.d("kklog", "editor.commit()");
    }

    @Override
    public boolean checkPdSaved(String swuId) {
        String pd = sharedPreferences.getString(swuId, "nothing");
        if (pd.equals("nothing")) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public String getSwuId() {
        String swuId = MainActivity.sharedPreferences.getString("swuID", "nothing");
        return swuId;
    }

    @Override
    public String getPd(String swuId) {
        String pd = null;
        pd = sharedPreferences.getString(swuId, "nothing");
        return pd;
    }

    @Override
    public void checkPdVailed(String id, String pd) {
        List<EcardInfo> temp = ecardTools.GetEcardInfos(id, pd);
        if (temp==null || temp.isEmpty()) {
            iEcardView.onCheckPdVaild(false);
        } else {
            iEcardView.onCheckPdVaild(true);
        }
    }
}
