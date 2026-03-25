import dao.ProductDAO;
import dao.ReportDAO;
import service.FlashSaleService;
import utils.DatabaseInitializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * =====================================================================
 * MINI PROJECT: HỆ THỐNG XỬ LÝ ĐƠN HÀNG FLASH SALE
 * =====================================================================
 *
 * Mục tiêu: Minh họa việc sử dụng JDBC, Transaction Management, và
 * xử lý đồng thời (Concurrency) trong môi trường Flash Sale
 *
 * Kiến thức áp dụng:
 * - JDBC Connection Management (Singleton Pattern)
 * - Statement vs PreparedStatement vs CallableStatement
 * - Transaction Management (ACID, Isolation Levels)
 * - Batch Processing
 * - Stored Procedures
 * - Multi-threading và Race Conditions
 *
 * =====================================================================
 */
public class Main {

    private static FlashSaleService flashSaleService = new FlashSaleService();
    private static ReportDAO reportDAO = new ReportDAO();
    private static ProductDAO productDAO = new ProductDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║   HỆ THỐNG XỬ LÝ ĐƠN HÀNG FLASH SALE - E-COMMERCE       ║");
        System.out.println("║   Mini Project: JDBC & Transaction Management            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();

        // Bước 1: Khởi tạo Database (sử dụng Statement)
        initializeDatabase();

        // Bước 2: Hiển thị menu chính
        showMainMenu();
    }

    /**
     * KHỞI TẠO DATABASE TỪ FILE SQL SCRIPT
     * Sử dụng Statement để chạy file schema.sql
     */
    private static void initializeDatabase() {
        System.out.println("Đang kiểm tra database...");

        if (!DatabaseInitializer.isDatabaseInitialized()) {
            System.out.println("Database chưa được khởi tạo. Bắt đầu tạo tables và dữ liệu mẫu...\n");
            DatabaseInitializer.initializeDatabase("schema.sql");
        } else {
            System.out.println("Database đã sẵn sàng!\n");
        }
    }

    /**
     * HIỂN THỊ MENU CHÍNH
     */
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n═══════════════ MENU CHÍNH ═══════════════");
            System.out.println("1. Xem danh sách sản phẩm");
            System.out.println("2. Đặt hàng thủ công (1 user)");
            System.out.println("3. ★ TEST FLASH SALE - 50 THREADS (Yêu cầu đề bài)");
            System.out.println("4. Xem báo cáo Top Buyers");
            System.out.println("5. Xem báo cáo Doanh thu theo Danh mục");
            System.out.println("6. Xem tình trạng tồn kho");
            System.out.println("7. Reset lại database (chạy lại schema.sql)");
            System.out.println("0. Thoát");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn chức năng: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        viewProducts();
                        break;
                    case 2:
                        manualOrder();
                        break;
                    case 3:
                        testFlashSale50Threads();
                        break;
                    case 4:
                        reportDAO.getTopBuyers();
                        break;
                    case 5:
                        reportDAO.displayAllCategoryRevenue();
                        break;
                    case 6:
                        reportDAO.displayStockStatus();
                        break;
                    case 7:
                        resetDatabase();
                        break;
                    case 0:
                        System.out.println("\nCảm ơn bạn đã sử dụng hệ thống!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    /**
     * XEM DANH SÁCH SẢN PHẨM
     */
    private static void viewProducts() {
        try {
            System.out.println("\n========== DANH SÁCH SẢN PHẨM FLASH SALE ==========");
            productDAO.getAll().forEach(product -> {
                System.out.printf("ID: %d | %s | Giá: %.0f VNĐ | Tồn kho: %d | Danh mục: %s%n",
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getStock(),
                        product.getCategory());
            });
            System.out.println("====================================================");
        } catch (Exception e) {
            System.err.println("Lỗi khi xem sản phẩm: " + e.getMessage());
        }
    }

    /**
     * ĐẶT HÀNG THỦ CÔNG
     */
    private static void manualOrder() {
        try {
            System.out.print("\nNhập User ID (1-8): ");
            int userId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Nhập Product ID: ");
            int productId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Nhập số lượng: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            Map<Integer, Integer> items = new HashMap<>();
            items.put(productId, quantity);

            System.out.println("\nĐang xử lý đơn hàng...");
            flashSaleService.placeOrder(userId, items);

        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    /**
     * ★★★ TEST FLASH SALE VỚI 50 THREADS ★★★
     *
     * ĐÂY LÀ YÊU CẦU CỐT LÕI CỦA ĐỀ BÀI (Giai đoạn 5)
     *
     * Mô phỏng tình huống: 50 khách hàng cùng lúc tranh mua 1 sản phẩm
     * chỉ có 10 cái trong kho.
     *
     * Kết quả mong đợi:
     * - Chỉ có đúng 10 đơn hàng thành công
     * - 40 đơn hàng còn lại thất bại (hết hàng)
     * - Số lượng tồn kho cuối cùng = 0 (KHÔNG BAO GIỜ âm)
     *
     * Nếu không có Transaction và Isolation Level phù hợp:
     * - Có thể xảy ra Overselling (bán quá số lượng trong kho)
     * - Stock có thể bị âm (Vi phạm ràng buộc CHECK stock >= 0)
     */
    private static void testFlashSale50Threads() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ★ TEST FLASH SALE - 50 THREADS CÙNG MUA 1 SẢN PHẨM ★        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");

        System.out.print("\nNhập Product ID để test (mặc định: 1 - iPhone 15 Pro Max): ");
        String input = scanner.nextLine().trim();
        int productId = input.isEmpty() ? 1 : Integer.parseInt(input);

        try {
            // Kiểm tra stock trước khi test
            int stockBefore = flashSaleService.checkStock(productId);
            System.out.println("\n[TRƯỚC TEST] Số lượng tồn kho của Product ID " + productId + ": " + stockBefore);

            if (stockBefore <= 0) {
                System.out.println("⚠ Sản phẩm đã hết hàng! Vui lòng reset database hoặc chọn sản phẩm khác.");
                return;
            }

            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("Bắt đầu khởi tạo 50 THREADS (mỗi thread = 1 khách hàng)...");
            System.out.println("Mỗi thread sẽ cố gắng mua 1 sản phẩm");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

            // Tạo 50 threads
            Thread[] threads = new Thread[50];

            for (int i = 0; i < 50; i++) {
                int userId = (i % 8) + 1; // Phân bố cho 8 users
                final int threadNumber = i + 1;

                threads[i] = new Thread(() -> {
                    Map<Integer, Integer> items = new HashMap<>();
                    items.put(productId, 1); // Mỗi thread mua 1 sản phẩm

                    System.out.println("[Thread " + threadNumber + "] User " + userId + " đang cố mua...");
                    flashSaleService.placeOrder(userId, items);
                });

                threads[i].setName("Customer-Thread-" + threadNumber);
            }

            // Start tất cả threads cùng lúc
            long startTime = System.currentTimeMillis();

            for (Thread thread : threads) {
                thread.start();
            }

            // Đợi tất cả threads hoàn thành
            for (Thread thread : threads) {
                thread.join();
            }

            long endTime = System.currentTimeMillis();

            // Kiểm tra stock sau khi test
            Thread.sleep(1000); // Đợi DB hoàn tất mọi transaction
            int stockAfter = flashSaleService.checkStock(productId);

            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("                    KẾT QUẢ TEST                               ");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("Số lượng tồn kho TRƯỚC:     " + stockBefore);
            System.out.println("Số lượng tồn kho SAU:       " + stockAfter);
            System.out.println("Số sản phẩm đã bán:         " + (stockBefore - stockAfter));
            System.out.println("Thời gian xử lý:            " + (endTime - startTime) + " ms");

            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("                 PHÂN TÍCH KẾT QUẢ                             ");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            if (stockAfter < 0) {
                System.out.println("❌ THẤT BẠI: Stock bị âm! Hệ thống đã OVERSELLING!");
                System.out.println("   → Transaction hoặc Isolation Level chưa đúng");
            } else if (stockAfter == 0 && stockBefore <= 10) {
                System.out.println("✅ THÀNH CÔNG: Đúng " + stockBefore + " đơn hàng được xử lý!");
                System.out.println("   → Transaction hoạt động HOÀN HẢO");
                System.out.println("   → Không xảy ra Overselling");
            } else {
                System.out.println("✅ THÀNH CÔNG: Stock không bị âm");
                System.out.println("   Đã bán: " + (stockBefore - stockAfter) + " sản phẩm");
            }

            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        } catch (Exception e) {
            System.err.println("Lỗi khi test: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * RESET LẠI DATABASE
     */
    private static void resetDatabase() {
        System.out.print("\nBạn có chắc chắn muốn reset lại database? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            System.out.println("\nĐang reset database...");
            DatabaseInitializer.initializeDatabase("schema.sql");
            System.out.println("✓ Reset hoàn tất!");
        } else {
            System.out.println("Đã hủy.");
        }
    }
}
