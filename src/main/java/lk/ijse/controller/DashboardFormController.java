package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private AnchorPane anpDash;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private Label lblCusCount;

    @FXML
    private Label lblEmpCount;

    @FXML
    private Label lblOrderCount;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private PieChart pieChart;

    private int customerCount;

    private int orderCount;

    private int employeeCount;

    @FXML
    private BarChart<?, ?> barChart;

    public void initialize() throws IOException {

        try {
            customerCount = getActiveCustomerCount();
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

    private int getActiveCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS active_customer_count FROM customer WHERE status = 'ACTIVE'";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("active_customer_count");
        }
        return 0;
    }

    private void setOrderCount(int orderCount) {
        lblOrderCount.setText(String.valueOf(orderCount));
    }

    private int getOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS active_order_count FROM Orders WHERE status = 'ACTIVE'";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("active_order_count");
        }
        return 0;
    }

    private void setEmployeeCount(int employeeCount) {
        lblEmpCount.setText(String.valueOf(employeeCount));
    }

    private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS active_employee_count FROM Employee WHERE status = 'ACTIVE'";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("active_employee_count");
        }
        return 0;

    }

    private void setCustomerCount(int customerCount) {
        lblCusCount.setText(String.valueOf(customerCount));

    }

   /* private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM Customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("customer_count");
        }
        return 0;
    }*/

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNote = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(rootNote);
        Stage stage = (Stage) this.anpDash.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login form");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniPieChart();
        iniLineChart();
        iniBarChart();

        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void iniBarChart() {
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Monday",78));
        series.getData().add(new XYChart.Data<>("Teusday",45));
        series.getData().add(new XYChart.Data<>("Wednesday",60));
        series.getData().add(new XYChart.Data<>("Thursday",80));
        series.getData().add(new XYChart.Data<>("Friday",90));
        series.getData().add(new XYChart.Data<>("Saturday",85));
        series.getData().add(new XYChart.Data<>("Sunday",50));
        barChart.getData().addAll(series);
        barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }

    private void iniLineChart() {
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("January",20));
        series.getData().add(new XYChart.Data<>("Fabruary",60));
        series.getData().add(new XYChart.Data<>("March",10));
        series.getData().add(new XYChart.Data<>("April",75));
        series.getData().add(new XYChart.Data<>("May",90));
        series.getData().add(new XYChart.Data<>("June",20));
        series.getData().add(new XYChart.Data<>("July",25));
        series.getData().add(new XYChart.Data<>("August",50));
        series.getData().add(new XYChart.Data<>("September",87));
        series.getData().add(new XYChart.Data<>("October",88));
        series.getData().add(new XYChart.Data<>("November",90));
        series.getData().add(new XYChart.Data<>("December",100));
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }

    private void iniPieChart() {
        ObservableList<PieChart.Data> pieChartDate = FXCollections.observableArrayList(
                new PieChart.Data("Resin",100),
                new PieChart.Data("Button",20),
                new PieChart.Data("color",200)
        );
        pieChart.setData(pieChartDate);

    }
}



     /*try {
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
        setEmployeeCount(employeeCount);*/