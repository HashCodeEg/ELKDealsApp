package com.elkdeals.mobile.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.SimpleFragmentPagerAdapter;

public class ViewPager extends androidx.viewpager.widget.ViewPager {

    SimpleFragmentPagerAdapter adapter;
    private boolean swipeable;

    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom);
        try {
            swipeable = a.getBoolean(R.styleable.custom_swipeable, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        if (adapter instanceof SimpleFragmentPagerAdapter)
            this.adapter = (SimpleFragmentPagerAdapter) adapter;
        super.setAdapter(adapter);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipeable && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeable && super.onTouchEvent(event);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (item >= adapter.getCount() || item < 0 || item == getCurrentItem()) return;
        /*if(getCurrentItem()!=adapter.getCount()-1&&item>getCurrentItem())
        {
            adapter.replace(getCurrentItem()+1,item);
        }
        else adapter.replace(getCurrentItem()-1,item);
        adapter.notifyDataSetChanged();*/
        super.setCurrentItem(item, smoothScroll);
    }
}
