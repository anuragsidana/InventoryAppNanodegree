package com.example.anurag.nanodegreeinventoryapp.Alert;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.anurag.nanodegreeinventoryapp.Classes.MyApplication;

/**
 * Created by anurag on 6/26/2016.
 */
public class MyAlert extends DialogFragment {

    public static MyAlert newInstance(String name) {
        MyAlert f = new MyAlert();
        Bundle args = new Bundle();
        args.putString("name", name);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Item");
        builder.setMessage("It will delete your whole item details");
        builder.setCancelable(false);
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyApplication.getWritableDatabase().deleteItem(getArguments().getString("name"));
                getActivity().finish();
            }
        });

        Dialog dialog = builder.create();
        return dialog;
    }
}
