package com.swuos.ALLFragment.main_page;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.swuos.ALLFragment.FragmentControl;
import com.swuos.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/4/20.
 */
@Deprecated
public class ScheduleRecycleViewAdapter extends RecyclerView.Adapter<ScheduleRecycleViewAdapter.ScheduleRecycleViewHolder> {
    private LayoutInflater layoutInflater;
    private Context mContext;

    public ScheduleRecycleViewAdapter(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ScheduleRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScheduleRecycleViewHolder(layoutInflater.inflate(R.layout.main_page_schedule_recycleview, parent, false));
    }

    @Override
    public void onBindViewHolder(ScheduleRecycleViewHolder holder, int position) {
        holder.textView.setText("课程表");
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ScheduleRecycleViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;

        public ScheduleRecycleViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.main_page_schedule_text);
            this.button = (Button) itemView.findViewById(R.id.main_page_schedule_button);
            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentControl.fragmentSelection(R.id.nav_schedule);
                }
            });
        }
    }
}
