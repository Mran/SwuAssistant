package com.swuos.ALLFragment.library.search.views;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.swuos.ALLFragment.library.search.adapters.FragmentAdapter;
import com.swuos.ALLFragment.library.search.model.BookDetail;
import com.swuos.ALLFragment.library.search.model.LibHoldInfo;
import com.swuos.ALLFragment.library.search.presenter.BookDetailViewPresenterImp;
import com.swuos.ALLFragment.library.search.presenter.IBookDeatilPresenter;
import com.swuos.ALLFragment.library.search.presenter.SearchPresenterImp;
import com.swuos.ALLFragment.library.search.utils.LibSearch;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by youngkaaa on 2016/5/26.
 * Email:  645326280@qq.com
 */
public class BookDetailViewAty extends AppCompatActivity implements IBookDetailView {
    private TextView textViewIsbnPrice;
    private TextView textViewLanguage;
    private TextView textViewBookNameAuthor;
    private TextView textViewPublisher;
    private TextView textViewPages;
    private TextView textVieCLkind;
    private TextView textViewNameMajor;
    private TextView textViewNameMinor;
    private TextView textViewRecordRsource;
    private TextView textViewError;
    private String bookId;
    private String bookName;
    private String author;
    private LibSearch libSearch;
    private String s;
    private BookDetail bookDetail;
    private IBookDeatilPresenter deatilPresenter;
    private List<LibHoldInfo> libHoldInfos;
    private List<Fragment> fragments;
    private FragmentAdapter fragmentAdapter;
    private ViewPager viewPager;
    private CircleIndicator indicator;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    deatilPresenter.setProgressDialogVisible(View.GONE);
                    bindData();
                    break;
                case 2:
                    deatilPresenter.setProgressDialogVisible(View.GONE);
                    break;
                case 3:
                    deatilPresenter.setIndicatorAndPagerVisible(View.VISIBLE);
                    deatilPresenter.setErrorTextVisible(View.GONE, "");
                    if (libHoldInfos == null) {
                        SALog.d("kklog", "libHoldInfos==null");
                    }
                    fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
                    viewPager.setAdapter(fragmentAdapter);
                    indicator.setViewPager(viewPager);
                    SALog.d("kklog", "libHoldInfos.size()==>" + libHoldInfos.size());
                    break;
                case 4:
                    deatilPresenter.setProgressDialogVisible(View.GONE);
                    deatilPresenter.setIndicatorAndPagerVisible(View.GONE);
                    deatilPresenter.setErrorTextVisible(View.VISIBLE, "网络貌似不正常");
                    Toast.makeText(BookDetailViewAty.this, "网络貌似不正常", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    deatilPresenter.setProgressDialogVisible(View.GONE);
                    deatilPresenter.setIndicatorAndPagerVisible(View.GONE);
                    deatilPresenter.setErrorTextVisible(View.VISIBLE, "获取馆藏信息为空");
                    Toast.makeText(BookDetailViewAty.this, "获取馆藏信息为空", Toast.LENGTH_SHORT).show();
                    break;

            }
        }

    };
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        inits();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarBookDetail);
        setSupportActionBar(toolbar);
        this.setTitle(bookName);
        toolbar.setTitleTextColor(Color.WHITE);
        Drawable drawable = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationIcon(drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        deatilPresenter.setProgressDialogVisible(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                SALog.d("kklog", "BookDetailViewAty bookID==>" + bookId);
                deatilPresenter.updateBookDetails(bookId);
                deatilPresenter.updateLibHoldInfos(bookId);
                Looper.loop();
            }
        }).start();
    }

    private void inits() {
        fragments = new ArrayList<>();
        libSearch = new LibSearch();
        indicator = (CircleIndicator) findViewById(R.id.indicatorViewPager);
        viewPager = (ViewPager) findViewById(R.id.viewPagerLibInfo);
        deatilPresenter = new BookDetailViewPresenterImp(this);
        textViewIsbnPrice = (TextView) findViewById(R.id.textViewBookDetailIsbn_Price);
        textViewLanguage = (TextView) findViewById(R.id.textViewBookDetailLanguage);
        textViewBookNameAuthor = (TextView) findViewById(R.id.textViewBookDetailBookName_author);
        textViewPublisher = (TextView) findViewById(R.id.textViewBookDetailPublisher);
        textViewPages = (TextView) findViewById(R.id.textViewBookDetailPages);
        textVieCLkind = (TextView) findViewById(R.id.textViewBookDetailCL_kind);
        textViewNameMajor = (TextView) findViewById(R.id.textViewBookDetailNameMajor);
        textViewNameMinor = (TextView) findViewById(R.id.textViewBookDetailNameMinor);
        textViewRecordRsource = (TextView) findViewById(R.id.textViewBookRecordResource);
        textViewError = (TextView) findViewById(R.id.textViewLibInfoError);
        bookId = getIntent().getStringExtra("bookId");
        bookName = getIntent().getStringExtra("bookName");
        author = getIntent().getStringExtra("author");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
    }

    @Override
    public void onSetProgressDialogVisible(int visible) {
        if (visible == View.VISIBLE && !progressDialog.isShowing()) {
            progressDialog.show();
        } else if (visible == View.GONE && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void onUpdateBookDetails(int code, BookDetail bookDetail) {
        if (code == SearchPresenterImp.BOOK_SUCCEED) {
            this.bookDetail = bookDetail;
            mHandler.sendEmptyMessage(1);
        } else {
            mHandler.sendEmptyMessage(2);
        }
    }

    @Override
    public void onUpdateLibHoldInfos(int code, List<LibHoldInfo> libHoldInfos) {
        if (code == SearchPresenterImp.BOOK_SUCCEED) {
            this.libHoldInfos = libHoldInfos;
            for (int i = 0; i < libHoldInfos.size(); i++) {
                FragmentViewPagerItem fragmentViewPagerItem = FragmentViewPagerItem.newInstance(libHoldInfos.get(i));
                fragments.add(fragmentViewPagerItem);
            }
            mHandler.sendEmptyMessage(3);
        } else if (code == SearchPresenterImp.BOOK_NET_ERROR) {
            mHandler.sendEmptyMessage(4);
        } else if (code == SearchPresenterImp.BOOK_NO_INFO) {
            mHandler.sendEmptyMessage(5);
        }
    }

    @Override
    public void onFinishThisView() {
        finish();
    }

    @Override
    public void onSetErrorTextVisible(int visible, String text) {
        if (visible == View.GONE && textViewError.getVisibility() == View.VISIBLE) {
            textViewError.setVisibility(visible);
        } else if (visible == View.VISIBLE) {
            textViewError.setVisibility(visible);
            textViewError.setText(text);
        }
    }

    @Override
    public void onSetIndicatorAndPagerVisible(int visible) {
        if (visible == View.VISIBLE) {
            if (indicator.getVisibility() == View.GONE) {
                indicator.setVisibility(visible);
            }
            if (viewPager.getVisibility() == View.GONE) {
                viewPager.setVisibility(visible);
            }
        } else if (visible == View.GONE) {
            if (indicator.getVisibility() == View.VISIBLE) {
                indicator.setVisibility(visible);
            }
            if (viewPager.getVisibility() == View.VISIBLE) {
                viewPager.setVisibility(visible);
            }
        }
    }

    private void bindData() {
        if (bookDetail.getIsbn_price().equals("")) {
            textViewIsbnPrice.setVisibility(View.GONE);
        } else {
            textViewIsbnPrice.setText(bookDetail.getIsbn_price());
        }

        if (bookDetail.getLanguage().equals("")) {
            textViewIsbnPrice.setVisibility(View.GONE);
        } else {
            textViewLanguage.setText(bookDetail.getLanguage());
        }

        if (bookDetail.getBookName_author().equals("")) {
            textViewIsbnPrice.setVisibility(View.GONE);
        } else {
            textViewBookNameAuthor.setText(bookDetail.getBookName_author());
        }

        if (bookDetail.getPublisher().equals("")) {
            textViewIsbnPrice.setVisibility(View.GONE);
        } else {
            textViewPublisher.setText(bookDetail.getPublisher());
        }

        if (bookDetail.getPages().equals("")) {
            textViewIsbnPrice.setVisibility(View.GONE);
        } else {
            textViewPages.setText(bookDetail.getPages());
        }

        if (bookDetail.getCl_kind().equals("")) {
            textViewIsbnPrice.setVisibility(View.GONE);
        } else {
            textVieCLkind.setText(bookDetail.getCl_kind());
        }

        if (bookDetail.getPersonNameMajor().equals("")) {
            textViewIsbnPrice.setVisibility(View.GONE);
        } else {
            textViewNameMajor.setText(bookDetail.getPersonNameMajor());
        }

        if (bookDetail.getPersonNameMinor().equals("")) {
            textViewIsbnPrice.setVisibility(View.GONE);
        } else {
            textViewNameMinor.setText(bookDetail.getPersonNameMinor());
        }

        if (bookDetail.getRecord_source().equals("")) {
            textViewIsbnPrice.setVisibility(View.GONE);
        } else {
            textViewRecordRsource.setText(bookDetail.getRecord_source());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
