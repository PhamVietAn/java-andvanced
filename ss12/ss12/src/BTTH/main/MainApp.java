package BTTH.main;

import BTTH.dao.MedicineDAO;
import BTTH.dao.PrescriptionDAO;
import BTTH.model.Medicine;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MedicineDAO mDAO = new MedicineDAO();
        PrescriptionDAO pDAO = new PrescriptionDAO();

        System.out.print("Nhập ID thuốc: ");
        int id = sc.nextInt();

        System.out.print("Nhập số lượng thêm: ");
        int qty = sc.nextInt();

        mDAO.updateMedicineStock(id, qty);

        System.out.print("Min price: ");
        double min = sc.nextDouble();

        System.out.print("Max price: ");
        double max = sc.nextDouble();

        List<Medicine> list = mDAO.findMedicinesByPrice(min, max);

        System.out.println("Danh sách thuốc:");
        list.forEach(System.out::println);


        System.out.print("Nhập ID đơn thuốc: ");
        int pid = sc.nextInt();

        double total = pDAO.calculatePrescriptionTotal(pid);
        System.out.println("Tổng tiền đơn: " + total);


        sc.nextLine();
        System.out.print("Nhập ngày (yyyy-MM-dd): ");
        String date = sc.nextLine();

        double revenue = pDAO.getDailyRevenue(date);
        System.out.println("Doanh thu ngày: " + revenue);
    }
}