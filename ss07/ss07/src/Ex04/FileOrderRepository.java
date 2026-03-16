package Ex04;

import java.util.ArrayList;
import java.util.List;

public class FileOrderRepository implements OrderRepository {

    private List<Order> orders = new ArrayList<>();

    @Override
    public void save(Order order) {
        orders.add(order);
        System.out.println("Lưu đơn hàng vào file: " + order.getId());
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }
}
