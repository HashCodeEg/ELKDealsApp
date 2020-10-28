package com.elkdeals.mobile.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.elkdeals.mobile.ui.BaseFragment;

import java.util.ArrayList;
//import com.android.internal.

/**
 * Created by Islam on 10-Oct-17.
 */

public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final int FRAGMENT_ALR_EADY_ADDED = -2;
    private static final int FRAGMENT_IS_NULL = -3;
    private final ViewPager pager;
    private Context mContext;
    private ArrayList<BaseFragment> fragments;
    private ArrayList<String> titles;

    public SimpleFragmentPagerAdapter(FragmentManager fm, ViewPager pager) {
        super(fm);
        this.pager = pager;
    }

    /*public int add(BaseFragment fragment, String title) {
        int index=0;
        if(fragment==null)return FRAGMENT_IS_NULL;
        if(TextUtils.isEmpty(title))title="";
        if (fragments == null || titles == null) {
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
        }
        if (fragments.contains(fragment)) {
            Log.e("elk", "fragment has been added before,create new instance to avoid problems!");
            return FRAGMENT_ALR_EADY_ADDED;
        }
        boolean added=fragments.add(fragment);
        if(added)
            index=fragments.indexOf(fragment);
        titles.add(title);
        return index;

    }*/
    public int add(BaseFragment fragment, String title, boolean commitNow) {
        int index = 0;
        if (fragment == null) return FRAGMENT_IS_NULL;
        if (TextUtils.isEmpty(title)) title = "";
        if (fragments == null || titles == null) {
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
        }
        if (fragments.contains(fragment)) {
            Log.e("elk", "fragment has been added before,create new instance to avoid problems!");
            return FRAGMENT_ALR_EADY_ADDED;
        }
        boolean added = fragments.add(fragment);
        if (added)
            index = fragments.indexOf(fragment);
        titles.add(title);
        if (commitNow)
            notifyDataSetChanged();
        return index;

    }

    public int removeTop() {
        if (fragments != null && fragments.size() > 1) {
            fragments.remove(fragments.size() - 1);
            notifyDataSetChanged();
            return fragments.size() - 1;
        }
        return 0;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    // This determines the title for each tab
    @Override
    public String getPageTitle(int position) {
        return titles.get(position);
    }

    public void replace(int i, int item) {
        BaseFragment temp = fragments.get(i);
        fragments.set(i, fragments.get(item));
        fragments.set(item, temp);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        BaseFragment fragment = (BaseFragment) object;
        int index = fragments.indexOf(fragment);
        if (index == -1)
            return POSITION_NONE;
        if (pager != null && (pager.getCurrentItem() <= index))
            return POSITION_UNCHANGED;
        else return index;
    }

    public int getItemPositionByTAG(String tag) {
        if (fragments == null) return -1;
        for (int i = 0; i < fragments.size(); i++)
            if (fragments.get(i).getTAG().equals(tag))
                return i;
        return -1;
    }

}



