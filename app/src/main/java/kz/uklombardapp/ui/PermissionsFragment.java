package kz.uklombardapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.uklombardapp.R;

public class PermissionsFragment extends Fragment {
    private FragmentsEventsListener listener;

    public PermissionsFragment(FragmentsEventsListener listener) {
        super(R.layout.permissoins_allow_fragment);
        this.listener = listener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn_permission = view.findViewById(R.id.btn_permission);
        btn_permission.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, new UserPolicyFragment(this.listener), null)
                    .commit();
        });
    }
}
