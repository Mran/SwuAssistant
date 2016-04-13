package com.swuos.ALLFragment.library;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.swuassistant.MainActivity;
import com.swuos.swuassistant.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class LibraryFragment extends Fragment {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fabRefresh;
    private List<NameValuePair> nameValuePairsLoginLibrary;
    private List<BookCell> books;
    private List<BookCell> userInfo;
    private List<BookInfo> borrowedInfo;
    private LibraryContentFragment fragmentHistory;
    private LibraryContentFragment fragmentUserInfo;
    private LibraryContentFragment fragmentBorrowInfo;
    private FloatingActionButton floatingActionButton;
    private static ProgressDialog progressDialogLoading;
    private String userName;
    private String password;
    private TotalInfo totalInfo = new TotalInfo();
    private static final int FRAGMENT_USERINFO_UPDATE = 0;
    private static final int FRAGMENT_HISTORY_UPDATE = 1;
    private static final int FRAGMENT_BORROWINFO_UPDATE = 2;

    private View libraryLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FRAGMENT_HISTORY_UPDATE:
                    progressDialogLoading.dismiss();
                    if (books.isEmpty()) {
                        Snackbar.make(tabLayout, "获取数据失败或者借阅历史为空.", Snackbar.LENGTH_SHORT).show();
                    } else {
                        fragmentHistory.UpdateHistory(books);
                    }
                    break;
                case FRAGMENT_USERINFO_UPDATE:
                    progressDialogLoading.dismiss();
                    if (userInfo.isEmpty()) {
                        Snackbar.make(tabLayout, "获取数据失败，请重试.", Snackbar.LENGTH_SHORT).show();
                    } else {
                        fragmentUserInfo.UpdateUserInfo(userInfo);
                    }
                    break;
                case FRAGMENT_BORROWINFO_UPDATE:
                    progressDialogLoading.dismiss();
                    if (borrowedInfo.isEmpty()) {
                        Snackbar.make(tabLayout, "你确定你借书了？！", Snackbar.LENGTH_SHORT).show();
                    } else {
                        fragmentBorrowInfo.UpdateBorrowedInfo(borrowedInfo);
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        libraryLayout = inflater.inflate(R.layout.library_layout, container, false);
        return libraryLayout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameValuePairsLoginLibrary = new ArrayList<>();



        Log.d("HttpLog", "onActivityCreated userName====>" + userName);
        Log.d("HttpLog", "onActivityCreated password====>" + password);
        progressDialogLoading = new ProgressDialog(getContext());
        tabLayout = (TabLayout) libraryLayout.findViewById(R.id.tabLayoutLibrary);
        viewPager = (ViewPager) libraryLayout.findViewById(R.id.viewPagerLibrary);
        floatingActionButton = (FloatingActionButton) libraryLayout.findViewById(R.id.fabRefresh);
        books = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("个人信息");
        titles.add("借阅历史");
        titles.add("借阅信息");
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));
        final List<Fragment> fragments = new ArrayList<>();
        fragmentHistory = new LibraryContentFragment();
        fragmentUserInfo = new LibraryContentFragment();
        fragmentBorrowInfo = new LibraryContentFragment();
        fragments.add(fragmentUserInfo);
        fragments.add(fragmentHistory);
        fragments.add(fragmentBorrowInfo);
        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = MainActivity.sharedPreferences.getString("userName", "none");
                password = MainActivity.sharedPreferences.getString("password", "none");
                nameValuePairsLoginLibrary.add(new BasicNameValuePair("passWord", password));
                nameValuePairsLoginLibrary.add(new BasicNameValuePair("userName", userName));
                loginLibrary(nameValuePairsLoginLibrary);

                progressDialogLoading.setMessage("正在查询请稍后");
                progressDialogLoading.setCancelable(false);
                progressDialogLoading.show();
                if (viewPager.getCurrentItem() == 0) {
                    UpdateFragmentUserInfo();
                } else if (viewPager.getCurrentItem() == 1) {
                    UpdateFragmentHistory();
                } else if (viewPager.getCurrentItem() == 2) {
                    UpdateFragmentBorrowInfo();
                }
            }
        });
    }

    private void UpdateFragmentHistory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String history = GetMyLibraryInfo.getMyBorrowHistory();
                if (history != "nothing") {
                    books = HtmlParserTools.parserHtml(history, "td");
                    Message message = new Message();
                    message.what = FRAGMENT_HISTORY_UPDATE;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void UpdateFragmentUserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String history = GetMyLibraryInfo.ToMyBookShelf();
                if (history != "nothing") {
                    userInfo = HtmlParserTools.parserHtmlNormal(history, "td");
                    Message message = new Message();
                    message.what = FRAGMENT_USERINFO_UPDATE;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void UpdateFragmentBorrowInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String info = GetMyLibraryInfo.libraryBorrowInfo();
                borrowedInfo = HtmlParserTools.parserHtmlForBookInfo(info, "td");
                Message message = new Message();
                message.what = FRAGMENT_BORROWINFO_UPDATE;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void loginLibrary(final List<NameValuePair> nameValuePairs){
        GetMyLibraryInfo.Init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetMyLibraryInfo.libraryLogin(nameValuePairs);
            }
        }).start();
    }
}
