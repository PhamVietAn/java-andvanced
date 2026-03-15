package Ex06;

import java.util.ArrayList;
import java.util.List;

class TicketPool {

    String roomName;
    List<Ticket> tickets = new ArrayList<>();

    public TicketPool(String roomName, int count) {

        this.roomName = roomName;

        for (int i = 1; i <= count; i++) {
            tickets.add(new Ticket(
                    roomName + "-" + String.format("%03d", i)
            ));
        }
    }

    public synchronized Ticket sellTicket() {

        for (Ticket t : tickets) {
            if (!t.isSold) {
                t.isSold = true;
                return t;
            }
        }

        return null;
    }

    public int soldCount() {

        int c = 0;

        for (Ticket t : tickets) {
            if (t.isSold) c++;
        }

        return c;
    }

    public int totalTickets() {
        return tickets.size();
    }
}
