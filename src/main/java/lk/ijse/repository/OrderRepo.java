package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepo {
    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?,?,?,'ACTIVE')";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,order.getOId());
        pstm.setObject(2,order.getDate());
        pstm.setObject(3,order.getCusId());

        return pstm.executeUpdate() > 0;
    }

    public static List<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM orders WHERE  status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Order> orderList = new ArrayList<>();

        while (resultSet.next()){
            String oId = resultSet.getString(1);
            Date date = Date.valueOf(resultSet.getString(2));
            String CusId = resultSet.getString(3);

            Order order = new Order(oId,date,CusId);
            orderList.add(order);
        }
        return orderList;
    }

    public static boolean delete(String oId) throws SQLException {
        String sql = "UPDATE orders SET status = 'DELETE' WHERE orderId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, oId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Order order) throws SQLException {
        String sql = "UPDATE orders SET orderDate = ? , customerId = ?  WHERE orderId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,order.getDate());
        pstm.setObject(2,order.getCusId());
        pstm.setObject(3,order.getOId());

        return pstm.executeUpdate() > 0;
    }

    public static Order searchById(String id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE orderId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String oId = resultSet.getString(1);
            Date date = Date.valueOf(resultSet.getString(2));
            String CusId = resultSet.getString(3);

            Order order = new Order(oId,date,CusId);

            return order;
        }

        return null;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT orderId FROM orders";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<String> orderList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            orderList.add(id);
        }
        return orderList;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static Map<String, Double> getOrdersByDay() {
        Map<String, Double> OrderByDay = new HashMap<>();

        String sql = "SELECT orderDate, COUNT(*) AS order_count FROM orders GROUP BY orderDate";

        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            while (resultSet.next()) {
                String date = resultSet.getString("orderDate");
                double orderCount = resultSet.getDouble("order_count");
                OrderByDay.put(date, orderCount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return OrderByDay;
    }
}
