package com.swuos.ALLFragment.swujw.grade.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.swuos.ALLFragment.swujw.grade.model.GradeItem;
import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/7/27.
 */
public class GradeDetaiAdapter extends ArrayAdapter<String[]> {

    int resourceId;
    private Context mContext;


    public GradeDetaiAdapter(Context context, int resource, List<String[]> objects) {
        super(context, resource, objects);
        mContext = context;
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String[] tmp = getItem(position);
        ViewHolders mViewHolder = new ViewHolders();
        View view = null;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId, null);
            mViewHolder.grade_detail_fx = (TextView) view.findViewById(R.id.grade_detail_fx);
            mViewHolder.grade_detail_proportion = (TextView) view.findViewById(R.id.grade_detail_proportion);
            mViewHolder.grade_detail_score = (TextView) view.findViewById(R.id.grade_detail_score);
            view.setTag(mViewHolder);
        } else {

            view = convertView;
            mViewHolder = (ViewHolders) view.getTag();
        }
        mViewHolder.grade_detail_fx.setText(tmp[0]);
        mViewHolder.grade_detail_proportion.setText(tmp[1]);
        mViewHolder.grade_detail_score.setText(tmp[2]);
        return view;
    }

    class ViewHolders {
        TextView grade_detail_fx;
        TextView grade_detail_proportion;
        TextView grade_detail_score;
    }
}
