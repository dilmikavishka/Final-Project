package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Order;
import lk.ijse.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES(?,?,?,?,?,'ACTIVE')";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,supplier.getSupId());
        pstm.setObject(2,supplier.getName());
        pstm.setObject(3,supplier.getDate());
        pstm.setObject(4,supplier.getTel());
        pstm.setObject(5,supplier.getPayId());

        return pstm.executeUpdate() > 0;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier WHERE  status = 'ACTIVE'";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supplierList = new ArrayList<>();

        while (resultSet.next()){
            String SupId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String payId = resultSet.getString(5);

            Supplier supplier = new Supplier(SupId,name,date,tel,payId);
            supplierList.add(supplier);
        }
        return supplierList;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET supplierName = ? , supplierDate = ? , supplierC0n_Number = ?,paymentId = ?  WHERE supplierId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,supplier.getName());
        pstm.setObject(2,supplier.getDate());
        pstm.setObject(3,supplier.getTel());
        pstm.setObject(4,supplier.getPayId());
        pstm.setObject(5,supplier.getSupId());


        return pstm.executeUpdate() > 0;
    }

    public static Supplier searchById(String id) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String SupId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String payId = resultSet.getString(5);

            Supplier supplier = new Supplier(SupId,name,date,tel,payId);

            return supplier;
        }

        return null;
    }

    public static boolean delete(String supId) throws SQLException {
        String sql = "UPDATE supplier SET status = 'DELETE' WHERE supplierId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supId);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getId() throws SQLException {
        String sql = "SELECT supplierId FROM supplier";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<String> payList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            payList.add(id);
        }
        return payList;
    }
}
