package com.swuos.ALLFragment.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by ASUS on 2016/3/12.
 */
public class MyRecyclerViewAdapter_BorrowedInfo extends RecyclerView.Adapter<MyRecyclerViewAdapter_BorrowedInfo.KKViewHolder> {

    private Context mContext;
    private List<BookInfo> bookInfos;

    MyRecyclerViewAdapter_BorrowedInfo(Context context, List<BookInfo> infos) {
        this.mContext = context;
        this.bookInfos = infos;
    }

    @Override
    public KKViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_recycler_view_item_borrow_info, parent, false);
        return new KKViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KKViewHolder holder, int position) {
        holder.textViewTitle.setText(bookInfos.get(position).getBookName());
        holder.textViewRenewTimes.setText("续借次数:" + bookInfos.get(position).getRenewTimes());
        holder.textViewTimeOut.setText("外借时间:" + bookInfos.get(position).getDateOut());
        holder.textViewTimeBack.setText("应归还时间:" + bookInfos.get(position).getDateBack());
        holder.buttonRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "续借功能还在测试中。。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookInfos.size();
    }

    public class KKViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewTimeOut;
        private TextView textViewTimeBack;
        private TextView textViewRenewTimes;
        private Button buttonRenew;

        public KKViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewLibraryItemBorrowInfoTitle);
            textViewTimeOut = (TextView) itemView.findViewById(R.id.textViewLibraryItemBorrowInfoOut);
            textViewTimeBack = (TextView) itemView.findViewById(R.id.textViewLibraryItemBorrowInfoBack);
            textViewRenewTimes = (TextView) itemView.findViewById(R.id.textViewLibraryItemBorrowInfoTimes);
            buttonRenew = (Button) itemView.findViewById(R.id.btnLibraryItemBorrowInfoRenew);
        }
    }
}
