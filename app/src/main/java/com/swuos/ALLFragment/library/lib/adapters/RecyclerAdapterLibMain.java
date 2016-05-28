package com.swuos.ALLFragment.library.lib.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.swuos.ALLFragment.library.lib.model.BookItem;
import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/27.
 * Email:  645326280@qq.com
 */
public class RecyclerAdapterLibMain extends RecyclerView.Adapter<RecyclerAdapterLibMain.LibMainViewHolder> {
    private List<BookItem> bookItems;
    private Context mContext;
    private OnRecyclerItemClickedListener listener;

    public interface OnRecyclerItemClickedListener{
        void onClicked(View v, int position);
    }

    public RecyclerAdapterLibMain(Context context, List<BookItem> bookItems) {
        this.mContext = context;
        this.bookItems = bookItems;
    }

    @Override
    public LibMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_recycler_item, parent, false);

        return new LibMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LibMainViewHolder holder, final int position) {
        holder.textViewBookName.setText(bookItems.get(position).getBookName());
        holder.textViewBookTime.setText(bookItems.get(position).getTimeOut() + " － " + bookItems.get(position).getTimeBack());
        if (bookItems.get(position).getStatus() == BookItem.BACKED) {
            holder.textViewBacked.setVisibility(View.VISIBLE);
            holder.buttonRenew.setVisibility(View.GONE);
        } else {
            holder.textViewBacked.setVisibility(View.GONE);
            holder.buttonRenew.setVisibility(View.VISIBLE);
            holder.buttonRenew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClicked(v,position);
                }
            });
        }
    }

    public void setRecyclerItemClickedListener(OnRecyclerItemClickedListener listener){
        if(listener!=null){
            this.listener=listener;
        }
    }

    @Override
    public int getItemCount() {
        return bookItems.size();
    }

    public class LibMainViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewBookName;
        private TextView textViewBookTime;
        private TextView textViewBacked;
        private Button buttonRenew;

        public LibMainViewHolder(View itemView) {
            super(itemView);
            textViewBookName = (TextView) itemView.findViewById(R.id.textViewRecyclerLibBookName);
            textViewBookTime = (TextView) itemView.findViewById(R.id.textViewRecyclerLibTime);
            buttonRenew = (Button) itemView.findViewById(R.id.btnRecyclerLibRenew);
            textViewBacked = (TextView) itemView.findViewById(R.id.textViewRecyclerLibBacked);
        }
    }
}
