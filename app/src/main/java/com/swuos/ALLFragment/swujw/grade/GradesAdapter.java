package com.swuos.ALLFragment.swujw.grade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.swuos.ALLFragment.swujw.grade.util.GradeItem;
import com.swuos.swuassistant.R;

import java.util.List;
/*自定义的ListView的适配器*/
public class GradesAdapter extends ArrayAdapter<GradeItem>
{

    private int resourceId;

    public GradesAdapter(Context context, int textViewResourceId,
                         List<GradeItem> objects)
    {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        GradeItem gradeItem = getItem(position);
        View view;
//        View gradesItems=getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.gradesKcmc = (TextView) view.findViewById(R.id.kcmc);
            viewHolder.gradesCj = (TextView) view.findViewById(R.id.cj);
            viewHolder.gradesJd = (TextView) view.findViewById(R.id.jd);
            viewHolder.gradesXf = (TextView) view.findViewById(R.id.xf);

            view.setTag(viewHolder);
        } else
        {

            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.gradesKcmc.setText(gradeItem.getKcmc());
        viewHolder.gradesXf.setText(gradeItem.getXf());
        viewHolder.gradesJd.setText(gradeItem.getJd());
        viewHolder.gradesCj.setText(gradeItem.getCj());

        return view;
    }

    class ViewHolder
    {
        TextView gradesKcmc;
        TextView gradesCj;
        TextView gradesJd;
        TextView gradesXf;
    }

}
