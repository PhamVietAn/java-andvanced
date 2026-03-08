package ss01.exception;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MyCheckException exception = new MyCheckException("không có người yêu");
        MyUncheckException uncheckedException = new MyUncheckException("thức khuya");
    }
    public static int InputInterger() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Nhập vào một số nguyên: ");
            int number = sc.nextInt();
            return number;
        } catch (Exception e) {
            System.out.println("phải nhập vào một số nguyên");
            return InputInterger();
        } finally {
            sc.close();
        }
    }
}
