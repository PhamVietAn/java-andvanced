import java.util.Scanner;

public class Ex01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nhap nam sinh: ");
            String input = sc.nextLine();

            int namSinh = Integer.parseInt(input);
            int tuoi = 2026 - namSinh;

            System.out.println("Tuoi cua ban la: " + tuoi);

        } catch (NumberFormatException e) {
            System.out.println("Loi: Vui long nhap nam sinh bang so!");
        } finally {
            sc.close();
            System.out.println("Thuc hien don dep tai nguyen trong finally...");
        }
    }
}
