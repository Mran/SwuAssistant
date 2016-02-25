package com.example.kk;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kk on 2016/2/25.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //拦截View的焦点，设置为true
    @Override
    public boolean isFocused() {
        return true;
    }
}
