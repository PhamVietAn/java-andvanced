package Product;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductDatabase db = ProductDatabase.getInstance();
        int choice;
        do {
            System.out.println("""
                    ---------------------- QUẢN LÝ SẢN PHẨM----------------------
                    1. Thêm mới sản phẩm
                    2. Xem danh sách sản phẩm
                    3. Cập nhật thông tin sản phẩm
                    4. Xoá sản phẩm
                    5. Thoát
                    -------------------------------------------------------------
                    """);
            Scanner input = new Scanner(System.in);
            System.out.println("Nhập lựa chọn của bạn: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    String type;
                    do {
                        System.out.println("Nhập loại sản phẩm (physical/digital): ");
                        type = input.nextLine();
                        if (type == null || !type.equalsIgnoreCase("physical") && !type.equalsIgnoreCase("digital")) {
                            System.out.println("Loại sản phẩm không hợp lệ. Vui lòng nhập 'physical' hoặc 'digital'.");
                        }
                    } while (type == null || (!type.equalsIgnoreCase("physical") && !type.equalsIgnoreCase("digital")));
                    System.out.println("Nhập ID: ");
                    String id = input.nextLine();
                    System.out.println("Nhập tên: ");
                    String name = input.nextLine();
                    System.out.println("Nhập giá: ");
                    double price = input.nextDouble();
                    input.nextLine();
                    double extra = 0;
                    if (type.equalsIgnoreCase("physical")) {
                        System.out.println("Nhập trọng lượng (kg): ");
                        extra = input.nextDouble();
                    } else if (type.equalsIgnoreCase("digital")) {
                        System.out.println("Nhập dung lượng (MB): ");
                        extra = input.nextInt();
                    }
                    Product product = ProductFactory.createProduct(type, id, name, price, extra);
                    db.addProduct(product);
                    break;
                case 2:
                    for (Product p : db.getAllProducts()) {
                        p.displayInfo();
                        System.out.println("-----------------------------");
                    }
                    break;
                case 3:
                    System.out.println("Nhập ID sản phẩm cần cập nhật: ");
                    String updateId = input.nextLine();
                    System.out.println("Nhập tên mới: ");
                    String newName = input.nextLine();
                    System.out.println("Nhập giá mới: ");
                    double newPrice = input.nextDouble();
                    db.updateProduct(updateId, newName, newPrice);
                    break;
                case 4:
                    System.out.println("Nhập ID sản phẩm cần xoá: ");
                    String deleteId = input.nextLine();
                    db.deleteProduct(deleteId);
                    break;
                case 5:
                    System.out.println("Thoát chương trình. Hẹn gặp lại!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (choice != 5);

    }
}
