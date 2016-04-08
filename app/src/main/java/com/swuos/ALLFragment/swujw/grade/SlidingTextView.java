package com.swuos.ALLFragment.swujw.grade;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kk on 2016/2/25.
 */
public class SlidingTextView extends TextView
{

    public SlidingTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SlidingTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    //拦截View的焦点，设置为true
    @Override
    public boolean isFocused()
    {
        return true;
    }
}
