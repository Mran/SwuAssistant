package com.example.ALLFragment.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swuassistant.R;

import java.util.List;

/**
 * Created by ASUS on 2016/3/8.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<BookCell> books;

    MyRecyclerViewAdapter(Context context, List<BookCell> books) {
        this.mContext = context;
        this.books = books;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_recycler_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewTitle.setText(books.get(position).getBookName());
        holder.textViewAuthor.setText(books.get(position).getAuthor());
        if (books.get(position).getAuthor().equals(" ")) {  //表示是用户信息
            holder.textViewTime.setText(books.get(position).getTime());
        }
        else {  //表示是借阅历史
            holder.textViewTime.setText("操作时间:\n" + books.get(position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAuthor;
        private TextView textViewTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewLibraryContentItemTitle);
            textViewAuthor = (TextView) itemView.findViewById(R.id.textViewLibraryContentItemSubtitle);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewLibraryContentItemTime);
        }
    }
}
