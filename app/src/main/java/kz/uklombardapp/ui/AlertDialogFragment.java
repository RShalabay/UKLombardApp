package kz.uklombardapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private AlertDialogEventsListener listener;

    public AlertDialogFragment(AlertDialogEventsListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            String title = getArguments().getString("title");
            String msg = getArguments().getString("msg");
            String tag = getTag();
            builder.setMessage(msg)
                    .setTitle(title)
                    .setPositiveButton("OK", this);
            builder.setCancelable(true);
            return builder.create();
        }
        return null;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case Dialog.BUTTON_POSITIVE:
                //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                dialogInterface.cancel();
                //dismiss();
                listener.ok();
                break;
        }
    }
}