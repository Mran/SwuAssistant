package com.swuos.ALLFragment.library.lib.views;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.swuos.ALLFragment.card.MyItemDecoration;
import com.swuos.ALLFragment.library.lib.adapters.RecyclerAdapterLibMain;
import com.swuos.ALLFragment.library.lib.model.BookItem;
import com.swuos.ALLFragment.library.lib.presenter.ILibPresenter;
import com.swuos.ALLFragment.library.lib.presenter.LibPresenterImp;
import com.swuos.swuassistant.MainActivity;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/27.
 * Email:  645326280@qq.com
 */
public class LibFragment extends Fragment implements ILibView, SwipeRefreshLayout.OnRefreshListener, RecyclerAdapterLibMain.OnRecyclerItemClickedListener, View.OnClickListener {
    public static final String KEY_USER_NAME = "LibFragment_Name";
    public static final String KEY_USER_PD = "LibFragment_PASSWD";
    private RecyclerView recyclerView;
    private RecyclerAdapterLibMain recyclerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ILibPresenter iLibPresenter;
    private ProgressDialog progressDialog;
    private List<BookItem> bookItems;
    private String userName;
    private String passwd;
    private boolean isLogin = false;
    private MaterialDialog dialog;
    private LinearLayout linearLayoutError;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    swipeRefreshLayout.setRefreshing(false);
                    iLibPresenter.setProgressDialogVisible(View.GONE);
                    iLibPresenter.setErrorLayoutVisible(View.GONE);
                    Toast.makeText(getContext(), "获取数据成功！！", Toast.LENGTH_SHORT).show();
                    recyclerAdapter = new RecyclerAdapterLibMain(getContext(), bookItems);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                    recyclerAdapter.setRecyclerItemClickedListener(LibFragment.this);
                    break;
                case 2:
                    swipeRefreshLayout.setRefreshing(false);
                    iLibPresenter.setProgressDialogVisible(View.GONE);
                    iLibPresenter.setErrorLayoutVisible(View.VISIBLE);
                    Toast.makeText(getContext(), "获取数据失败！！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SALog.d("kklog", "onCreateView");
        View view = inflater.inflate(R.layout.lib_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewLibMain);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLibMain);
        linearLayoutError = (LinearLayout) view.findViewById(R.id.linearLayoutLibError);
        iLibPresenter = new LibPresenterImp(this);
        initDialog();
        userName = MainActivity.sharedPreferences.getString("userName", "nothing");
        passwd = MainActivity.sharedPreferences.getString("password", "nothing");
        if (userName.equals("nothing") || passwd.equals("nothing")) { //表示未登录
            iLibPresenter.setTipDialogVisible(View.VISIBLE);
            iLibPresenter.setErrorLayoutVisible(View.VISIBLE);
            isLogin = false;
        } else {
            isLogin = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean userInfos = ((LibPresenterImp) iLibPresenter).getUserInfos(userName, passwd);
                    if (userInfos) {
                        iLibPresenter.updateBookItems();
                    } else {
                        mHandler.sendEmptyMessage(2);
                    }
                }
            }).start();
        }
        return view;
    }

    private void initDialog() {
        dialog = new MaterialDialog.Builder(getActivity())
                .title("注意")
                .content("请先登录再执行对应操作")
                .positiveText("确定")
                .cancelable(true)
                .positiveColor(Color.parseColor("#48b360"))
                .build();
        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iLibPresenter.setTipDialogVisible(View.GONE);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        SALog.d("kklog", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("加载中...");
        linearLayoutError.setOnClickListener(this);
        if (isLogin) {
            iLibPresenter.setProgressDialogVisible(View.VISIBLE);
            iLibPresenter.setErrorLayoutVisible(View.GONE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new MyItemDecoration(getContext()));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onSetRecyclerViewVisible(int visible) {
        if (visible == View.GONE && recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        } else if (visible == View.VISIBLE && recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSetProgressDialogVisible(int visible) {
        if (visible == View.GONE && progressDialog.isShowing()) {
            progressDialog.cancel();
        } else if (visible == View.VISIBLE && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void onSetSwipeRefreshVisible(int visible) {
        if (visible == View.GONE && swipeRefreshLayout.getVisibility() == View.VISIBLE) {
            swipeRefreshLayout.setVisibility(View.GONE);
        } else if (visible == View.VISIBLE && swipeRefreshLayout.getVisibility() == View.GONE) {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUpdateBookItems(int code, List<BookItem> bookItems) {
        if (code == LibPresenterImp.FAILED) {
            SALog.d("kklog", "code == LibPresenterImp.FAILED");
            mHandler.sendEmptyMessage(2);
        } else if (code == LibPresenterImp.SUCCEED) {
            SALog.d("kklog", "code == LibPresenterImp.SUCCEED");
            this.bookItems = bookItems;
            mHandler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onSetTipDialogVisible(int visible) {
        if (visible == View.GONE && dialog.isShowing()) {
            dialog.cancel();
        } else if (visible == View.VISIBLE && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onSetErrorLayoutVisible(int visible) {
        if (visible == View.GONE && linearLayoutError.getVisibility() == View.VISIBLE) {
            linearLayoutError.setVisibility(View.GONE);
        } else if (visible == View.VISIBLE && linearLayoutError.getVisibility() == View.GONE) {
            linearLayoutError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClicked(View v, int position) {
        Toast.makeText(getContext(), "position==>" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        if (isLogin) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean userInfos = ((LibPresenterImp) iLibPresenter).getUserInfos(userName, passwd);
                    if (userInfos) {
                        iLibPresenter.updateBookItems();
                        mHandler.sendEmptyMessage(1);
                    } else {
                        mHandler.sendEmptyMessage(2);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayoutLibError:
                iLibPresenter.setProgressDialogVisible(View.VISIBLE);
                userName = MainActivity.sharedPreferences.getString("userName", "nothing");
                passwd = MainActivity.sharedPreferences.getString("password", "nothing");
                if (userName.equals("nothing") || passwd.equals("nothing")) {
                    isLogin = false;
                    iLibPresenter.setProgressDialogVisible(View.GONE);
                    iLibPresenter.setTipDialogVisible(View.VISIBLE);
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean userInfos = ((LibPresenterImp) iLibPresenter).getUserInfos(userName, passwd);
                            if (userInfos) {
                                iLibPresenter.updateBookItems();
                            } else {
                                mHandler.sendEmptyMessage(2);
                            }
                        }
                    }).start();
                }
                break;
        }
    }
}
