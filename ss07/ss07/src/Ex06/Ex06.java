package Ex06;

import java.util.Scanner;

public class Ex06 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Chọn kênh bán");
        System.out.println("1. Website");
        System.out.println("2. Mobile App");
        System.out.println("3. POS");

        int choice = sc.nextInt();

        SalesChannelFactory factory = null;

        if(choice == 1){
            factory = new WebsiteFactory();
        }
        else if(choice == 2){
            factory = new MobileAppFactory();
        }
        else if(choice == 3){
            factory = new StorePOSFactory();
        }

        System.out.println("Bạn đã chọn kênh " + factory.getChannelName());

        DiscountStrategy discount = factory.createDiscount();
        PaymentMethod payment = factory.createPayment();
        NotificationService notify = factory.createNotification();

        OrderService service = new OrderService();

        service.processOrder(15000000, discount, payment, notify);
    }
}   
