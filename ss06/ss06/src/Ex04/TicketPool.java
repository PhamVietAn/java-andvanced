package Ex04;

import java.util.ArrayList;
import java.util.List;

class TicketPool {

    private String roomName;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketPool(String roomName, int count) {
        this.roomName = roomName;

        for (int i = 1; i <= count; i++) {
            tickets.add(new Ticket(
                    roomName + "-" + String.format("%03d", i),
                    roomName
            ));
        }
    }

    public synchronized Ticket sellTicket() {

        for (Ticket t : tickets) {
            if (!t.isSold()) {
                t.setSold(true);
                return t;
            }
        }

        return null;
    }

    public synchronized int remainingTickets() {

        int count = 0;

        for (Ticket t : tickets) {
            if (!t.isSold()) count++;
        }

        return count;
    }
}
