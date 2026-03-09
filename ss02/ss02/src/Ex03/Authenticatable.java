package Ex03;

@FunctionalInterface
public interface Authenticatable {
    String getPassword();

    // Phương thức mặc định kiểm tra xác thực (mật khẩu không rỗng)
    default boolean isAuthenticated() {
        String password = getPassword();
        return password != null && !password.isEmpty();
    }

    // Phương thức tĩnh mô phỏng mã hóa mật khẩu
    static String encrypt(String rawPassword) {
        if (rawPassword == null) {
            return null;
        }
        // Mô phỏng mã hóa đơn giản bằng Caesar cipher
        StringBuilder encrypted = new StringBuilder();
        for (char c : rawPassword.toCharArray()) {
            encrypted.append((char) (c + 3));
        }
        return encrypted.toString();
    }
}
