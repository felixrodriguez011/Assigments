package com.example.felix.dialogapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

public class MyDialogFragment extends DialogFragment {

    private OnMyDialogConfirmedListener mListener;

    public MyDialogFragment() {
        // Required empty public constructor
    }

    public static MyDialogFragment newInstance() {
        MyDialogFragment fragment = new MyDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // set dialog icon
                .setIcon(android.R.drawable.btn_dialog)
                // set Dialog Title
                .setTitle("Important!")
                // Set Dialog Message
                .setMessage("This is Dialog One")

                // positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onDialogConfirmed();
                        }
                        dialog.dismiss();
                    }
                })
                // negative button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyDialogConfirmedListener) {
            mListener = (OnMyDialogConfirmedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnMyDialogConfirmedListener {
        void onDialogConfirmed();
    }
}