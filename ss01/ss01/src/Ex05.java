public class Ex05 {

    public static void main(String[] args) {
        System.out.println("=== Hệ thống Quản lý Người dùng ===\n");

        User user = new User();
        user.setName("Nguyen Van A");

        System.out.println("--- Test 1: Đăng ký với tuổi hợp lệ ---");
        try {
            user.setAge(25);
            System.out.println("Đăng ký thành công: " + user);
        } catch (InvalidAgeException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }

        System.out.println("\n--- Test 2: Đăng ký với tuổi âm ---");
        try {
            user.setAge(-5);
            System.out.println("Đăng ký thành công: " + user);
        } catch (InvalidAgeException e) {
            System.out.println("Lỗi: " + e.getMessage());
            System.out.println("Loại ngoại lệ: " + e.getClass().getName());
            e.printStackTrace(); 
        }

        System.out.println("\n--- Test 3: Đăng ký với tuổi quá lớn ---");
        try {
            user.setAge(200);
            System.out.println("Đăng ký thành công: " + user);
        } catch (InvalidAgeException e) {
            System.out.println("Lỗi: " + e.getMessage());
            System.out.println("Loại ngoại lệ: " + e.getClass().getName());
        }

        System.out.println("\n=== Chương trình kết thúc bình thường ===");
    }
}

class InvalidAgeException extends Exception {

    public InvalidAgeException(String msg) {
        super(msg);
    }
}

class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Tuổi không thể âm! Giá trị nhập: " + age);
        }
        if (age > 150) {
            throw new InvalidAgeException("Tuổi không hợp lệ (vượt quá 150)! Giá trị nhập: " + age);
        }
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + "}";
    }
}
