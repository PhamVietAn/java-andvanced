package Ex06;

public class MemberDiscount implements DiscountStrategy {

    public double apply(double total) {
        System.out.println("Áp dụng giảm giá thành viên 5%");
        return total * 0.95;
    }

    public String getName() {
        return "Member Discount";
    }
}