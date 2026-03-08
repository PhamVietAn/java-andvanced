import java.util.Scanner;

public class Ex02 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nhap tong so nguoi dung: ");
            int tongNguoi = Integer.parseInt(sc.nextLine());

            System.out.print("Nhap so nhom muon chia: ");
            int soNhom = Integer.parseInt(sc.nextLine());

            // vùng nguy hiểm
            int moiNhom = tongNguoi / soNhom;

            System.out.println("Moi nhom co: " + moiNhom + " nguoi");

        } catch (ArithmeticException e) {
            System.out.println("Khong the chia cho 0!");
        }

        System.out.println("Chuong trinh van tiep tuc chay...");
        sc.close();
    }
}