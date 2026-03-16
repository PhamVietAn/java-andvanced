package ss07;

import java.time.LocalDate;

public class OrderService {
    String orderId;
    LocalDate createAt;
    String receiverName;
    String phone;

    void createOrder(){}

    void pay(PaymentMethod method){
            method.pay();
    }

    void saveOrderToDatabase(){}

}


