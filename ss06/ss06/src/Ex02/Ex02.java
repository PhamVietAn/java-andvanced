package Ex02;

import java.util.LinkedList;
import java.util.Queue;

class TicketPool {

    private Queue<String> tickets = new LinkedList<>();
    private String roomName;
    private int nextTicketNumber = 1;

    public TicketPool(String roomName, int count) {
        this.roomName = roomName;

        for (int i = 1; i <= count; i++) {
            tickets.add(roomName + "-" + String.format("%03d", i));
            nextTicketNumber++;
        }
    }

    public synchronized String sellTicket(String counterName) {

        while (tickets.isEmpty()) {
            try {
                System.out.println(counterName + ": Hết vé phòng " + roomName + ", đang chờ...");
                wait(); // thread ngủ
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String ticket = tickets.poll();
        System.out.println(counterName + " bán vé " + ticket);

        return ticket;
    }

    public synchronized void addTickets(int count) {

        for (int i = 0; i < count; i++) {
            tickets.add(roomName + "-" + String.format("%03d", nextTicketNumber++));
        }

        System.out.println("Nhà cung cấp: Đã thêm " + count + " vé vào phòng " + roomName);

        notifyAll(); // đánh thức tất cả thread đang wait
    }
}
class BookingCounter implements Runnable {

    private String name;
    private TicketPool pool;

    public BookingCounter(String name, TicketPool pool) {
        this.name = name;
        this.pool = pool;
    }

    @Override
    public void run() {

        while (true) {
            pool.sellTicket(name);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
class TicketSupplier implements Runnable {

    private TicketPool pool;

    public TicketSupplier(TicketPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {}

        pool.addTickets(3);
    }
}
public class Ex02 {

    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A", 3);
        TicketPool roomB = new TicketPool("B", 5);

        Thread counter1 = new Thread(new BookingCounter("Quầy 1", roomA));
        Thread counter2 = new Thread(new BookingCounter("Quầy 2", roomB));

        Thread supplier = new Thread(new TicketSupplier(roomA));

        counter1.start();
        counter2.start();
        supplier.start();
    }
}
