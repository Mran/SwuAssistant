package com.swuos.ALLFragment.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by ASUS on 2016/3/11.
 */
public class LibraryContentFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<BookCell> books;
    private List<BookCell> userInfo;
    private List<BookInfo> borrowInfo;
    private MyRecyclerViewAdapter adapter;
    private MyRecyclerViewAdapter_BorrowedInfo adapter_borrowedInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView= (RecyclerView) inflater.inflate(R.layout.library_layout_content,container,false);
        return recyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void UpdateHistory(List<BookCell> cells){
        this.books=cells;
        Log.d("HttpLog","LibraryContentFragment Books Updated!!");
        for(BookCell ce:cells){
            Log.d("HttpLog","LibraryContentFragment cell===>"+ce.getBookName());
        }
        adapter=new MyRecyclerViewAdapter(getActivity(),cells);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void UpdateUserInfo(List<BookCell> info){
        this.userInfo=info;
        Log.d("HttpLog","LibraryContentFragment UserInfo Updated!!");
        for(BookCell s:userInfo){
            Log.d("HttpLog","LibraryContentFragment s===>"+s);
        }
        adapter=new MyRecyclerViewAdapter(getActivity(),userInfo);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void UpdateBorrowedInfo(List<BookInfo> info){
        this.borrowInfo=info;
        adapter_borrowedInfo=new MyRecyclerViewAdapter_BorrowedInfo(getActivity(),borrowInfo);
        recyclerView.setAdapter(adapter_borrowedInfo);
        adapter_borrowedInfo.notifyDataSetChanged();
    }
}
