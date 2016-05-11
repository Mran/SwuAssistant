package com.swuos.ALLFragment.card;

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
public class CardContentFragmentConsume extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutError;
    private String id;
    private String pd;
    private List<ConsumeInfo> consumeInfos;
    private RecyclerAdapterConsume adapter;
    public EcardTools ecardTools;
    public static SwipeRefreshLayout swipeRefreshLayout;
    public static final int CARD_INFO_UPDATE = 1;
    public static final int CARD_INFO_ERROR = 2;
    private int lastIndex;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CARD_INFO_UPDATE:
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    if (recyclerView.getVisibility() == View.GONE) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    if (swipeRefreshLayout.getVisibility() == View.GONE) {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                    }

                    if (linearLayoutError.getVisibility() == View.VISIBLE) {
                        linearLayoutError.setVisibility(View.GONE);
                    }

                    Cardfragment.page2IsUpdate = true;
                    adapter = new RecyclerAdapterConsume(getContext(), consumeInfos);
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
                    adapter = new RecyclerAdapterConsume(getContext(), consumeInfos);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.addItemDecoration(new MyItemDecoration(getContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    linearLayoutError.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("kklog","consume onCreateView");
        View view = inflater.inflate(R.layout.card_layout_content, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCardContent);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshCard);
        linearLayoutError = (LinearLayout) view.findViewById(R.id.linearLayoutCardError);
        return view;
    }

    public static CardContentFragmentConsume onNewInstance(EcardTools ecardTools) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CARD_FRAGMENT_ECARD, ecardTools);
        CardContentFragmentConsume fragment = new CardContentFragmentConsume();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("kklog","consume onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        linearLayoutError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHttpData();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        ecardTools = (EcardTools) arguments.getSerializable(Constant.CARD_FRAGMENT_ECARD);
    }

    public void initHttpData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s=null;
                try {
                    s = ecardTools.GetLastIndex();
                }catch (StringIndexOutOfBoundsException e){
                    Message message = new Message();
                    message.what = CARD_INFO_ERROR;
                    handler.sendMessage(message);
                }
                if (s == null) {
                    Message message = new Message();
                    message.what = CARD_INFO_ERROR;
                    handler.sendMessage(message);
                } else {
                    lastIndex = Integer.parseInt(s);
                    consumeInfos = ecardTools.GetConsumeInfos(String.valueOf(lastIndex));
                    if (consumeInfos == null || consumeInfos.isEmpty()) {
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
            }
        }).start();
    }

    @Override
    public void onRefresh() {
        initHttpData();
    }
    //防止当数据未加载到位而产生的滑动recyclerview错误
    public List<ConsumeInfo> getInfo() {
        List<ConsumeInfo> infos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ConsumeInfo consumeInfo = new ConsumeInfo();
            consumeInfo.setTime("time");
            consumeInfo.setTimes("time");
            consumeInfo.setAddress("time");
            consumeInfo.setKind("time");
            consumeInfo.setBefore("time");
            consumeInfo.setDelta("time");
            consumeInfo.setAfter("time");
            infos.add(consumeInfo);
        }
        return infos;
    }
}
