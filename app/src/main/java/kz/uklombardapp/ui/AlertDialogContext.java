package kz.uklombardapp.ui;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

public class AlertDialogContext {
    public static AlertDialogFragment dialog;
    public static void showDialog(AlertDialogEventsListener context,
                                  FragmentManager manager,
                                  String title,
                                  String msg,
                                  String tag) {
        dialog = new AlertDialogFragment(context);
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", msg);
        dialog.setArguments(args);
        dialog.show(manager, tag);
    }
}
