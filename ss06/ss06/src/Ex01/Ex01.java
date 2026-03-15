package Ex01;

import java.util.LinkedList;
import java.util.Queue;

class TicketPool {

    private Queue<String> tickets = new LinkedList<>();
    private String roomName;

    public TicketPool(String roomName, int count) {
        this.roomName = roomName;

        for (int i = 1; i <= count; i++) {
            tickets.add(roomName + "-" + String.format("%03d", i));
        }
    }

    public String getTicket() {
        return tickets.poll();
    }

    public void returnTicket(String ticket) {
        tickets.add(ticket);
    }

    public boolean hasTicket() {
        return !tickets.isEmpty();
    }

    public String getRoomName() {
        return roomName;
    }
}
class BookingCounter implements Runnable {

    private String name;
    private TicketPool roomA;
    private TicketPool roomB;

    public BookingCounter(String name, TicketPool roomA, TicketPool roomB) {
        this.name = name;
        this.roomA = roomA;
        this.roomB = roomB;
    }

    public void sellComboSafe() {

        TicketPool first = roomA;
        TicketPool second = roomB;

        // đảm bảo luôn lock theo thứ tự A -> B
        if (roomA.getRoomName().compareTo(roomB.getRoomName()) > 0) {
            first = roomB;
            second = roomA;
        }

        synchronized (first) {
            synchronized (second) {

                String ticket1 = roomA.getTicket();
                String ticket2 = roomB.getTicket();

                if (ticket1 == null || ticket2 == null) {

                    if (ticket1 != null) roomA.returnTicket(ticket1);
                    if (ticket2 != null) roomB.returnTicket(ticket2);

                    System.out.println(name + ": Bán combo thất bại");
                    return;
                }

                System.out.println(name + " bán combo thành công: "
                        + ticket1 + " & " + ticket2);
            }
        }
    }

    @Override
    public void run() {

        while (true) {

            sellComboSafe();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}



public class Ex01 {

    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A", 2);
        TicketPool roomB = new TicketPool("B", 2);

        // Quầy 1: khóa A -> B
        Thread counter1 = new Thread(
                new BookingCounter("Quầy 1", roomA, roomB)
        );

        // Quầy 2: khóa B -> A
        Thread counter2 = new Thread(
                new BookingCounter("Quầy 2", roomB, roomA)
        );

        counter1.start();
        counter2.start();
    }
}
