package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {
    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payment VALUES (?,?,?,?,'ACTIVE')";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,payment.getPaymentId());
        pstm.setObject(2,payment.getPaymentDate());
        pstm.setObject(3,payment.getAmount());
        pstm.setObject(4,payment.getType());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE payment SET paymentDate = ?,amount = ?,type = ?, WHERE paymentId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,payment.getPaymentDate());
        pstm.setObject(2,payment.getAmount());
        pstm.setObject(3,payment.getType());
        pstm.setObject(4,payment.getPaymentId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "UPDATE Payment SET status = 'DELETE' WHERE paymentId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static Payment searchById(String id) throws SQLException {
        String sql = "SELECT * FROM  Payment WHERE paymentId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String payId = resultSet.getString(1);
            String date = resultSet.getString(2);
            double amount = Double.parseDouble(resultSet.getString(3));
            String type = resultSet.getString(4);

            Payment payment = new Payment(payId,date,amount,type);
            return payment;
        }
        return null;
    }

    public static List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM  Payment WHERE status = 'ACTIVE' ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> payList = new ArrayList<>();

        while(resultSet.next()){
            String payId = resultSet.getString(1);
            String date = resultSet.getString(2);
            double amount = Double.parseDouble(resultSet.getString(3));
            String type = resultSet.getString(4);

            Payment payment = new Payment(payId,date,amount,type);

            payList.add(payment);
        }
        return payList;
    }
}
