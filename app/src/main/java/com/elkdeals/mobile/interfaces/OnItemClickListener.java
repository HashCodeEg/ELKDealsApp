package com.elkdeals.mobile.interfaces;

import android.view.View;

public abstract class OnItemClickListener {

    Object data;
    View view;
    int position;

    private void OnItemClick() {

    }

    public void OnItemClick(Object data) {

    }

    private void OnItemClick(int position) {

    }

    private void OnItemClick(Object data, int position) {

    }

    private void OnItemClick(View view, int position) {

    }

    private void OnItemClick(Object data, View view, int position) {

    }

    private void OnItemClick(View view) {

    }

    public static class onIncrementClickListener extends OnItemClickListener {
        public onIncrementClickListener() {
        }
    }

    public static class onDecrementClickListener extends OnItemClickListener {
        public onDecrementClickListener() {
        }
    }

    public static class onAddProductsClickListener extends OnItemClickListener {
        public onAddProductsClickListener() {
        }
    }

}
