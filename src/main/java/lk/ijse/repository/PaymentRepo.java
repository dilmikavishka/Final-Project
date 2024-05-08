package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Payment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentRepo {
    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES (?,?,?,?,?,'ACTIVE')";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,payment.getPaymentId());
        pstm.setObject(2,payment.getPaymentDate());
        pstm.setObject(3,payment.getAmount());
        pstm.setObject(4,payment.getType());
        pstm.setObject(5,payment.getOId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE payment SET paymentDate = ?,amount = ?,type = ?,orderId = ?, WHERE paymentId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,payment.getPaymentDate());
        pstm.setObject(2,payment.getAmount());
        pstm.setObject(3,payment.getType());
        pstm.setObject(4,payment.getOId());
        pstm.setObject(5,payment.getPaymentId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "UPDATE payment SET status = 'DELETE' WHERE paymentId = ?";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static Payment searchById(String id) throws SQLException {
        String sql = "SELECT * FROM  payment WHERE paymentId = ? ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String payId = resultSet.getString(1);
            Date date = Date.valueOf(resultSet.getString(2));
            double amount = Double.parseDouble(resultSet.getString(3));
            String type = resultSet.getString(4);
            String oId = resultSet.getString(5);

            Payment payment = new Payment(payId,date,amount,type,oId);
            return payment;
        }
        return null;
    }

    public static List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM  payment WHERE status = 'ACTIVE' ";
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().
                prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> payList = new ArrayList<>();

        while(resultSet.next()){
            String payId = resultSet.getString(1);
            Date date = Date.valueOf(resultSet.getString(2));
            double amount = Double.parseDouble(resultSet.getString(3));
            String type = resultSet.getString(4);
            String oId = resultSet.getString(5);

            Payment payment = new Payment(payId,date,amount,type,oId);

            payList.add(payment);
        }
        return payList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT paymentId FROM payment";
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
        String sql = "SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String id = resultSet.getString(1);
            return id;
        }
        return null;
    }

    public static Map<String, Double> getPaymentsByDay() {
        Map<String, Double> paymentsByDay = new HashMap<>();

        String sql = "SELECT paymentDate, SUM(amount) AS total_amount FROM payment GROUP BY paymentDate";

        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            while (resultSet.next()) {
                String date = resultSet.getString("paymentDate");
                double totalAmount = resultSet.getDouble("total_amount");
                paymentsByDay.put(date, totalAmount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return paymentsByDay;
    }
    }

