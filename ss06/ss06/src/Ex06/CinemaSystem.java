package Ex06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CinemaSystem {

    TicketPool[] pools;
    List<BookingCounter> counters = new ArrayList<>();
    ExecutorService executor;

    boolean running = false;

    public void start(int rooms, int ticketsPerRoom, int counterCount) {

        pools = new TicketPool[rooms];

        for (int i = 0; i < rooms; i++) {

            char roomName = (char) ('A' + i);

            pools[i] = new TicketPool(String.valueOf(roomName), ticketsPerRoom);
        }

        executor = Executors.newFixedThreadPool(counterCount);

        for (int i = 1; i <= counterCount; i++) {

            BookingCounter counter =
                    new BookingCounter("Quầy " + i, pools);

            counters.add(counter);

            executor.execute(counter);
        }

        running = true;

        System.out.println("Đã khởi tạo hệ thống với "
                + rooms + " phòng, "
                + (rooms * ticketsPerRoom) + " vé, "
                + counterCount + " quầy");
    }

    public void stop() {

        for (BookingCounter c : counters) {
            c.stop();
        }

        executor.shutdown();

        running = false;

        System.out.println("Đã tạm dừng tất cả quầy bán vé.");
    }

    public void statistics() {

        System.out.println("\n=== THỐNG KÊ HIỆN TẠI ===");

        int totalRevenue = 0;

        for (TicketPool p : pools) {

            int sold = p.soldCount();

            System.out.println("Phòng " + p.roomName
                    + ": Đã bán "
                    + sold + "/" + p.totalTickets() + " vé");

            totalRevenue += sold * 250000;
        }

        System.out.println("Tổng doanh thu: "
                + totalRevenue + " VNĐ");
    }
}