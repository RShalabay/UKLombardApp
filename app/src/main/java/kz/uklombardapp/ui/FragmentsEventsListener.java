package kz.uklombardapp.ui;

import kz.uklombardapp.models.Ticket;

public interface FragmentsEventsListener {
    void userIsChecked(String iin, boolean result);
    void codeIsChecked(String iin, String psw, boolean result, String consumer);
    void accessCodeIsSaved();
    void ticketHasSelected(Ticket ticket);
    void logout();
    void policyAllowed();
}
