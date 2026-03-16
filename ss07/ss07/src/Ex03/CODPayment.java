package Ex03;

public class CODPayment implements CODPayable {

    @Override
    public void processCOD(double amount) {
        System.out.println("Xử lý thanh toán COD: " + (long)amount + " - Thành công");
    }
}