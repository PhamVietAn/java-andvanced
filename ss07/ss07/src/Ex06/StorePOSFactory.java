package Ex06;

public class StorePOSFactory implements SalesChannelFactory {

    public DiscountStrategy createDiscount() {
        return new MemberDiscount();
    }

    public PaymentMethod createPayment() {
        return new CODPayment();
    }

    public NotificationService createNotification() {
        return new PrintInvoiceNotification();
    }

    public String getChannelName() {
        return "Store POS";
    }
}