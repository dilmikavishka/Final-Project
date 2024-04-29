package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashBordFormController {

    @FXML
    private AnchorPane anpDashbord;

    @FXML
    private JFXButton btnBack;

    @FXML
    private Label lblCusCount;

    @FXML
    private Label lblEmpCount;

    @FXML
    private Label lblOrderCount;

    private int customerCount;
    private int orderCount;

    private int employeeCount;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    public void initialize() {
        try {
            customerCount = getCustomerCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCustomerCount(customerCount);


        try {
            orderCount = getOrderCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setOrderCount(orderCount);



        try {
            employeeCount = getEmployeeCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setEmployeeCount(employeeCount);


    }

    private void setEmployeeCount(int employeeCount) {
        lblEmpCount.setText(String.valueOf(employeeCount));
    }

    private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employee_count FROM Employee";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("employee_count");
        }
        return 0;
    }

    private void setOrderCount(int orderCount) {
        lblOrderCount.setText(String.valueOf(orderCount));
    }

    private int getOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS order_count FROM Orders";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("order_count");
        }
        return 0;
    }

    private void setCustomerCount(int customerCount) {
        lblCusCount.setText(String.valueOf(customerCount));
    }

    private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM Customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("customer_count");
        }
        return 0;
    }




}
