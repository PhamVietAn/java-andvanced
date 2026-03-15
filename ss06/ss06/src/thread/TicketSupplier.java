package thread;

import service.TicketPool;

public class TicketSupplier implements Runnable {

    private TicketPool roomA;
    private TicketPool roomB;

    private int supplyCount;
    private int interval;
    private int times;

    public TicketSupplier(TicketPool roomA, TicketPool roomB, int supplyCount, int interval, int times) {
        this.roomA = roomA;
        this.roomB = roomB;
        this.supplyCount = supplyCount;
        this.interval = interval;
        this.times = times;
    }

    @Override
    public void run() {

        try {

            for (int i = 0; i < times; i++) {

                Thread.sleep(interval);

                roomA.addTickets(supplyCount);
                roomB.addTickets(supplyCount);

                System.out.println("Nhà cung cấp đã thêm vé lần " + (i + 1));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}