package dao;

import entity.Product;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class cho thao tác CRUD với bảng Products
 * Sử dụng PreparedStatement để phòng chống SQL Injection
 */
public class ProductDAO {

    /**
     * Thêm mới một sản phẩm vào database
     * @param product - đối tượng Product cần thêm
     * @return id của product vừa được thêm, hoặc -1 nếu thất bại
     */
    public int insert(Product product) throws SQLException {
        String sql = "INSERT INTO products(name, category, price, stock) VALUES(?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Cập nhật thông tin sản phẩm
     * @param product - đối tượng Product với thông tin mới
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean update(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, category = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setInt(5, product.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Xóa sản phẩm theo id
     * @param productId - id của sản phẩm cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean delete(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Lấy thông tin sản phẩm theo id
     * @param productId - id của sản phẩm cần tìm
     * @return đối tượng Product nếu tìm thấy, null nếu không tìm thấy
     */
    public Product getById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Lấy danh sách tất cả sản phẩm
     * @return danh sách Product
     */
    public List<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM products ORDER BY id";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getTimestamp("created_at")
                );
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Lấy số lượng tồn kho của một sản phẩm
     * Sử dụng trong Connection riêng để phục vụ transaction
     */
    public int getStock(Connection conn, int productId) throws SQLException {
        String sql = "SELECT stock FROM products WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                }
            }
        }
        return 0;
    }

    /**
     * Lấy giá của một sản phẩm
     * Sử dụng trong Connection riêng để phục vụ transaction
     */
    public double getPrice(Connection conn, int productId) throws SQLException {
        String sql = "SELECT price FROM products WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("price");
                }
            }
        }
        return 0;
    }

    /**
     * TRỪ SỐ LƯỢNG TỒN KHO (QUAN TRỌNG NHẤT TRONG HỆ THỐNG FLASH SALE)
     *
     * Câu lệnh SQL này sử dụng kỹ thuật "Optimistic Locking" với điều kiện WHERE:
     * - UPDATE chỉ thực hiện nếu stock >= quantity (đảm bảo không bị âm)
     * - Trả về số dòng bị ảnh hưởng (affected rows):
     *   + = 1: Trừ kho thành công
     *   + = 0: Không đủ hàng hoặc sản phẩm không tồn tại
     *
     * Method này PHẢI được gọi trong một Transaction để đảm bảo tính nhất quán
     *
     * @param conn - Connection từ transaction (quan trọng!)
     * @param productId - id sản phẩm
     * @param quantity - số lượng cần trừ
     * @return true nếu trừ kho thành công, false nếu không đủ hàng
     */
    public boolean decreaseStock(Connection conn, int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Lấy danh sách sản phẩm theo category
     * @param category - tên category
     * @return danh sách Product
     */
    public List<Product> getByCategory(String category) throws SQLException {
        String sql = "SELECT * FROM products WHERE category = ? ORDER BY id";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getTimestamp("created_at")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }
}
