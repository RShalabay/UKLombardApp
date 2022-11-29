package kz.uklombardapp.domain.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.List;
import java.util.Locale;

import kz.uklombardapp.R;
import kz.uklombardapp.models.Ticket;
import kz.uklombardapp.ui.FragmentsEventsListener;


public class StatementListAdapter extends ArrayAdapter<Ticket> {
    private final int layout;
    private final LayoutInflater inflater;
    private final List<Ticket> tickets;
    private final FragmentsEventsListener listener;

    public StatementListAdapter(Context context, int resource, List<Ticket> tickets, FragmentsEventsListener listener) {
        super(context, resource, tickets);
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.tickets = tickets;
        this.listener = listener;
    }

    public View getView(int position, View currentView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (currentView == null) {
            currentView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(currentView);
            currentView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) currentView.getTag();
        }
        TextView ticket = currentView.findViewById(R.id.ticket);
        TextView sum_now = currentView.findViewById(R.id.sum_now);

        Ticket ticket1 = tickets.get(position);

        if (!ticket1.isOverdue) {
            viewHolder.isOverdue.setVisibility(View.GONE);
        }

        viewHolder.ticket.setText(ticket1.ticket);
        viewHolder.sum_now.setText(String.format(Locale.FRANCE,"%,d", ticket1.sum_now) + " тг.");

        Button open = currentView.findViewById(R.id.btn_open);
        open.setOnClickListener(view -> listener.ticketHasSelected(ticket1));

        return currentView;
    }

    private class ViewHolder {
        final TextView ticket, sum_now, isOverdue;
        final Button open;

        ViewHolder(View view) {
            ticket = view.findViewById(R.id.ticket);
            sum_now = view.findViewById(R.id.sum_now);
            open = view.findViewById(R.id.btn_open);
            isOverdue = view.findViewById(R.id.isOverdue);
        }
    }

}
