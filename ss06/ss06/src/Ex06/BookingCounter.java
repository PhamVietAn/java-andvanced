package Ex06;

import java.util.Random;

class BookingCounter implements Runnable {

    String name;
    TicketPool[] pools;
    boolean running = true;
    Random random = new Random();

    public BookingCounter(String name, TicketPool[] pools) {
        this.name = name;
        this.pools = pools;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {

        System.out.println(name + " bắt đầu bán vé...");

        while (running) {

            TicketPool pool = pools[random.nextInt(pools.length)];

            Ticket ticket = pool.sellTicket();

            if (ticket != null) {
                System.out.println(name + " đã bán vé " + ticket.ticketId);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
    }
}
