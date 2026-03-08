package ss01.exception;

import java.util.Scanner;

public class Example {
    /*
        tạo ra các phương thức nhập và trả về các giá trị gồm
        1. số nguyên
        2. số thực
        3. kí tự và chuỗi
        4. boolean
        lưu ý: xử lý hết lỗi sinh ra khi nhập, nếu không đúng thì nhập lại
    * */
    static Scanner sc = new Scanner(System.in);
    public static int inputInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Nhap sai dinh dang so nguyen. Vui long nhap lai!");
            }
        }
    }
    // nhập số thực
    public static double inputDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Nhap sai dinh dang so thuc. Vui long nhap lai!");
            }
        }
    }

    // nhập chuỗi
    public static String inputString(String message) {
        while (true) {
            System.out.print(message);
            String str = sc.nextLine().trim();
            if (!str.isEmpty()) {
                return str;
            }
            System.out.println("Chuoi khong duoc de trong!");
        }
    }

    // nhập kí tự
    public static char inputChar(String message) {
        while (true) {
            System.out.print(message);
            String str = sc.nextLine();
            if (str.length() == 1) {
                return str.charAt(0);
            }
            System.out.println("Chi duoc nhap 1 ky tu!");
        }
    }

    // nhập boolean
    public static boolean inputBoolean(String message) {
        while (true) {
            System.out.print(message + " (true/false): ");
            String str = sc.nextLine().toLowerCase();
            if (str.equals("true") || str.equals("false")) {
                return Boolean.parseBoolean(str);
            }
            System.out.println("Chi duoc nhap true hoac false!");
        }
    }

    public static void main(String[] args) {
        int a = Example.inputInt("Nhap so nguyen: ");
        double b = Example.inputDouble("Nhap so thuc: ");
        String name = Example.inputString("Nhap chuoi: ");
        char c = Example.inputChar("Nhap ky tu: ");
        boolean check = Example.inputBoolean("Nhap boolean");

        System.out.println(a + " " + b + " " + name + " " + c + " " + check);
    }
}
