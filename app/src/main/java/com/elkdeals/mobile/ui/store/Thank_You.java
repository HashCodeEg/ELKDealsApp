package com.elkdeals.mobile.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.ui.BaseFragment;


public class Thank_You extends BaseFragment {

    private static final String TAG = "Thank_You";
    FrameLayout banner_adView;
    Button order_status_btn, continue_shopping_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.thank_you, container, false);
        // Binding Layout Views
        banner_adView = rootView.findViewById(R.id.banner_adView);
        order_status_btn = rootView.findViewById(R.id.order_status_btn);
        continue_shopping_btn = rootView.findViewById(R.id.continue_shopping_btn);

        // Binding Layout Views
        order_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                // Navigate to My_Orders Fragment
                BaseFragment fragment = new My_Orders();
                activity.removeTopFragment();
                activity.addPage(fragment);
                
            }
        });


        // Binding Layout Views
        continue_shopping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to HomePage Fragment
                BaseFragment fragment = new CategoriesStore();
                activity.removeTopFragment();
                activity.addPage(fragment);
            }
        });


        return rootView;
    }
    
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }

}

