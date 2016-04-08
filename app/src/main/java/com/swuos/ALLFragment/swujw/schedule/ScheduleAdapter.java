package com.swuos.ALLFragment.swujw.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.swuos.ALLFragment.swujw.schedule.util.ScheduleItem;
import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/3/4.
 */
/*自定义的ListView的适配器*/
@Deprecated
public class ScheduleAdapter extends ArrayAdapter<ScheduleItem>
{

    private int resourceId;

    public ScheduleAdapter(Context context, int textViewResourceId,
                           List<ScheduleItem> objects)
    {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ScheduleItem scheduleItem = getItem(position);
        View view;
//        View gradesItems=getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.kcmcs = (TextView) view.findViewById(R.id.kcmcs);
            viewHolder.xm = (TextView) view.findViewById(R.id.xm);
            viewHolder.jc = (TextView) view.findViewById(R.id.jc);
            viewHolder.xqjmc = (TextView) view.findViewById(R.id.xqjmc);
            viewHolder.zcd = (TextView) view.findViewById(R.id.zcd);
            viewHolder.cdmc = (TextView) view.findViewById(R.id.cdmc);


            view.setTag(viewHolder);
        } else
        {

            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.kcmcs.setText(scheduleItem.getKcmc());
        viewHolder.xqjmc.setText(scheduleItem.getXqjmc());
        viewHolder.jc.setText(scheduleItem.getJc());
        viewHolder.xm.setText(scheduleItem.getXm());
        viewHolder.zcd.setText(scheduleItem.getZcd());
        viewHolder.cdmc.setText(scheduleItem.getCdmc());


        return view;
    }

    class ViewHolder
    {
        TextView kcmcs;
        TextView xm;
        TextView jc;
        TextView xqjmc;
        TextView zcd;
        TextView cdmc;
    }

}