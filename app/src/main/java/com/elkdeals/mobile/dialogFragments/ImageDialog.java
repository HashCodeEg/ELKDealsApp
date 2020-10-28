package com.elkdeals.mobile.dialogFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;

/**
 * Created by mohamed on 6/11/17.
 */

@SuppressLint("ValidFragment")
public class ImageDialog extends DialogFragment {

    public static final String TAG="ImageDialog";

    String image;
    String details;

    @SuppressLint("ValidFragment")
    public ImageDialog(String image) {
        this.image = image;
    }

    public ImageDialog(String image, String details) {
        this.image = image;
        this.details = details;
    }
   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.profile_image_pop_up,null);
        ImageView profilePicImageView = view.findViewById(R.id.profilePicImageView);
        builder.setView(view);
        builder.setCancelable(false);
        final byte[] bvytessf = Base64.decode(image, 1);
        final Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
        if (details!=null){
            Utils.loadImage(profilePicImageView,"http://elkdeals.com/admin/uploads/",image);
        }else {
            profilePicImageView.setImageBitmap(bmp3);
        }



        return builder.create();
    }
}
