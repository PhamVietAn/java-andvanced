package thread;

import model.Ticket;
import service.TicketPool;

import java.util.Random;

public class BookingCounter implements Runnable {

    private String counterName;
    private TicketPool roomA;
    private TicketPool roomB;
    private int totalSold = 0;

    private Random random = new Random();

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
    }

    @Override
    public void run() {

        try {

            while (true) {

                if (roomA.getAvailableCount() == 0 && roomB.getAvailableCount() == 0) {
                    System.out.println(counterName + " : Hết vé cả hai phòng -> dừng bán");
                    break;
                }

                TicketPool chosenRoom;

                if (random.nextBoolean()) {
                    chosenRoom = roomA;
                } else {
                    chosenRoom = roomB;
                }

                Ticket ticket = chosenRoom.sellTicket();

                if (ticket != null) {

                    totalSold++;

                    System.out.println(
                            counterName + " bán vé "
                                    + ticket.getTicketId()
                                    + " (" + ticket.getRoomName() + ")"
                    );

                } else {

                    System.out.println(counterName + ": Hết vé phòng " + chosenRoom.getRoomName());
                }

                Thread.sleep(500);

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int getTotalSold() {
        return totalSold;
    }

    public String getCounterName() {
        return counterName;
    }
}