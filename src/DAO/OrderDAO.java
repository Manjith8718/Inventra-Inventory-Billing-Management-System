package DAO;
import Utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class OrderDAO {
    public static double getMonthlyRevenue(int month, int year) {

        String sql = "SELECT SUM(total_amount) " +
                "FROM orders " +
                "WHERE MONTH(order_date) = ? AND YEAR(order_date) = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, month);
            ps.setInt(2, year);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public static int insertOrder(String invoiceNumber, double totalAmount) {

        String query = "INSERT INTO orders (invoice_number, total_amount, order_date) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, invoiceNumber);
            ps.setDouble(2, totalAmount);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static void insertOrderItem(int orderId, int productId, int quantity, double price) {

        String query = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
