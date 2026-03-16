package Ex01;

public class Ex01 {
    public static void main(String[] args) {

        System.out.println("Tạo sản phẩm: SP01 - Laptop - 15000000, SP02 - Chuột - 300000");

        Product p1 = new Product("SP01", "Laptop", 15000000);
        Product p2 = new Product("SP02", "Chuột", 300000);

        System.out.println("Đã thêm sản phẩm SP01, SP02");

        Customer customer = new Customer("Nguyễn Văn A", "a@example.com", "Hà Nội");

        System.out.println("Tạo khách hàng: Nguyễn Văn A - a@example.com");
        System.out.println("Đã thêm khách hàng");

        Order order = new Order("ORD001", customer);

        order.addProduct(p1, 1);
        order.addProduct(p2, 2);

        System.out.println("Tạo đơn hàng: SP01 (1 cái), SP02 (2 cái)");
        System.out.println("Đơn hàng ORD001 được tạo");

        System.out.println("Tính tổng tiền");

        OrderCalculator calculator = new OrderCalculator();
        double total = calculator.calculateTotal(order);

        order.setTotal(total);

        System.out.println("Tổng tiền: " + (long) total);

        System.out.println("Lưu đơn hàng");

        OrderRepository repository = new OrderRepository();
        repository.save(order);

        System.out.println("Gửi email xác nhận");

        EmailService emailService = new EmailService();
        emailService.sendOrderConfirmation(customer, order);
    }
}
