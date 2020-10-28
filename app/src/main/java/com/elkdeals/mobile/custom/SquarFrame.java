package com.elkdeals.mobile.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SquarFrame extends FrameLayout {

public SquarFrame(final Context context) {
    super(context);
}

public SquarFrame(final Context context, final AttributeSet attrs) {
    super(context, attrs);
}

public SquarFrame(final Context context, final AttributeSet attrs,
                  final int defStyle) {
    super(context, attrs, defStyle);
}

@Override
protected void onMeasure(int width, int height) {
    super.onMeasure(width, height);
    int measuredWidth = getMeasuredWidth();
    int measuredHeight = getMeasuredHeight();
    if (measuredWidth > measuredHeight) {
        setMeasuredDimension(measuredWidth, measuredWidth);
    } else {
        setMeasuredDimension(measuredHeight, measuredHeight);

    }

}

}