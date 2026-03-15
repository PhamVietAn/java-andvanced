package Ex03;

import java.util.LinkedList;
import java.util.Queue;
class Ticket {

    private String id;

    public Ticket(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}


class TicketPool {

    private Queue<Ticket> tickets = new LinkedList<>();
    private String roomName;
    private int nextTicket = 1;

    public TicketPool(String roomName, int count) {
        this.roomName = roomName;

        for (int i = 0; i < count; i++) {
            tickets.add(new Ticket(roomName + "-" + String.format("%03d", nextTicket++)));
        }
    }

    public synchronized Ticket sellTicket() {

        if (tickets.isEmpty()) {
            return null;
        }

        return tickets.poll();
    }

    public synchronized void addTickets(int count) {

        for (int i = 0; i < count; i++) {
            tickets.add(new Ticket(roomName + "-" + String.format("%03d", nextTicket++)));
        }

        System.out.println("Nhà cung cấp: Đã thêm " + count + " vé vào phòng " + roomName);
    }

    public synchronized int remainingTickets() {
        return tickets.size();
    }
}
class BookingCounter implements Runnable {

    private String name;
    private TicketPool pool;
    private int soldCount = 0;

    public BookingCounter(String name, TicketPool pool) {
        this.name = name;
        this.pool = pool;
    }

    public int getSoldCount() {
        return soldCount;
    }

    @Override
    public void run() {

        while (true) {

            Ticket ticket = pool.sellTicket();

            if (ticket != null) {
                soldCount++;
                System.out.println(name + " đã bán vé " + ticket.getId());
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
class TicketSupplier implements Runnable {

    private TicketPool roomA;
    private TicketPool roomB;
    private int supplyCount;
    private int interval;
    private int rounds;

    public TicketSupplier(TicketPool roomA, TicketPool roomB,
                          int supplyCount, int interval, int rounds) {

        this.roomA = roomA;
        this.roomB = roomB;
        this.supplyCount = supplyCount;
        this.interval = interval;
        this.rounds = rounds;
    }

    @Override
    public void run() {

        for (int i = 0; i < rounds; i++) {

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {}

            roomA.addTickets(supplyCount);
            roomB.addTickets(supplyCount);
        }
    }
}
public class Ex03 {

    public static void main(String[] args) throws InterruptedException {

        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);

        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomB);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        Thread supplier = new Thread(
                new TicketSupplier(roomA, roomB, 3, 3000, 3)
        );

        t1.start();
        t2.start();
        supplier.start();

        Thread.sleep(12000);

        t1.interrupt();
        t2.interrupt();

        System.out.println("\n--- KẾT QUẢ ---");
        System.out.println("Quầy 1 bán được: " + counter1.getSoldCount() + " vé");
        System.out.println("Quầy 2 bán được: " + counter2.getSoldCount() + " vé");
        System.out.println("Vé còn lại phòng A: " + roomA.remainingTickets());
        System.out.println("Vé còn lại phòng B: " + roomB.remainingTickets());
    }
}

