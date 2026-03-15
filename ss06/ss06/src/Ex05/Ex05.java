package Ex05;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
class Ticket {

    String ticketId;
    String roomName;

    boolean isSold = false;
    boolean isHeld = false;
    boolean isVIP;

    long holdExpiryTime = 0;

    public Ticket(String id, String room) {
        this.ticketId = id;
        this.roomName = room;
    }
}

class TicketPool {

    String roomName;
    List<Ticket> tickets = new ArrayList<>();

    public TicketPool(String roomName, int capacity) {

        this.roomName = roomName;

        for (int i = 1; i <= capacity; i++) {
            tickets.add(new Ticket(
                    roomName + "-" + String.format("%03d", i),
                    roomName
            ));
        }
    }

    public synchronized Ticket holdTicket(boolean vip) {

        for (Ticket t : tickets) {

            if (!t.isSold && !t.isHeld) {

                t.isHeld = true;
                t.isVIP = vip;
                t.holdExpiryTime = System.currentTimeMillis() + 5000;

                return t;
            }
        }

        return null;
    }

    public synchronized void sellHeldTicket(Ticket t) {

        if (t != null && t.isHeld && !t.isSold) {

            t.isSold = true;
            t.isHeld = false;
        }
    }

    public synchronized void releaseExpiredTickets() {

        long now = System.currentTimeMillis();

        for (Ticket t : tickets) {

            if (t.isHeld && !t.isSold && now > t.holdExpiryTime) {

                System.out.println(
                        "TimeoutManager: Vé " + t.ticketId + " hết hạn giữ, đã trả lại kho"
                );

                t.isHeld = false;
            }
        }
    }
}

class BookingCounter implements Runnable {

    String name;
    TicketPool[] pools;
    Random random = new Random();

    public BookingCounter(String name, TicketPool[] pools) {
        this.name = name;
        this.pools = pools;
    }

    @Override
    public void run() {

        while (true) {

            TicketPool pool = pools[random.nextInt(pools.length)];

            boolean vip = random.nextBoolean();

            Ticket ticket = pool.holdTicket(vip);

            if (ticket != null) {

                System.out.println(name + ": Đã giữ vé "
                        + ticket.ticketId +
                        (vip ? " (VIP)" : "")
                        + ". Vui lòng thanh toán trong 5s");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {}

                pool.sellHeldTicket(ticket);

                System.out.println(name + ": Thanh toán thành công vé " + ticket.ticketId);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}
class TimeoutManager implements Runnable {

    TicketPool[] pools;

    public TimeoutManager(TicketPool[] pools) {
        this.pools = pools;
    }

    @Override
    public void run() {

        while (true) {

            for (TicketPool pool : pools) {
                pool.releaseExpiredTickets();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}
public class Ex05 {

    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);
        TicketPool roomC = new TicketPool("C", 5);

        TicketPool[] pools = {roomA, roomB, roomC};

        for (int i = 1; i <= 5; i++) {

            Thread counter = new Thread(
                    new BookingCounter("Quầy " + i, pools)
            );

            counter.start();
        }

        Thread timeoutManager = new Thread(new TimeoutManager(pools));
        timeoutManager.start();
    }
}
