package com.example.gymfinder.ui.finder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.gymfinder.R;

public class Dialog extends DialogFragment {

    private String message = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString("message");
        }
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog, null);
        TextView messageTextView = view.findViewById(R.id.alert_message);
        messageTextView.setText(message);
        builder.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
        builder.setTitle("Информация о пользователе.");
        return builder.create();
    }

    public static Dialog newInstance(String message) {
        Dialog fragment = new Dialog();
        Bundle args = new Bundle();
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }
}
