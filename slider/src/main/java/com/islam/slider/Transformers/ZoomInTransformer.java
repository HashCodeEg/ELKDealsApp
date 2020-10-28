package com.islam.slider.Transformers;

import android.view.View;

import androidx.core.view.ViewCompat;


public class ZoomInTransformer extends BaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		final float scale = position < 0 ? position + 1f : Math.abs(1f - position);
		ViewCompat.setScaleX(view,scale);
        ViewCompat.setScaleY(view,scale);
        ViewCompat.setPivotX(view,view.getWidth() * 0.5f);
        ViewCompat.setPivotY(view,view.getHeight() * 0.5f);
        ViewCompat.setAlpha(view,position < -1f || position > 1f ? 0f : 1f - (scale - 1f));
	}

}
