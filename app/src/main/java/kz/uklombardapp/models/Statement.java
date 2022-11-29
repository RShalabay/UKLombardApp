package kz.uklombardapp.models;

import java.util.List;

public class Statement {
    public List<Ticket> tickets;
    public Integer summary_now;
    public Integer summary_date_buy;

    private Statement(List<Ticket> tickets, Integer summary_now, Integer summary_date_buy) {
        this.tickets = tickets;
        this.summary_now = summary_now;
        this.summary_date_buy = summary_date_buy;
    }

    public static Statement map(GetStatementResponse response) {
        return new Statement(response.tickets, response.summary_now, response.summary_date_buy);
    }
}
