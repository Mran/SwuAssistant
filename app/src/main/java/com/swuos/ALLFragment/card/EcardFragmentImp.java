package com.swuos.ALLFragment.card;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codekk on 2016/5/13.
 * Email:  645326280@qq.com
 */
public class EcardFragmentImp extends Fragment implements IEcardView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private IEcardPresenter iEcardPresenter;
    private ProgressDialog progressDialog;
    private RecyclerAdapterEcardInfo recyclerAdapter;
    private List<EcardInfo> ecardInfos;
    private FloatingActionButton floatingActionButton;
    private String lastIndex;
    private LinearLayout linearLayoutError;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MaterialDialog materialDialog;
    private String id;
    private String pd;
    private boolean pdVaild = false;
    private IntentFilter intentFilter;
    private LocalRecevier localRecevier;
    private LocalBroadcastManager localBroadcastManager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    iEcardPresenter.setSwipeRefreshVisible(View.INVISIBLE);
                    iEcardPresenter.setProgressDialogVisible(View.INVISIBLE);
                    iEcardPresenter.setErrorPageVisible(View.INVISIBLE);
                    iEcardPresenter.setInputDialogVisible(View.INVISIBLE);
                    recyclerAdapter = new RecyclerAdapterEcardInfo(getContext(), ecardInfos);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    iEcardPresenter.setProgressDialogVisible(View.INVISIBLE);
                    iEcardPresenter.setErrorPageVisible(View.VISIBLE);
                    Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    iEcardPresenter.setProgressDialogVisible(View.INVISIBLE);
                    Toast.makeText(getContext(), "密码错误", Toast.LENGTH_LONG).show();
                    initInputDialog();
                    iEcardPresenter.setInputDialogVisible(View.VISIBLE);
                    break;
            }
        }
    };
    ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewEcard);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fabCard);
        linearLayoutError = (LinearLayout) view.findViewById(R.id.linearLayoutEcardError);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshEcard);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iEcardPresenter = new EcardPresenterImp(this, getContext());
        floatingActionButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        id = iEcardPresenter.getSwuId();
        if (id.equals("nothing")) {   //表示用户还没有登录
            initTipDialog("请先登录再执行对应操作");
        } else {
            initInputDialog();
        }
        ecardInfos = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapterEcardInfo(getContext(), getInfo());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new MyItemDecoration(getContext()));
        recyclerView.setAdapter(recyclerAdapter);
        linearLayoutError.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (!iEcardPresenter.checkPdSaved(id)) {  //每次启动时，判断是否已经储存了密码
            iEcardPresenter.setInputDialogVisible(View.VISIBLE);
        } else {
            pd = iEcardPresenter.getPd(id);
            iEcardPresenter.setProgressDialogVisible(View.VISIBLE);
            updateInfos();
        }
    }

    private void initTipDialog(String s) {
        materialDialog = new MaterialDialog.Builder(getActivity())
                .title("注意")
                .content(s)
                .positiveText("确定")
                .cancelable(true)
                .positiveColor(Color.parseColor("#48b360"))
                .build();
        materialDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iEcardPresenter.setInputDialogVisible(View.INVISIBLE);
            }
        });
    }


    private void initInputDialog() {
        materialDialog = new MaterialDialog.Builder(getActivity())
                .title("请输入")
                .positiveText("确定")
                .cancelable(true)
                .positiveColor(Color.parseColor("#48b360"))
                .customView(R.layout.card_dialog_view)
                .build();
        View customView = materialDialog.getCustomView();
        final EditText edittextPd = (EditText) customView.findViewById(R.id.editTextDialogCardPd);
        materialDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //wifi ssid状态获取
                WifiManager wifiManager = (WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String wifiSsid = wifiInfo.toString();
                if (!wifiSsid.contains("swu-wifi")) {
                    Toast.makeText(getActivity(), "请连接swu-wifi或者swu-wifi-dorm后重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                pd = edittextPd.getText().toString();
                SALog.d("kklog", "input pd==>" + pd);
                iEcardPresenter.setInputDialogVisible(View.INVISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        iEcardPresenter.checkPdVailed(id, pd);
                    }
                }).start();
                iEcardPresenter.setProgressDialogVisible(View.VISIBLE);
            }
        });
    }

    private void updateInfos() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                iEcardPresenter.updateEcardInfo(id, pd);
            }
        }).start();
    }

    @Override
    public void onUpdateEcardInfo(boolean status, List<EcardInfo> ecardInfos) {
        if (status) {
            this.ecardInfos = ecardInfos;
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
            lastIndex = iEcardPresenter.getLastIndex();
        } else {
            Message message = new Message();
            message.what = 2;
            mHandler.sendMessage(message);
            lastIndex = iEcardPresenter.getLastIndex();
        }
    }

    @Override
    public void onSetSwipeRefreshVisible(int visible) {
        if (swipeRefreshLayout.isRefreshing() && visible == View.INVISIBLE) {
            swipeRefreshLayout.setRefreshing(false);
        } else if (!swipeRefreshLayout.isRefreshing() && visible == View.VISIBLE) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onSetInputDialogVisible(int visible) {
        if (materialDialog.isShowing() && visible == View.INVISIBLE) {
            materialDialog.dismiss();
        } else if (!materialDialog.isShowing() && visible == View.VISIBLE) {
            materialDialog.show();
        }
    }

    @Override
    public void onCheckPdVaild(boolean flag) {
        if (!flag) {
            Message message = new Message();
            message.what = 3;
            mHandler.sendMessage(message);

        } else {
            iEcardPresenter.savePassWord(id, pd);
            updateInfos();
        }
    }

    @Override
    public void onSetProgressDialogVisible(int visible) {
        if (visible == View.VISIBLE && !progressDialog.isShowing()) {
            progressDialog.show();
        } else if (visible == View.INVISIBLE && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onSetErrorPageVisible(int visible) {
        if (linearLayoutError.getVisibility() == View.GONE && visible == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            linearLayoutError.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            floatingActionButton.hide();
        } else if (linearLayoutError.getVisibility() == View.VISIBLE && visible == View.INVISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            linearLayoutError.setVisibility(View.GONE);
            floatingActionButton.show();
        }
    }

    public List<EcardInfo> getInfo() {
        for (int i = 0; i < 10; i++) {
            ecardInfos.add(new EcardInfo("加载中", "加载中"));
        }
        return ecardInfos;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabCard:
                Intent intent = new Intent(getActivity(), ConsumeActivityImp.class);
                intent.putExtra("lastIndex", lastIndex);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.cardconsume_activity_in, R.anim.cardconsume_activity_out);
                break;
            case R.id.linearLayoutEcardError:
                id = iEcardPresenter.getSwuId();
                if (id.equals("nothing")) {   //表示用户还没有登录
                    initTipDialog("请先登录再执行对应操作");
                    iEcardPresenter.setInputDialogVisible(View.VISIBLE);
                    break;
                } else if (!id.equals("nothing")) {
                    initInputDialog();
                    if (!iEcardPresenter.checkPdSaved(id)) {  //每次启动时，判断是否已经储存了密码
                        iEcardPresenter.setInputDialogVisible(View.VISIBLE);
                    } else {
                        pd = iEcardPresenter.getPd(id);
                        iEcardPresenter.setProgressDialogVisible(View.VISIBLE);
                        updateInfos();
                    }
                    break;
                }
                iEcardPresenter.setProgressDialogVisible(View.VISIBLE);
                updateInfos();
                break;
        }
    }

    @Override
    public void onRefresh() {
        updateInfos();
    }

    /*设置广播接收刷新消息*/
    class LocalRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
