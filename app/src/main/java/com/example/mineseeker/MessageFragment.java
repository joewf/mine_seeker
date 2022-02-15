package com.example.mineseeker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MessageFragment extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create the view to show
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.message_layout, null);

        // Create a button Listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        getActivity().finish();
                        break;
                }
                Log.i("TAG", "You clicked the dialog button");
            }
        };
        // Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.game_over)
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();


    }
}
