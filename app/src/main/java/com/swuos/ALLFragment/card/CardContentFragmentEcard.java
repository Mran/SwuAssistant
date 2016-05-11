package com.swuos.ALLFragment.card;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codekk on 2016/5/3.
 * Email:  645326280@qq.com
 */
public class CardContentFragmentEcard extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutError;
    private String id;
    private String pd;
    private List<EcardInfo> ecardInfos;
    private RecyclerAdapterEcardInfo adapter;
    public EcardTools ecardTools;
    public static SwipeRefreshLayout swipeRefreshLayout;
    public static final int CARD_INFO_UPDATE = 1;
    public static final int CARD_INFO_ERROR = 2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CARD_INFO_UPDATE:
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (swipeRefreshLayout.getVisibility() == View.GONE) {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                    }
                    if (recyclerView.getVisibility() == View.GONE) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    if (linearLayoutError.getVisibility() == View.VISIBLE) {
                        linearLayoutError.setVisibility(View.GONE);
                    }
                    sharedPreferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(id, pd);  //以一卡通号来匹配一卡通密码
                    editor.commit();
                    Log.d("kklog","commits");
                    adapter = new RecyclerAdapterEcardInfo(getContext(), ecardInfos);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.addItemDecoration(new MyItemDecoration(getContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                    break;
                case CARD_INFO_ERROR:
                    if (Cardfragment.progressDialogLoading.isShowing()) {
                        Cardfragment.progressDialogLoading.dismiss();
                    }
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    adapter = new RecyclerAdapterEcardInfo(getContext(), ecardInfos);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.addItemDecoration(new MyItemDecoration(getContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    linearLayoutError.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    initHttpData();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_layout_content, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCardContent);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshCard);
        linearLayoutError = (LinearLayout) view.findViewById(R.id.linearLayoutCardError);
        Log.d("kklog","ecard onCreateView");
        return view;
    }

    public static CardContentFragmentEcard onNewInstance(String id, String pd, EcardTools ecardTools) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CARD_FRAGMENT_ID, id);
        bundle.putString(Constant.CARD_FRAGMENT_PD, pd);
        bundle.putSerializable(Constant.CARD_FRAGMENT_ECARD, ecardTools);
        CardContentFragmentEcard fragment = new CardContentFragmentEcard();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        id = arguments.getString(Constant.CARD_FRAGMENT_ID);
        pd = arguments.getString(Constant.CARD_FRAGMENT_PD);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHttpData();
            }
        });
        ecardTools = (EcardTools) arguments.getSerializable(Constant.CARD_FRAGMENT_ECARD);
        ecardInfos = new ArrayList<>();
        initHttpData();
    }

    public void initHttpData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ecardInfos = ecardTools.GetEcardInfos(id, pd);
                if (ecardInfos == null || ecardInfos.isEmpty()) {
                    Message message = new Message();
                    message.what = CARD_INFO_ERROR;
                    handler.sendMessage(message);
                } else {
                    if (Cardfragment.progressDialogLoading.isShowing()) {
                        Cardfragment.progressDialogLoading.dismiss();
                    }
                    Message message = new Message();
                    message.what = CARD_INFO_UPDATE;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    public void setPd(String pd) {
        this.pd = pd;
    }


    @Override
    public void onRefresh() {
        initHttpData();
    }

    public List<EcardInfo> getInfo() {
        List<EcardInfo> infos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            infos.add(new EcardInfo("name", "content"));
        }
        return infos;
    }
}
