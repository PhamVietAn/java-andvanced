package Ex06;

public class WebsiteFactory implements SalesChannelFactory {

    public DiscountStrategy createDiscount() {
        return new WebsiteDiscount();
    }

    public PaymentMethod createPayment() {
        return new OnlineCardPayment();
    }

    public NotificationService createNotification() {
        return new EmailNotification();
    }

    public String getChannelName() {
        return "Website";
    }
}