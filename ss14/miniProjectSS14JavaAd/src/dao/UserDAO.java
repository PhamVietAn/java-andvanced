package dao;

import entity.User;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class cho thao tác CRUD với bảng Users
 * Sử dụng PreparedStatement để phòng chống SQL Injection
 */
public class UserDAO {

    /**
     * Thêm mới một user vào database
     * @param user - đối tượng User cần thêm
     * @return id của user vừa được thêm, hoặc -1 nếu thất bại
     */
    public int insert(User user) throws SQLException {
        String sql = "INSERT INTO users(name, email) VALUES(?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());

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
     * Cập nhật thông tin user
     * @param user - đối tượng User với thông tin mới
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Xóa user theo id
     * @param userId - id của user cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean delete(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Lấy thông tin user theo id
     * @param userId - id của user cần tìm
     * @return đối tượng User nếu tìm thấy, null nếu không tìm thấy
     */
    public User getById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Lấy danh sách tất cả users
     * @return danh sách User
     */
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY id";
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at")
                );
                users.add(user);
            }
        }
        return users;
    }

    /**
     * Tìm user theo email
     * @param email - email cần tìm
     * @return đối tượng User nếu tìm thấy, null nếu không tìm thấy
     */
    public User getByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return null;
    }
}
