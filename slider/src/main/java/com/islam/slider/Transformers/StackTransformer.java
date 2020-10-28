package com.islam.slider.Transformers;

import android.view.View;

import androidx.core.view.ViewCompat;


public class StackTransformer extends BaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		ViewCompat.setTranslationX(view,position < 0 ? 0f : -view.getWidth() * position);
	}

}
