package db.repository.impl;

import db.entity.Product;
import db.repository.ProductRepository;
import db.utility.DBUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        Connection con;
        Statement stmt = null;
        ResultSet rs = null;

        con = DBUtility.getConnection();


        try {
            stmt = con.createStatement();
            String sql = "SELECT * FROM products";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Product product = new Product();
                product.setProId(rs.getInt("product_id"));
                product.setProName(rs.getString("product_name"));
                product.setProducer(rs.getString("producer"));
                product.setYearMaking(rs.getInt("year_making"));
                product.setExpiryDate(rs.getDate("expire_date"));
                product.setPrice(rs.getDouble("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtility.closeAll(con, stmt, rs);
        }

        return  products;
    }
}
