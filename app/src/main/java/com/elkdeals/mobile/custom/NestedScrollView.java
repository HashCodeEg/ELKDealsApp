package com.elkdeals.mobile.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NestedScrollView extends androidx.core.widget.NestedScrollView {

    private boolean enableScrolling = true;

    public NestedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollView(Context context) {
        super(context);
    }

    public boolean isEnableScrolling() {
        return enableScrolling;
    }

    public void setEnableScrolling(boolean enableScrolling) {
        this.enableScrolling = enableScrolling;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (isEnableScrolling()) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isEnableScrolling()) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }
}
