package Ex02;

public class Ex02 {

    public static void main(String[] args) {

        double total = 1000000;

        System.out.println("Đơn hàng: tổng tiền 1.000.000, áp dụng PercentageDiscount 10%");
        OrderCalculator cal1 = new OrderCalculator(new PercentageDiscount(10));
        System.out.println("Số tiền sau giảm: " + (long) cal1.calculate(total));

        System.out.println();

        System.out.println("Đơn hàng: tổng tiền 1.000.000, áp dụng FixedDiscount 50.000");
        OrderCalculator cal2 = new OrderCalculator(new FixedDiscount(50000));
        System.out.println("Số tiền sau giảm: " + (long) cal2.calculate(total));

        System.out.println();

        System.out.println("Đơn hàng: tổng tiền 1.000.000, áp dụng NoDiscount");
        OrderCalculator cal3 = new OrderCalculator(new NoDiscount());
        System.out.println("Số tiền sau giảm: " + (long) cal3.calculate(total));

        System.out.println();

        System.out.println("Thêm HolidayDiscount 15% (không sửa code cũ)");
        OrderCalculator cal4 = new OrderCalculator(new HolidayDiscount());
        System.out.println("Số tiền sau giảm: " + (long) cal4.calculate(total));
    }
}
