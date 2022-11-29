package kz.uklombardapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import kz.uklombardapp.R;

public class UserPolicyFragment extends Fragment {
    private FragmentsEventsListener listener;

    public UserPolicyFragment(FragmentsEventsListener listener) {
        super(R.layout.user_policy_fragment);
        this.listener = listener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn_policy = view.findViewById(R.id.btn_policy);
//        TextView t_p = view.findViewById(R.id.t_p);
        //t_p.setText(getTermsString());
        btn_policy.setOnClickListener(v -> {
            SharedPreferences prefs = getActivity().getSharedPreferences("LOMBARD_SETTINGS", Context.MODE_PRIVATE);
            prefs.edit()
                    .putBoolean("isAllowed", true)
                    .apply();
            this.listener.policyAllowed();
        });
    }

    private String getTermsString() {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("policy.txt")));

            String str;
            while ((str = reader.readLine()) != null) {
                termsString.append(str);
            }

            reader.close();
            return termsString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
