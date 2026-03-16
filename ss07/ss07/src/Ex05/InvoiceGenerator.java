package Ex05;

public class InvoiceGenerator {

    public void printInvoice(Order order, double total, double finalAmount) {

        System.out.println("=== HÓA ĐƠN ===");
        System.out.println("Khách: " + order.getCustomer().getName());

        for (OrderItem item : order.getItems()) {
            System.out.println(
                    item.getProduct().getName() +
                            " - SL: " + item.getQuantity() +
                            " - Thành tiền: " + item.getTotal()
            );
        }

        System.out.println("Tổng tiền: " + total);
        System.out.println("Cần thanh toán: " + finalAmount);
    }
}