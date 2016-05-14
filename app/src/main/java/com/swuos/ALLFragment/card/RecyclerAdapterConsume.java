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
public class RecyclerAdapterConsume extends RecyclerView.Adapter<RecyclerAdapterConsume.ViewHolderConsumeInfo> {
    private Context context;
    private List<ConsumeInfo> consumeInfos;
    public RecyclerAdapterConsume(Context context, List<ConsumeInfo> consumeInfos){
        this.context=context;
        this.consumeInfos=consumeInfos;
    }

    @Override
    public ViewHolderConsumeInfo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recycler_item_consumeinfo,parent,false);
        return new ViewHolderConsumeInfo(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderConsumeInfo holder, int position) {
       holder.textViewKind.setText(consumeInfos.get(position).getKind());
        holder.textViewAddress.setText(consumeInfos.get(position).getAddress());
        holder.textViewTime.setText(consumeInfos.get(position).getTime());
        holder.textViewBefore.setText(consumeInfos.get(position).getBefore());
        holder.textViewDelta.setText(consumeInfos.get(position).getDelta());
        holder.textViewAfter.setText(consumeInfos.get(position).getAfter());
    }
    @Override
    public int getItemCount() {
        return consumeInfos.size();
    }

    public class ViewHolderConsumeInfo extends RecyclerView.ViewHolder{
        private TextView textViewKind;
        private TextView textViewAddress;
        private TextView textViewTime;
        private TextView textViewBefore;
        private TextView textViewDelta;
        private TextView textViewAfter;
        public ViewHolderConsumeInfo(View itemView) {
            super(itemView);
            textViewKind= (TextView) itemView.findViewById(R.id.textViewCardConsumeKind);
            textViewAddress= (TextView) itemView.findViewById(R.id.textViewCardConsumeAddress);
            textViewTime= (TextView) itemView.findViewById(R.id.textViewCardConsumeTime);
            textViewBefore= (TextView) itemView.findViewById(R.id.textViewCardConsumeBefore);
            textViewDelta= (TextView) itemView.findViewById(R.id.textViewCardConsumeDelta);
            textViewAfter= (TextView) itemView.findViewById(R.id.textViewCardConsumeAfter);
        }
    }
}
