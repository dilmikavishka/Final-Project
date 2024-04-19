package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepo {
    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?,?,?,?,'ACTIVE')";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,order.getoId());
        pstm.setObject(2,order.getdate());
        pstm.setObject(3,order.getCusId());
        pstm.setObject(4,order.getPayId());

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
            String date = resultSet.getString(2);
            String CusId = resultSet.getString(3);
            String PayId = resultSet.getString(4);

            Order order = new Order(oId,date,CusId,PayId);
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
        String sql = "UPDATE orders SET orderDate = ? , customerId = ? , paymentId = ?  WHERE orderId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,order.getdate());
        pstm.setObject(2,order.getCusId());
        pstm.setObject(3,order.getPayId());
        pstm.setObject(4,order.getoId());

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
            String date = resultSet.getString(2);
            String CusId = resultSet.getString(3);
            String PayId = resultSet.getString(4);

            Order order = new Order(oId,date,CusId,PayId);

            return order;
        }

        return null;
    }
}
