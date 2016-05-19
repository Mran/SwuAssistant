package com.swuos.ALLFragment.card;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codekk on 2016/5/13.
 * Email:  645326280@qq.com
 */
public class ConsumeActivityImp extends AppCompatActivity implements IConsumeView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private String lastIndex;
    private String currentIndex="0";
    private RecyclerView recyclerView;
    private List<ConsumeInfo> consumeInfos;
    private RecyclerAdapterConsume recyclerAdapter;
    private ProgressDialog progressDialog;
    private ConsmuePresenterImp consmuePresenter;
    private LinearLayout linearLayoutError;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private  int currentPos = 0;
    private int count;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    consmuePresenter.setProgressDialogVisible(View.INVISIBLE);
                    consmuePresenter.setSwipeRefreshVisible(View.INVISIBLE);
                    consmuePresenter.setErrorPageVisible(View.INVISIBLE);
                    recyclerAdapter = new RecyclerAdapterConsume(ConsumeActivityImp.this, consumeInfos);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                    count = recyclerAdapter.getItemCount();
                    recyclerView.smoothScrollToPosition(currentPos);
                    break;
                case 2:
                    consmuePresenter.setProgressDialogVisible(View.INVISIBLE);
                    consmuePresenter.setSwipeRefreshVisible(View.INVISIBLE);
                    consmuePresenter.setErrorPageVisible(View.VISIBLE);
                    Toast.makeText(ConsumeActivityImp.this, "刷新失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consmue_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarConsume);
        toolbar.setTitle("消费记录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        consumeInfos=new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#303F9F"));
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewConsmue);
        linearLayoutError = (LinearLayout) findViewById(R.id.linearLayoutConsumeError);
        lastIndex = getIntent().getStringExtra("lastIndex");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshConsume);
        consmuePresenter = new ConsmuePresenterImp(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(this));
        recyclerAdapter = new RecyclerAdapterConsume(this, getInfo());
        recyclerView.setAdapter(recyclerAdapter);
        linearLayoutError.setOnClickListener(this);
        consmuePresenter.setProgressDialogVisible(View.VISIBLE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // RecyclerView.SCROLL_STATE_IDLE: The RecyclerView is not currently scrolling.
                if (count == currentPos + 1 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    consmuePresenter.setSwipeRefreshVisible(View.VISIBLE);
                    currentIndex=lastIndex;
                    int index = Integer.parseInt(currentIndex);
                    --index;
                    currentIndex = String.valueOf(index);
                    consmuePresenter.setProgressDialogVisible(View.VISIBLE);
                    updateInfos(currentIndex);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentPos = layoutManager.findLastVisibleItemPosition();
            }
        });

        updateInfos(lastIndex);
    }

    private void updateInfos(final String lastIndex) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                consmuePresenter.updateConsmueInfo(lastIndex);
            }
        }).start();
    }

    @Override
    public void onUpdateConsmueInfo(boolean status, List<ConsumeInfo> consumeInfos) {
        if (status) {
            for (ConsumeInfo info : consumeInfos) {
                this.consumeInfos.add(info);
            }
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        } else {
            Message message = new Message();
            message.what = 2;
            mHandler.sendMessage(message);
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
            swipeRefreshLayout.setVisibility(View.GONE);
            linearLayoutError.setVisibility(View.VISIBLE);
        } else if (linearLayoutError.getVisibility() == View.VISIBLE && visible == View.INVISIBLE) {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            linearLayoutError.setVisibility(View.GONE);
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

    public List<ConsumeInfo> getInfo() {
        List<ConsumeInfo> infos = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            ConsumeInfo consumeInfo = new ConsumeInfo();
            consumeInfo.setTime("nothing");
            consumeInfo.setTimes("nothing");
            consumeInfo.setAddress("nothing");
            consumeInfo.setKind("nothing");
            consumeInfo.setBefore("nothing");
            consumeInfo.setDelta("nothing");
            consumeInfo.setAfter("nothing");
            infos.add(consumeInfo);
        }
        return infos;
    }

    @Override
    public void onRefresh() {
        updateInfos(lastIndex);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayoutConsumeError:
                consmuePresenter.setProgressDialogVisible(View.VISIBLE);
                updateInfos(lastIndex);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
