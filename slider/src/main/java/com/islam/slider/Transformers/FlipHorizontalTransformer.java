package com.islam.slider.Transformers;

import android.view.View;

import androidx.core.view.ViewCompat;


public class FlipHorizontalTransformer extends BaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		final float rotation = 180f * position;
        ViewCompat.setAlpha(view,rotation > 90f || rotation < -90f ? 0 : 1);
        ViewCompat.setPivotY(view,view.getHeight()*0.5f);
		ViewCompat.setPivotX(view,view.getWidth() * 0.5f);
		ViewCompat.setRotationY(view,rotation);
	}

}
