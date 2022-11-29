package kz.uklombardapp.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import kz.uklombardapp.MainActivity;
import kz.uklombardapp.R;
import kz.uklombardapp.domain.viewmodel.CheckUserViewModel;

public class CheckUserFragment extends Fragment implements AlertDialogEventsListener {
    private TextInputEditText input;
    private TextInputLayout l_login;
    private Button button;
    public FragmentsEventsListener listener;

    public CheckUserFragment(FragmentsEventsListener listener) {
        super(R.layout.check_user_fragment);
        this.listener = listener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelStoreOwner owner = this;
        l_login = view.findViewById(R.id.l_login);
        input = view.findViewById(R.id.textInputEditText);
        button = view.findViewById(R.id.textButton);
        button.setOnClickListener(view1 -> {
            //Проверяем ИИН в базе
            l_login.setError(null);
            String iin = input.getText().toString();

            if (iin.isEmpty()) {
                l_login.setError("Не заполнен ИИН!");
                return;
            }

            CheckUserViewModel vm = new ViewModelProvider(owner).get(CheckUserViewModel.class);
            vm.checkUser(iin).observe(getViewLifecycleOwner(), s -> {
                //Если ИИН найден - просим ввести код из СМС
                if (s.equals("true")) {
                    listener.userIsChecked(iin, true);
                } else {
                    l_login.setError("Пользователя с таким ИИН не существует!");
                }
            });
        });

    }

    @Override
    public void ok() {

    }
}
