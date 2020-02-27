package com.apps.firebasechat;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class LoadingFragment extends DialogFragment {

    private static LoadingFragment fragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_loading, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    public synchronized static void show(AppCompatActivity activity) {
        hide();
        fragment = new LoadingFragment();
        fragment.setCancelable(false);
        fragment.show(activity.getSupportFragmentManager(), "Loading");
    }

    // Hide loading spinner fragment if one exists
    public synchronized static void hide() {
        if (fragment != null) {
            fragment.dismiss();
            fragment = null;
        }
    }
}
