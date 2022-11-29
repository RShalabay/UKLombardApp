package kz.uklombardapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import kz.uklombardapp.R;
import kz.uklombardapp.models.Ticket;


public class TicketFragment extends Fragment {
    private FragmentsEventsListener listener;
    private Ticket ticket;

    public TicketFragment(FragmentsEventsListener listener, Ticket ticket) {
        super(R.layout.statement_fragment);
        this.listener = listener;
        this.ticket = ticket;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView d_ticket, d_date, d_sum, d_date_buy, d_sum_now, sum_date_buy, t_mass, d_percent, d_gesv;
        ListView d_lv;
        Button btn_kaspi;

        d_ticket = view.findViewById(R.id.d_ticket);
        d_date = view.findViewById(R.id.d_date);
        d_sum = view.findViewById(R.id.d_sum);
        d_date_buy = view.findViewById(R.id.d_date_buy);
        d_sum_now = view.findViewById(R.id.d_sum_now);
        sum_date_buy = view.findViewById(R.id.d_sum_date_buy);
        t_mass = view.findViewById(R.id.t_mass);
        d_percent = view.findViewById(R.id.d_percent);
        d_gesv = view.findViewById(R.id.d_gesv);

        d_lv = view.findViewById(R.id.d_lv);

        btn_kaspi = view.findViewById(R.id.btn_kaspi);

        d_ticket.setText(this.ticket.ticket.toString());
        //d_date.setText(this.ticket.date.toString());
        d_date.setText(format(this.ticket.date));
        d_sum.setText(String.format(Locale.FRANCE,"%,d", this.ticket.sum) + " тг.");
        d_date_buy.setText(format(this.ticket.date_buy));
        d_sum_now.setText(String.format(Locale.FRANCE,"%,d", this.ticket.sum_now) + " тг.");
        sum_date_buy.setText(String.format(Locale.FRANCE,"%,d", this.ticket.sum_date_buy) + " тг.");
        t_mass.setText(this.ticket.zalog.allMass);
        d_percent.setText(ticket.percent);
        d_gesv.setText(ticket.gesv + "%");

        d_lv.setAdapter(new ArrayAdapter<>(getContext(), R.layout.zalog_item, this.ticket.zalog.elems));

        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("LOMBARD_SETTINGS", Context.MODE_PRIVATE);
        String s = prefs.getString("login", "");

        btn_kaspi.setOnClickListener(view1 -> {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://kaspi.kz/pay/YKLOMBARD?service_id=4214&6513=" + s + "&6514=" + ticket.ticket + "&6515=30")
            );
            startActivity(intent);
        });
    }

    private String format(String value) {
        String[] v = value.split("-");
        return v[2] + "." + v[1] + "." + v[0] + " г.";
    }
}
