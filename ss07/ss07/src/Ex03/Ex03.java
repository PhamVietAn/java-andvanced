package Ex03;

public class Ex03 {

    public static void main(String[] args) {

        PaymentProcessor processor = new PaymentProcessor();

        System.out.println("COD");
        processor.processPayment(new CODPayment(), 500000);

        System.out.println();

        System.out.println("Thẻ tín dụng");
        processor.processPayment(new CreditCardPayment(), 1000000);

        System.out.println();

        System.out.println("Ví MoMo");
        processor.processPayment(new MomoPayment(), 750000);

        System.out.println();
        System.out.println("Kiểm tra LSP");

        // Thay thế implementation khác
        CardPayable payment1 = new CreditCardPayment();
        payment1.processCreditCard(1000000);

        // nếu sau này có class khác implement CardPayable
        // thì có thể thay thế mà chương trình vẫn chạy đúng
    }
}