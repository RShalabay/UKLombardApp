package kz.uklombardapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import kz.uklombardapp.R;
import kz.uklombardapp.domain.viewmodel.LoginViewModel;

public class InputAccessCodeFragment extends Fragment implements AlertDialogEventsListener {
    private TextInputEditText input;
    private TextInputLayout l_code;
    private Button button;
    public FragmentsEventsListener listener;
    private String iin;
    private String psw;
    private String consumer;

    public InputAccessCodeFragment(FragmentsEventsListener listener, String iin, String psw, String consumer) {
        super(R.layout.input_access_code_fragment);
        this.listener = listener;
        this.iin = iin;
        this.psw = psw;
        this.consumer = consumer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("LOMBARD_SETTINGS", Context.MODE_PRIVATE);
        String s = prefs.getString("code", "");

        CardView c_cnsm = view.findViewById(R.id.c_cnsm);

        if (prefs.getString("login", "").isEmpty()) {
            c_cnsm.setVisibility(View.GONE);
        }

        if (!s.isEmpty()) {
            TextView t = view.findViewById(R.id.t_ac);
            t.setVisibility(View.GONE);
        }

        ViewModelStoreOwner owner = this;
        l_code = view.findViewById(R.id.l_code);
        input = view.findViewById(R.id.textInputEditText);
        button = view.findViewById(R.id.textButton);
        button.setOnClickListener(view1 -> {
            l_code.setError(null);
            String accessCode = input.getText().toString();
            if (!accessCode.equals(s) && !s.isEmpty()) {
                l_code.setError("Неверный код доступа!");
            } else {
                if (s.isEmpty()) {
                    prefs.edit().putString("code", accessCode).apply();
                }
                LoginViewModel vm = new ViewModelProvider(owner).get(LoginViewModel.class);
                vm.saveUser(iin, psw, Integer.parseInt(accessCode), this.consumer);
                listener.accessCodeIsSaved();
            }
        });

        TextView t_cnsm = view.findViewById(R.id.t_cnsm);
        t_cnsm.setText(prefs.getString("consumer", ""));

        TextView tv_restore = view.findViewById(R.id.tv_restore);
        tv_restore.setOnClickListener(view1 -> {
            prefs.edit()
                    .putString("code", "")
                    .putString("login", "")
                    .putString("psw", "")
                    .putString("consumer", "")
                    .apply();
            listener.logout();
        });
    }

    @Override
    public void ok() {

    }
}
