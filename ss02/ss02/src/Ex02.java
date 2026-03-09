@FunctionalInterface
interface PasswordValidator {
    boolean isValid(String password);
}

public class Ex02 {
    public static void main(String[] args) {
        PasswordValidator validator1 = new PasswordValidator() {
            @Override
            public boolean isValid(String password) {
                return password.length() >= 8;
            }
        };

        // Rút gọn bằng Lambda - Cách 1: Đầy đủ
        PasswordValidator validator2 = (String password) -> {
            return password.length() >= 8;
        };

        // Rút gọn bằng Lambda - Cách 2: Bỏ kiểu dữ liệu (type inference)
        PasswordValidator validator3 = (password) -> {
            return password.length() >= 8;
        };

        // Rút gọn bằng Lambda - Cách 3: Bỏ ngoặc đơn (1 tham số)
        PasswordValidator validator4 = password -> {
            return password.length() >= 8;
        };

        // ✅ RÚT GỌN NGẮN NHẤT: Bỏ cả ngoặc nhọn và return (expression body)
        PasswordValidator validator = password -> password.length() >= 8;

        // Test theo yêu cầu
        System.out.println(validator.isValid("12345678")); // true
        System.out.println(validator.isValid("1234"));     // false
    }
}
