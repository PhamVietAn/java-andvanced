-- ==============================================================
-- SCHEMA CHO HỆ THỐNG XỬ LÝ ĐƠN HÀNG FLASH SALE
-- Database: flash_sale (MySQL)
-- ==============================================================

-- Xóa các bảng cũ nếu tồn tại (để chạy lại script dễ dàng)
DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

-- ==============================================================
-- TẠO CÁC BẢNG
-- ==============================================================

-- Bảng Users: Lưu thông tin người dùng
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Bảng Products: Lưu thông tin sản phẩm
CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Bảng Orders: Lưu thông tin đơn hàng
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    total_amount DECIMAL(10, 2) DEFAULT 0 CHECK (total_amount >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Bảng Order_Details: Lưu chi tiết đơn hàng
CREATE TABLE order_details (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- ==============================================================
-- TẠO INDEX ĐỂ TỐI ƯU HÓA TRUY VẤN
-- ==============================================================

CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_order_details_order_id ON order_details(order_id);
CREATE INDEX idx_order_details_product_id ON order_details(product_id);
CREATE INDEX idx_products_category ON products(category);

-- ==============================================================
-- STORED PROCEDURES
-- ==============================================================

-- Procedure 1: Lấy Top 5 người mua hàng nhiều nhất
DELIMITER //

CREATE PROCEDURE GetTopBuyers()
BEGIN
    SELECT
        u.id,
        u.name,
        u.email,
        COUNT(o.id) as total_orders,
        COALESCE(SUM(o.total_amount), 0) as total_spent
    FROM users u
    LEFT JOIN orders o ON u.id = o.user_id
    GROUP BY u.id, u.name, u.email
    ORDER BY total_orders DESC, total_spent DESC
    LIMIT 5;
END //

DELIMITER ;

-- Procedure 2: Tính tổng doanh thu theo danh mục sản phẩm
DELIMITER //

CREATE PROCEDURE CalculateCategoryRevenue(IN categoryName VARCHAR(50), OUT totalRevenue DECIMAL(10, 2))
BEGIN
    SELECT COALESCE(SUM(od.quantity * od.price), 0)
    INTO totalRevenue
    FROM order_details od
    JOIN products p ON od.product_id = p.id
    WHERE p.category = categoryName;
END //

DELIMITER ;

-- Procedure 3: Lấy thống kê doanh thu tất cả danh mục
DELIMITER //

CREATE PROCEDURE GetAllCategoryRevenue()
BEGIN
    SELECT
        p.category,
        COUNT(DISTINCT od.order_id) as total_orders,
        SUM(od.quantity) as total_quantity_sold,
        SUM(od.quantity * od.price) as total_revenue
    FROM products p
    LEFT JOIN order_details od ON p.id = od.product_id
    GROUP BY p.category
    ORDER BY total_revenue DESC;
END //

DELIMITER ;

-- ==============================================================
-- DỮ LIỆU MẪU (SAMPLE DATA)
-- ==============================================================

-- Thêm người dùng mẫu
INSERT INTO users (name, email) VALUES
('Nguyễn Văn An', 'nva@gmail.com'),
('Trần Thị Bình', 'ttb@gmail.com'),
('Lê Hoàng Cường', 'lhc@gmail.com'),
('Phạm Thu Dung', 'ptd@gmail.com'),
('Hoàng Văn Em', 'hve@gmail.com'),
('Vũ Thị Phượng', 'vtp@gmail.com'),
('Đỗ Minh Quân', 'dmq@gmail.com'),
('Bùi Thu Hà', 'bth@gmail.com');

-- Thêm sản phẩm mẫu (Flash Sale Items)
INSERT INTO products (name, category, price, stock) VALUES
('iPhone 15 Pro Max', 'Electronics', 29990000, 10),
('Samsung Galaxy S24 Ultra', 'Electronics', 27990000, 15),
('MacBook Pro M3', 'Electronics', 45990000, 8),
('Sony WH-1000XM5', 'Electronics', 8990000, 25),
('iPad Air M2', 'Electronics', 16990000, 20),

('Nike Air Max', 'Fashion', 3500000, 30),
('Adidas Ultraboost', 'Fashion', 4200000, 25),
('Gucci Belt', 'Fashion', 12000000, 12),
('Louis Vuitton Bag', 'Fashion', 35000000, 5),
('Rolex Watch', 'Fashion', 250000000, 3),

('Tủ lạnh Samsung', 'Home', 15000000, 10),
('Máy giặt LG', 'Home', 12000000, 15),
('Điều hòa Daikin', 'Home', 18000000, 8),
('Nồi cơm điện Panasonic', 'Home', 2500000, 40),
('Lò vi sóng Sharp', 'Home', 3500000, 30);

-- ==============================================================
-- TRIGGER: Tự động cập nhật total_amount trong orders
-- ==============================================================

DELIMITER //

CREATE TRIGGER update_order_total_after_insert
AFTER INSERT ON order_details
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total_amount = (
        SELECT SUM(quantity * price)
        FROM order_details
        WHERE order_id = NEW.order_id
    )
    WHERE id = NEW.order_id;
END //

CREATE TRIGGER update_order_total_after_update
AFTER UPDATE ON order_details
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total_amount = (
        SELECT SUM(quantity * price)
        FROM order_details
        WHERE order_id = NEW.order_id
    )
    WHERE id = NEW.order_id;
END //

CREATE TRIGGER update_order_total_after_delete
AFTER DELETE ON order_details
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total_amount = (
        SELECT COALESCE(SUM(quantity * price), 0)
        FROM order_details
        WHERE order_id = OLD.order_id
    )
    WHERE id = OLD.order_id;
END //

DELIMITER ;

-- ==============================================================
-- KẾT THÚC SCRIPT
-- ==============================================================
