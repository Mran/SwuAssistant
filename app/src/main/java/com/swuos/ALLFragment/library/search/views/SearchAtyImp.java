package com.swuos.ALLFragment.library.search.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swuos.ALLFragment.card.MyItemDecoration;
import com.swuos.ALLFragment.library.search.adapters.RecycleAdapterSearch;
import com.swuos.ALLFragment.library.search.model.BookInfoSearch;
import com.swuos.ALLFragment.library.search.presenter.ISearchPresenter;
import com.swuos.ALLFragment.library.search.presenter.SearchPresenterImp;
import com.swuos.ALLFragment.library.search.utils.LibSearch;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;
import com.swuos.util.tools.Tools;

import java.util.List;

public class SearchAtyImp extends AppCompatActivity implements View.OnClickListener, ISearchView, RecycleAdapterSearch.OnRecyclerItemClickedListener,SwipeRefreshLayout.OnRefreshListener{
    private AppCompatEditText editText;
    private ImageButton imageButtonBack;
    private ImageButton imageButtonClear;
    private boolean hasInputSomething = false;
    private LibSearch libSearch;
    private TextView textViewResult;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ISearchPresenter iSearchPresenter;
    private List<BookInfoSearch> bookInfoSearches;
    private RecycleAdapterSearch recycleAdapter;
    private LinearLayout linearLayoutTip;

    public static final int REFRESH_BEGIN = 0;
    public static final int REFRESH_STOP = 1;

    private String resultStr;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    iSearchPresenter.setLinearTipVisible(View.GONE);
                    iSearchPresenter.setSwipeRefreshRefreshing(REFRESH_STOP);
                    iSearchPresenter.setRecyclerViewVisible(View.VISIBLE);
                    //关闭软键盘
                    Tools.closeSoftKeyBoard(SearchAtyImp.this);
                    recycleAdapter=new RecycleAdapterSearch(SearchAtyImp.this,bookInfoSearches);
                    recyclerView.setAdapter(recycleAdapter);
                    recycleAdapter.notifyDataSetChanged();
                    recycleAdapter.setOnRecyclerItemClickListener(SearchAtyImp.this);
                    break;
                case 2:
                    iSearchPresenter.setRecyclerViewVisible(View.INVISIBLE);
                    iSearchPresenter.setSwipeRefreshRefreshing(REFRESH_STOP);
                    Toast.makeText(SearchAtyImp.this, "网络不好，请重新加载！！", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    iSearchPresenter.setRecyclerViewVisible(View.INVISIBLE);
                    iSearchPresenter.setSwipeRefreshRefreshing(REFRESH_STOP);
                    Toast.makeText(SearchAtyImp.this, "并未查询到对应的书籍信息！！", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_aty);
        initsAndBinds();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void initsAndBinds() {
        iSearchPresenter = new SearchPresenterImp(this);
        imageButtonBack = (ImageButton) findViewById(R.id.imgBtnSearchBack);
        imageButtonClear = (ImageButton) findViewById(R.id.imgBtnSearchClear);
        editText = (AppCompatEditText) findViewById(R.id.editTextInput);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearch);
        linearLayoutTip= (LinearLayout) findViewById(R.id.linearLayoutSearchTip);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchAtyImp.this));
        recyclerView.addItemDecoration(new MyItemDecoration(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshSearch);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        imageButtonBack.setOnClickListener(this);
        imageButtonClear.setOnClickListener(this);
        libSearch = new LibSearch();
        iSearchPresenter.setLinearTipVisible(View.VISIBLE);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                iSearchPresenter.setSwipeRefreshVisible(View.VISIBLE);
                iSearchPresenter.setSwipeRefreshRefreshing(REFRESH_BEGIN);
                if (s.toString().length() != 0) {
                    hasInputSomething = true;
                    imageButtonClear.setVisibility(View.VISIBLE);
                    iSearchPresenter.setSwipeRefreshVisible(View.VISIBLE);
                    iSearchPresenter.setSwipeRefreshRefreshing(REFRESH_BEGIN);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            iSearchPresenter.updateBookInfoSearch(editText.getText().toString(), 0);
                        }
                    }).start();
                } else {
                    iSearchPresenter.setSwipeRefreshRefreshing(REFRESH_STOP);
                    hasInputSomething = false;
                    imageButtonClear.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnSearchBack:
                iSearchPresenter.finishThisView();
                break;
            case R.id.imgBtnSearchClear:
                editText.setText("");
                hasInputSomething = false;
                break;
        }
    }

    @Override
    public void onSetRecyclerViewVisible(int visible) {
        if (visible == View.VISIBLE && recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(visible);
        } else if (visible == View.GONE && recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(visible);
        }
    }

    @Override
    public void onSetSwipeRefreshVisible(int visible) {
        if (visible == View.VISIBLE && swipeRefreshLayout.getVisibility() == View.GONE) {
            swipeRefreshLayout.setVisibility(visible);
        } else if (visible == View.GONE && swipeRefreshLayout.getVisibility() == View.VISIBLE) {
            swipeRefreshLayout.setVisibility(visible);
        }
    }

    @Override
    public void onSetSwipeRefreshRefreshing(int refreshable) {
        if (refreshable == REFRESH_BEGIN && !swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        } else if (refreshable == REFRESH_STOP && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onUpdateBookInfoSearch(int code, List<BookInfoSearch> bookInfoSearches) {
        if (code == SearchPresenterImp.BOOK_NET_ERROR) {
            mHandler.sendEmptyMessage(2);
        } else if (code == SearchPresenterImp.BOOK_NO_INFO) {
            mHandler.sendEmptyMessage(3);
        } else {
            this.bookInfoSearches = bookInfoSearches;
            mHandler.sendEmptyMessage(1);
//            for (BookInfoSearch bookInfoSearch : bookInfoSearches) {
//                SALog.d("hlog", "bookInfoSearch.getBookId()==>" + bookInfoSearch.getBookId());
//                SALog.d("hlog", "bookInfoSearch.getBookName()==>" + bookInfoSearch.getBookName());
//                SALog.d("hlog", "bookInfoSearch.getAuthor()==>" + bookInfoSearch.getAuthor());
//                SALog.d("hlog", "bookInfoSearch.getCallNumber()==>" + bookInfoSearch.getCallNumber());
//                SALog.d("hlog", "bookInfoSearch.getIsbn()==>" + bookInfoSearch.getIsbn());
//                SALog.d("hlog", "bookInfoSearch.getPublisher()==>" + bookInfoSearch.getPublisher());
//                SALog.d("hlog", "bookInfoSearch.getBookKind()==>" + bookInfoSearch.getBookKind());
//            }
        }
    }

    @Override
    public void onFinishThisView() {
        this.finish();
    }

    @Override
    public void onSetLinearTipVisible(int visible) {
        if (visible == View.VISIBLE && linearLayoutTip.getVisibility() == View.GONE) {
            linearLayoutTip.setVisibility(visible);
        } else if (visible == View.GONE && linearLayoutTip.getVisibility() == View.VISIBLE) {
            linearLayoutTip.setVisibility(visible);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(SearchAtyImp.this,BookDetailViewAty.class);
        intent.putExtra("bookId",bookInfoSearches.get(position).getBookId());
        intent.putExtra("bookName",bookInfoSearches.get(position).getBookName());
        intent.putExtra("author",bookInfoSearches.get(position).getAuthor());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        iSearchPresenter.setSwipeRefreshRefreshing(REFRESH_STOP);
    }
}
