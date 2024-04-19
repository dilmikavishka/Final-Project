package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {

    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer VALUES(?,?,?,?,'ACTIVE')";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,customer.getId());
        pstm.setObject(2,customer.getName());
        pstm.setObject(3,customer.getTel());
        pstm.setObject(4,customer.getAddress());

        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer WHERE  status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String tel = resultSet.getString(3);
            String address = resultSet.getString(4);

            Customer customer = new Customer(id,name,address,tel);
            cusList.add(customer);
        }
        return cusList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "UPDATE customer SET status = 'DELETE' WHERE customerId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET customerName = ? , customerCon_Number = ? , customeraddress = ?  WHERE customerId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,customer.getName());
        pstm.setObject(2,customer.getTel());
        pstm.setObject(3,customer.getAddress());
        pstm.setObject(4,customer.getId());

        return pstm.executeUpdate() > 0;
    }

    public static Customer searchById(String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String tel = resultSet.getString(3);
            String address = resultSet.getString(4);

            Customer customer = new Customer(cus_id,name,tel,address);

            return customer;
        }

        return null;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT customerId FROM customer";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<String> cusList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            cusList.add(id);
        }
        return cusList;
    }
}

