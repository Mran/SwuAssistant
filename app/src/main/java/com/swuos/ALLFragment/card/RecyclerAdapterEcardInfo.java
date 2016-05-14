package com.swuos.ALLFragment.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.swuos.swuassistant.R;

import java.util.List;


/**
 * Created by codekk on 2016/5/3.
 * Email:  645326280@qq.com
 */
public class RecyclerAdapterEcardInfo extends RecyclerView.Adapter<RecyclerAdapterEcardInfo.ViewHolderEcardInfo> {
    private Context context;
    private List<EcardInfo> ecardInfos;
    public RecyclerAdapterEcardInfo(Context context, List<EcardInfo> ecardInfos){
        this.context=context;
        this.ecardInfos=ecardInfos;
    }

    @Override
    public ViewHolderEcardInfo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recycler_item_ecardinfo,parent,false);
        return new ViewHolderEcardInfo(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderEcardInfo holder, int position) {
        holder.textViewName.setText(ecardInfos.get(position).getSubjectName());
        holder.textViewContent.setText(ecardInfos.get(position).getSubjectContent());
    }

    @Override
    public int getItemCount() {
        return ecardInfos.size();
    }

    public class ViewHolderEcardInfo extends RecyclerView.ViewHolder{
        private TextView textViewName;
        private TextView textViewContent;
        public ViewHolderEcardInfo(View itemView) {
            super(itemView);
            textViewName= (TextView) itemView.findViewById(R.id.textViewCardEcardInfoName);
            textViewContent= (TextView) itemView.findViewById(R.id.textViewCardEcardInfoContent);
        }
    }
}
