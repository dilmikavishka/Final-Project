package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Batch;
import lk.ijse.model.OredrDetail;
import lk.ijse.model.Payment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchRepo {
    public static boolean save(Batch batch) throws SQLException {
        String sql = "INSERT INTO batch VALUES (?,?,?,?,?,?,?,?,'ACTIVE')";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,batch.getBatchId());
        pstm.setObject(2,batch.getBatchColor());
        pstm.setObject(3,batch.getDes());
        pstm.setObject(4,batch.getQtyOnHand());
        pstm.setObject(5,batch.getDate());
        pstm.setObject(6,batch.getEmployeeId());
        pstm.setObject(7,batch.getOrderId());
        pstm.setObject(8,batch.getPrice());

        return pstm.executeUpdate() > 0;

    }

    public static List<Batch> getAll() throws SQLException {
        String sql = "SELECT * FROM  batch WHERE status = 'ACTIVE' ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Batch> batchList = new ArrayList<>();

        while(resultSet.next()){
            String BatchId = resultSet.getString(1);
            String BatchColor = resultSet.getString(2);
            String Des = resultSet.getString(3);
            int qtyonHand = Integer.parseInt(resultSet.getString(4));
            Date date = Date.valueOf(resultSet.getString(5));
            String EmployeeId = resultSet.getString(6);
            String OrderId = resultSet.getString(7);
            double Price = Double.parseDouble(resultSet.getString(8));


            Batch batch = new Batch(BatchId,BatchColor,Des,qtyonHand,date,EmployeeId,OrderId,Price);

            batchList.add(batch);
        }
        return batchList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "UPDATE batch SET status = 'DELETE' WHERE batchId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Batch batch) throws SQLException {
        String sql = "UPDATE batch SET batchColor = ?,desingDescription = ?,QtyOnHand = ? ,Date = ?,employeeId = ?,orderId = ?,Price = ?, WHERE batchId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,batch.getBatchColor());
        pstm.setObject(2,batch.getDes());
        pstm.setObject(3,batch.getQtyOnHand());
        pstm.setObject(4,batch.getDate());
        pstm.setObject(5,batch.getEmployeeId());
        pstm.setObject(6,batch.getOrderId());
        pstm.setObject(7,batch.getPrice());
        pstm.setObject(8,batch.getBatchId());

        return pstm.executeUpdate() > 0;
    }

    public static Batch searchById(String id) throws SQLException {
        String sql = "SELECT * FROM  batch WHERE batchId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String BatchId = resultSet.getString(1);
            String BatchColor = resultSet.getString(2);
            String Des = resultSet.getString(3);
            int qtyOnHand = Integer.parseInt(resultSet.getString(4));
            Date date = Date.valueOf(resultSet.getString(5));
            String EmployeeId = resultSet.getString(6);
            String OrderId = resultSet.getString(7);
            double Price = Double.parseDouble(resultSet.getString(8));

            Batch batch = new Batch(BatchId,BatchColor,Des,qtyOnHand,date,EmployeeId,OrderId,Price);

            return batch;
        }
        return null;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT batchId FROM batch";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> batchList = new ArrayList<>();
        while (resultSet.next()) {
            batchList.add(resultSet.getString(1));
        }
        return batchList;}

    public static boolean update(List<OredrDetail> odList) throws SQLException {
        for (OredrDetail od : odList) {
            boolean isUpdateQty = updateQty(od.getBaId(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String batId, int qty) throws SQLException {
        String sql = "UPDATE batch SET QtyOnHand = QtyOnHand - ? WHERE batchId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, batId);

        return pstm.executeUpdate() > 0;
    }
}
