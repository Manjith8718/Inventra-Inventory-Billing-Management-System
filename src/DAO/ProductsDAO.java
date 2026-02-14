package DAO;
import Models.Products;
import Utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO {

    public static boolean addProduct(Products product) {

        String sql = "INSERT INTO products (name, category, price, stock) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Products> getAllProducts() {

        List<Products> list = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Products p = new Products(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Products getProductById(int productId) {

        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Products(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateProduct(Products product) {

        String sql = "UPDATE products SET name=?, category=?, price=?, stock=? WHERE product_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setInt(5, product.getProductId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteProduct(int productId) {

        String sql = "DELETE FROM products WHERE product_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static boolean updateStock(int productId, int newStock) {

        String sql = "UPDATE products SET stock=? WHERE product_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newStock);
            ps.setInt(2, productId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void getBestSellingProducts() {

        String sql = "SELECT p.product_id, p.name, p.category, " +
                "SUM(oi.quantity * oi.price) AS total_revenue " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.product_id " +
                "GROUP BY p.product_id, p.name, p.category " +
                "ORDER BY total_revenue DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Best Selling Products (By Revenue) ---");

            while (rs.next()) {
                System.out.println(
                        "Product ID: " + rs.getInt("product_id") +
                                " | Name: " + rs.getString("name") +
                                " | Category: " + rs.getString("category") +
                                " | Revenue: " + rs.getDouble("total_revenue")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getLowStockProducts(int threshold) {

        String sql = "SELECT * FROM products WHERE stock < ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Low Stock Products ---");

            while (rs.next()) {
                System.out.println(
                        "Product ID: " + rs.getInt("product_id") +
                                " | Name: " + rs.getString("name") +
                                " | Stock: " + rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

