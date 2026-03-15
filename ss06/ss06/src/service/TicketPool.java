package service;

import model.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketPool {

    private String roomName;
    private List<Ticket> tickets = new ArrayList<>();
    private int capacity;

    public TicketPool(String roomName, int capacity) {
        this.roomName = roomName;
        this.capacity = capacity;

        addTickets(capacity);
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

    public synchronized void addTickets(int count) {

        for (int i = 0; i < count; i++) {
            String id = roomName + "-" + UUID.randomUUID().toString().substring(0,5);
            tickets.add(new Ticket(id, roomName));
        }

        System.out.println("Supplier thêm " + count + " vé vào phòng " + roomName);

        notifyAll();
    }

    public synchronized int getAvailableCount() {

        int count = 0;

        for (Ticket t : tickets) {
            if (!t.isSold()) {
                count++;
            }
        }

        return count;
    }

    public String getRoomName() {
        return roomName;
    }
}