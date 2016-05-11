package com.swuos.ALLFragment.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by codekk on 2016/5/4.
 * Email:  645326280@qq.com
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS=new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    public MyItemDecoration(Context context) {
        TypedArray typedArray=context.obtainStyledAttributes(ATTRS);
        mDivider=typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left=parent.getPaddingLeft();
        int right=parent.getWidth()-parent.getPaddingLeft();
        int count=parent.getChildCount();
        for(int i=0;i<count;i++){
            View view=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) view.getLayoutParams();
            int top = view.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
