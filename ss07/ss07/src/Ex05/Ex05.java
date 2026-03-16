package Ex05;

import java.util.*;

public class Ex05 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Product> products = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();

        OrderRepository repo = new FileOrderRepository();
        NotificationService notify = new EmailNotification();

        OrderService service = new OrderService(repo, notify);

        while(true){

            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Xem đơn hàng");
            System.out.println("4. Tính doanh thu");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            if(choice == 1){

                Product p = new Product("SP01","Laptop",15000000,"Điện tử");
                products.add(p);

                System.out.println("Đã thêm sản phẩm SP01");
            }

            else if(choice == 2){

                Customer c =
                        new Customer("Nguyễn Văn A",
                                "a@example.com",
                                "0123456789");

                customers.add(c);

                System.out.println("Đã thêm khách hàng");
            }

            else if(choice == 3){

                for(Order o : service.getOrders()){
                    System.out.println(
                            o.getId() + " - " +
                                    o.getCustomer().getName() +
                                    " - " +
                                    o.getFinalAmount()
                    );
                }
            }

            else if(choice == 4){

                double revenue = 0;

                for(Order o : service.getOrders()){
                    revenue += o.getFinalAmount();
                }

                System.out.println("Tổng doanh thu: " + revenue);
            }

            else if(choice == 0){
                break;
            }
        }
    }
}
