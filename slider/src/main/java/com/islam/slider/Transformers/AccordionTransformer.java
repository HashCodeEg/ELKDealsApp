package com.islam.slider.Transformers;

/**
 * Created by islam on 14-5-29.
 */
import android.view.View;

import androidx.core.view.ViewCompat;


public class AccordionTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        ViewCompat.setPivotX(view,position < 0 ? 0 : view.getWidth());
        ViewCompat.setScaleX(view,position < 0 ? 1f + position : 1f - position);
    }

}