package kz.uklombardapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Locale;


import kz.uklombardapp.R;
import kz.uklombardapp.domain.repos.UserRepository;
import kz.uklombardapp.domain.utils.StatementListAdapter;
import kz.uklombardapp.domain.viewmodel.GetStatementViewModel;
import kz.uklombardapp.models.Ticket;
import kz.uklombardapp.models.User;

public class StatementListFragment extends Fragment implements AlertDialogEventsListener {
    public FragmentsEventsListener listener;
    private List<Ticket> tickets;
    private UserRepository repo;
    private ListView list;
    private Ticket selectedTicket;

    public StatementListFragment(FragmentsEventsListener listener) {
        super(R.layout.list);
        this.listener = listener;
        this.repo = new UserRepository(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("LOMBARD_SETTINGS", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        StatementListFragment owner = this;
        User user = repo.getUser();
        GetStatementViewModel vm = new ViewModelProvider(this).get(GetStatementViewModel.class);
        vm.getStatement(user.iin, user.pass, token).observe(getViewLifecycleOwner(), response -> {
            if (response.success) {
                owner.tickets = response.tickets;
                TextView consumer = view.findViewById(R.id.consumer);
                TextView sum = view.findViewById(R.id.sum);
                TextView date = view.findViewById(R.id.date);
                consumer.setText(response.consumer);
                sum.setText(String.format(Locale.FRANCE,"%,d", response.summary_now) + " тг.");
                date.setText(String.format(Locale.FRANCE,"%,d", response.summary_date_buy) + " тг.");

                list = view.findViewById(R.id.list);
                list.setAdapter(new StatementListAdapter(getContext(), R.layout.statement_item, owner.tickets, listener));
            } else {
                AlertDialogContext.showDialog(this,
                        getActivity().getSupportFragmentManager(),
                        "Внимание",
                        response.msg,
                        "LOGIN_ERROR");
            }
        });
    }

    @Override
    public void ok() {

    }
}
