package com.example.felix.dialogapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SignInDialogFragment extends DialogFragment {
    private OnCredentialEnteredListener mListener;

    public SignInDialogFragment() {
        // Required empty public constructor
    }

    public static SignInDialogFragment newInstance() {
        SignInDialogFragment fragment = new SignInDialogFragment();
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_signin_dialog, null);

        final EditText usernameText = (EditText) view.findViewById(R.id.username);
        // set dialog icon
        builder.setIcon(android.R.drawable.btn_dialog)
                // set Dialog Title
                .setTitle("Sign in:")
                // Set Dialog Message
                .setView(view)
                // positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.authenticate(usernameText.getText().toString());
                        }
                        dialog.dismiss();
                    }
                })
                // negative button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnCredentialEnteredListener) {
            mListener = (OnCredentialEnteredListener) context;
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


    public interface OnCredentialEnteredListener {
        void authenticate(String username);
    }
}