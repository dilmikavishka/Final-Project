package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.Customer;
import lk.ijse.model.Order;
import lk.ijse.model.Tm.OrderTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.OrderRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OderFormController {

    @FXML
    private AnchorPane anpOrderManage;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> comCustId;

    @FXML
    private JFXComboBox<String> comPayId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableView<OrderTm> tblOrderManage;

    @FXML
    private TextField txtOrderDate;

    @FXML
    private TextField txtOrderId;

    public void initialize() {
        setCellValueFactory();
        loadAllOrders();
        getCustmoreIds();
    }

    private void getCustmoreIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> cusList = CustomerRepo.getIds();
            for (String id : cusList){
                obList.add(id);
            }
            comCustId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory("oId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory("date"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory("CusId"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory("payId"));
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) anpOrderManage.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Main Form");
        stage.centerOnScreen();

        AnchorPane anchorPane2 = FXMLLoader.load(getClass().getResource("/view/DashBordForm.fxml"));
        Stage stage2 = (Stage) anpOrderManage.getScene().getWindow();

        stage2.setScene(new Scene(anchorPane));
        stage2.setTitle("Dashboard Form");
        stage2.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtOrderId.setText("");
        txtOrderDate.setText("");
        comCustId.setValue(null);
        comPayId.setValue(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String oId = txtOrderId.getText();

        try {
            boolean isDeleted = OrderRepo.delete(oId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Orders deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllOrders();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String oId = txtOrderId.getText();
        String date = txtOrderDate.getText();
        String CusId = comCustId.getValue();
        String PayId = comPayId.getValue();

        Order order = new Order(oId,date,CusId,PayId);

        try {
            boolean isSaved = OrderRepo.save(order);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Orders is saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllOrders();
    }

    private void loadAllOrders() {
        ObservableList<OrderTm> obList = FXCollections.observableArrayList();
        try {
            List<Order> orderList = OrderRepo.getAll();
            for( Order order : orderList){
                OrderTm tm = new OrderTm(
                        order.getoId(),
                        order.getdate(),
                        order.getCusId(),
                        order.getPayId()
                );
                obList.add(tm);
            }

            tblOrderManage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String oId = txtOrderId.getText();
        String date = txtOrderDate.getText();
        String CusId = comCustId.getValue();
        String PayId = comPayId.getValue();

        Order order = new Order(oId,date,CusId,PayId);

        try {
            boolean isUpdate = OrderRepo.update(order);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"customer is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllOrders();

    }
    @FXML
    void comCustIdOnAction(ActionEvent event) {
        String id = comCustId.getValue();

        try {
            Customer customer = CustomerRepo.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void comPayIdOnAction(ActionEvent event) {

    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtOrderId.getText();

        Order order = OrderRepo.searchById(id);

        if (order != null) {
            txtOrderId.setText(order.getoId());
            txtOrderDate.setText(order.getdate());
            comCustId.setValue(order.getCusId());
            comPayId.setValue(order.getPayId());

        }else {
            new Alert(Alert.AlertType.INFORMATION,"orders is not found !").show();
        }
    }

}
