package com.example.tool;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.swuassistant.R;

import java.util.List;
/*自定义的ListView的适配器*/
public class GradesAdapter extends ArrayAdapter<Grades>
{

    private int resourceId;

    public GradesAdapter(Context context, int textViewResourceId,
                         List<Grades> objects)
    {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Grades grades = getItem(position);
        View view;
//        View gradesItems=getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
//			viewHolder.fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            viewHolder.gradesKcmc = (TextView) view.findViewById(R.id.kcmc);
            viewHolder.gradesCj = (TextView) view.findViewById(R.id.cj);
            viewHolder.gradesJd = (TextView) view.findViewById(R.id.jd);
            viewHolder.gradesXf = (TextView) view.findViewById(R.id.xf);

            view.setTag(viewHolder);
        } else
        {
//            if (position == -1)
//            {
//                convertView.setBackgroundColor(Color.parseColor("#3F51B5"));
//            } else if (position == (parent.getChildCount() - 2))
//            {
//                convertView.setBackgroundColor(Color.parseColor("#3F51B5"));
//            }else if (position == (parent.getChildCount() - 1));
//            {
//                convertView.setBackgroundColor(Color.parseColor("#3F51B5"));
//
//            }
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
//		viewHolder.fruitImage.setImageResource(grades.getImageId());
        viewHolder.gradesKcmc.setText(grades.getKcmc());
        viewHolder.gradesXf.setText(grades.getXf());
        viewHolder.gradesJd.setText(grades.getJd());
        viewHolder.gradesCj.setText(grades.getCj());

        return view;
    }

    class ViewHolder
    {

//		ImageView fruitImage;

        TextView gradesKcmc;
        TextView gradesCj;
        TextView gradesJd;
        TextView gradesXf;


    }

}
