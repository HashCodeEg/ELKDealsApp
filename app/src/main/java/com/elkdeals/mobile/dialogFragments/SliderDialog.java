package com.elkdeals.mobile.dialogFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;

@SuppressLint("ValidFragment")
public class SliderDialog extends DialogFragment {


    String image;

    @SuppressLint("ValidFragment")
    public SliderDialog(String image) {
        this.image = image;
    }
  public SliderDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.profile_image_pop_up, null);
        ImageView profilePicImageView = view.findViewById(R.id.profilePicImageView);
        builder.setView(view);
        builder.setCancelable(false);
        Utils.loadImage(profilePicImageView,image);
        return builder.create();
    }
}
