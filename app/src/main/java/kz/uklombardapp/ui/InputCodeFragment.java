package kz.uklombardapp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.Locale;

import kz.uklombardapp.R;
import kz.uklombardapp.domain.viewmodel.CheckUserViewModel;
import kz.uklombardapp.domain.viewmodel.LoginViewModel;


public class InputCodeFragment extends Fragment implements AlertDialogEventsListener {
    private TextInputEditText input;
    private TextInputLayout l_sms;
    private Button button;
    private CountDownTimer mChronometer;
    public FragmentsEventsListener listener;

    private boolean timerRunning;

    public String iin;

    private static final long START_TIME_IN_MILLIS = 300000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView chrinotemer;

    public InputCodeFragment(FragmentsEventsListener listener, String iin) {
        super(R.layout.input_code_fragment);
        this.listener = listener;
        this.iin = iin;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chrinotemer = view.findViewById(R.id.chronometer);

        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }

        ViewModelStoreOwner owner = this;
        l_sms = view.findViewById(R.id.l_sms);
        input = view.findViewById(R.id.textInputEditText);
        button = view.findViewById(R.id.textButton);
        button.setEnabled(true);
        button.setOnClickListener(view1 -> {
            l_sms.setError(null);
            String code = input.getText().toString();
            LoginViewModel vm = new ViewModelProvider(owner).get(LoginViewModel.class);
            vm.login(this.iin, code).observe(getViewLifecycleOwner(), s -> {
                if (s.success) {
                    Context context = getActivity();
                    SharedPreferences prefs = context.getSharedPreferences("LOMBARD_SETTINGS", Context.MODE_PRIVATE);
                    prefs.edit()
                            .putString("login", iin)
                            .putString("psw", s.psw)
                            .putString("consumer", s.consumer)
                            .apply();
                    listener.codeIsChecked(iin, s.psw, true, s.consumer);
                } else {
                    l_sms.setError(s.msg);
                    stopTimer();
                }
            });
        });
        //Запрашиваем код заново
        TextView tv_regen = view.findViewById(R.id.tv_regen);
        tv_regen.setOnClickListener(view1 -> {
            resetTimer();
            startTimer();
            CheckUserViewModel viewModel = new ViewModelProvider(owner).get(CheckUserViewModel.class);
            viewModel.checkUser(iin).observe(getViewLifecycleOwner(), s -> {
                if (s.equals("true")) {
                    button.setEnabled(true);
                    l_sms.setError(null);
                }
            });
        });
    }

    private void startTimer() {
        mChronometer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                l_sms.setError("Время действия кода истекло!");
                button.setEnabled(false);
            }
        }.start();
        timerRunning = true;
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        chrinotemer.setText(timeLeftFormatted);
    }

    private void stopTimer() {
        mChronometer.cancel();
        timerRunning = false;
    }

    private void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateTimerText();
    }

    @Override
    public void ok() {

    }
}
