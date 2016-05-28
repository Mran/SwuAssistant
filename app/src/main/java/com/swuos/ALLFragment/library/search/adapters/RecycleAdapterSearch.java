package com.swuos.ALLFragment.library.search.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swuos.ALLFragment.library.search.model.BookInfoSearch;
import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/25.
 * Email:  645326280@qq.com
 */
public class RecycleAdapterSearch extends RecyclerView.Adapter<RecycleAdapterSearch.SearchViewHodler> {
    private Context context;
    private List<BookInfoSearch> bookInfoSearches;
    private OnRecyclerItemClickedListener listener;

    public interface OnRecyclerItemClickedListener {
        void onItemClick(View view, int position);
    }

    public RecycleAdapterSearch(Context context, List<BookInfoSearch> bookInfoSearches) {
        this.context = context;
        this.bookInfoSearches = bookInfoSearches;
    }

    @Override
    public SearchViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_search_recycler_item, parent, false);
        return new SearchViewHodler(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHodler holder, final int position) {
        holder.textViewBookName.setText(bookInfoSearches.get(position).getBookName());
        holder.textViewAuthor.setText(bookInfoSearches.get(position).getAuthor());
        holder.textViewCallNum.setText(bookInfoSearches.get(position).getCallNumber());
        holder.textViewIsbn.setText(bookInfoSearches.get(position).getIsbn());
        holder.textViewPublisher.setText(bookInfoSearches.get(position).getPublisher());
        holder.textViewBookkind.setText(bookInfoSearches.get(position).getBookKind());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookInfoSearches.size();
    }

    public class SearchViewHodler extends RecyclerView.ViewHolder {
        private TextView textViewBookName;
        private TextView textViewAuthor;
        private TextView textViewCallNum;
        private TextView textViewIsbn;
        private TextView textViewPublisher;
        private TextView textViewBookkind;
        private View view;


        public SearchViewHodler(View itemView) {
            super(itemView);
            textViewBookName = (TextView) itemView.findViewById(R.id.textViewSearchRecyclerBookName);
            textViewAuthor = (TextView) itemView.findViewById(R.id.textViewSearchRecyclerAuthor);
            textViewCallNum = (TextView) itemView.findViewById(R.id.textViewSearchRecyclerCallNum);
            textViewIsbn = (TextView) itemView.findViewById(R.id.textViewSearchRecyclerIsbn);
            textViewPublisher = (TextView) itemView.findViewById(R.id.textViewSearchRecyclerPublisher);
            textViewBookkind = (TextView) itemView.findViewById(R.id.textViewSearchRecyclerBookkind);
            view=itemView;
        }
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickedListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }
}
