package kz.uklombardapp.models;

import java.util.List;

public class GetStatementResponse extends LoginResponse{
    public List<Ticket> tickets;
    public Integer summary_now;
    public Integer summary_date_buy;

    public GetStatementResponse(boolean success,
                                String msg,
                                String iin,
                                String consumer,
                                String pass,
                                List<Ticket> tickets,
                                Integer summary_now,
                                Integer summary_date_buy) {
        super(success, msg, iin, consumer, pass);
        this.tickets = tickets;
        this.summary_now = summary_now;
        this.summary_date_buy = summary_date_buy;
    }
}
