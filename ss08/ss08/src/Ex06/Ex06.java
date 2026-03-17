package Ex06;

import java.util.Scanner;

public class Ex06 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Chon kenh ban hang");
        System.out.println("1. Website");
        System.out.println("2. Mobile App");
        System.out.println("3. POS");

        int choice = sc.nextInt();

        SalesChannelFactory factory = null;

        switch (choice) {

            case 1:
                factory = new WebsiteFactory();
                System.out.println("Ban da chon kenh Website");
                break;

            case 2:
                factory = new MobileAppFactory();
                System.out.println("Ban da chon kenh Mobile App");
                break;

            case 3:
                factory = new POSFactory();
                System.out.println("Ban da chon kenh POS");
                break;

        }

        OrderService service = new OrderService(factory);

        System.out.print("Nhap gia san pham: ");
        double price = sc.nextDouble();

        System.out.print("Nhap so luong: ");
        int quantity = sc.nextInt();

        service.processOrder(price, quantity);

    }

}