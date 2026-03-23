package ex05.demo;

import ex05.DAO.PatientDAO;
import ex05.model.Patient;

import java.util.Date;
import java.util.Scanner;

public class Ex05 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PatientDAO dao = new PatientDAO();

        while (true) {
            System.out.println("\n===== RHMS =====");
            System.out.println("1. danh sách bệnh nhân");
            System.out.println("2. thêm bệnh nhân");
            System.out.println("3. cập nhật bệnh án");
            System.out.println("4. xuất viện & tính phí");
            System.out.println("5. thoát");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    dao.getAll();
                    break;

                case 2:
                    Patient p = new Patient();

                    System.out.print("tên: ");
                    p.setName(sc.nextLine());

                    System.out.print("tuổi: ");
                    p.setAge(sc.nextInt());
                    sc.nextLine();

                    System.out.print("khoa: ");
                    p.setDepartment(sc.nextLine());

                    System.out.print("bệnh: ");
                    p.setDisease(sc.nextLine());

                    p.setAdmissionDate(new Date());

                    dao.insert(p);
                    break;

                case 3:
                    System.out.print("id: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("bệnh mới: ");
                    String disease = sc.nextLine();

                    dao.updateDisease(id, disease);
                    break;

                case 4:
                    System.out.print("id: ");
                    int id2 = sc.nextInt();

                    dao.discharge(id2);
                    break;

                case 5:
                    System.out.println("thoát...");
                    return;
            }
        }
    }
}
