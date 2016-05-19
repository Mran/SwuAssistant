package com.swuos.ALLFragment.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by ASUS on 2016/3/8.
 */
public class MyRecyclerViewAdapterUserinfo extends RecyclerView.Adapter<MyRecyclerViewAdapterUserinfo.MyViewHolderUser> {
    private Context mContext;
    private List<BookCell> books;

    MyRecyclerViewAdapterUserinfo(Context context, List<BookCell> books) {
        this.mContext = context;
        this.books = books;
    }

    @Override
    public MyViewHolderUser onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_recycler_view_userinfo, parent, false);
        return new MyViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderUser holder, int position) {
        holder.textViewTitle.setText(books.get(position).getBookName());
        holder.textViewContent.setText(books.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class MyViewHolderUser extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewContent;

        public MyViewHolderUser(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewLibraryContentItemUserTitle);
            textViewContent = (TextView) itemView.findViewById(R.id.textViewLibraryContentItemUserContent);
        }
    }
}
