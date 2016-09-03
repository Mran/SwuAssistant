package com.swuos.allfragment.library.libsearchs.search.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swuos.allfragment.library.libsearchs.search.model.bean.SearchBookItem;
import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/25.
 * Email:  645326280@qq.com
 */
public class RecycleAdapterSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SearchBookItem> searchBookItemList;
    private OnRecyclerItemClickedListener listener;
    private boolean mOpenLoadMore;
    private int FOOTER_VIEW = 1;
    private int ITEM_VIEW = 0;

    public RecycleAdapterSearch(Context context, List<SearchBookItem> searchBookItemList, boolean mOpenLoadMore) {
        this.context = context;
        this.searchBookItemList = searchBookItemList;
        this.mOpenLoadMore = mOpenLoadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW) {
            View searchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.searche_activity_item_layout, parent, false);
            return new SearchViewHodler(searchView);

        } else if (viewType == FOOTER_VIEW) {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_footer_view, parent, false);
            return new FooterViewHolder(footerView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SearchViewHodler) {
            ((SearchViewHodler) holder).textViewBookName.setText(searchBookItemList.get(position).getBookName());
            //holder.bookImage.setImageURI(searchbookItemList.get(position));
            ((SearchViewHodler) holder).textViewBookNumber.setText(searchBookItemList.get(position).getBookNumber());
            ((SearchViewHodler) holder).textViewBookISBN.setText(searchBookItemList.get(position).getISBN());
            ((SearchViewHodler) holder).textViewBooksuoshuhao.setText(searchBookItemList.get(position).getBookSuoshuhao());
            ((SearchViewHodler) holder).textViewBookWriter.setText(searchBookItemList.get(position).getWriter());
        } else if (holder instanceof FooterViewHolder) {
        }
    }


    @Override
    public int getItemCount() {
        return searchBookItemList.size();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickedListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterView(position)) {
            return FOOTER_VIEW;
        } else
            return ITEM_VIEW;

    }

    private boolean isFooterView(int position) {
        return mOpenLoadMore && position >= getItemCount() - 1;
    }

    public interface OnRecyclerItemClickedListener {
        void onItemClick(View view, int position);
    }

    public class SearchViewHodler extends RecyclerView.ViewHolder {
        private SimpleDraweeView bookImage;
        private AppCompatTextView textViewBookName;
        private AppCompatTextView textViewBookWriter;
        private AppCompatTextView textViewBooksuoshuhao;
        private AppCompatTextView textViewBookISBN;
        private AppCompatTextView textViewBookNumber;

        private View view;


        public SearchViewHodler(View itemView) {
            super(itemView);
            bookImage = (SimpleDraweeView) itemView.findViewById(R.id.bookimage);
            textViewBookName = (AppCompatTextView) itemView.findViewById(R.id.search_item_bookname);
            textViewBookWriter = (AppCompatTextView) itemView.findViewById(R.id.search_item_bookwriter);
            textViewBooksuoshuhao = (AppCompatTextView) itemView.findViewById(R.id.search_item_booksuoshuhao);
            textViewBookISBN = (AppCompatTextView) itemView.findViewById(R.id.search_item_bookisbn);
            textViewBookNumber = (AppCompatTextView) itemView.findViewById(R.id.search_item_number);
            view = itemView;
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.search_footer_progressBar);
        }
    }
}
