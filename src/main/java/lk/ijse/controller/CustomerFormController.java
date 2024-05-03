package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.Customer;
import lk.ijse.model.Tm.CustomerTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.OrderRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    @FXML
    private AnchorPane anpCustomerManage;

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
    private TableColumn<?, ?> colCustomerAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colCustomerTel;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerTel;

    @FXML
    private Label lblCustomer;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();

    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for(Customer customer : customerList){
                CustomerTm tm = new CustomerTm(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getTel()
                );
                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        this.colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.colCustomerTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));


        anpCustomerManage.getChildren().clear();
        anpCustomerManage.getChildren().add(dashboardPane);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerTel.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllCustomers();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();

        Customer customer = new Customer(id,name,tel,address);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer is saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllCustomers();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();

        Customer customer = new Customer(id,name,tel,address);

        try {
            boolean isUpdate = CustomerRepo.update(customer);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"customer is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllCustomers();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtCustomerId.getText();

        Customer customer = CustomerRepo.searchById(id);

        if (customer != null) {
            txtCustomerId.setText(customer.getId());
            txtCustomerName.setText(customer.getName());
            txtCustomerTel.setText(customer.getTel());
            txtCustomerAddress.setText(customer.getAddress());

        }else {
            new Alert(Alert.AlertType.INFORMATION,"customer is not found !").show();
        }
    }

}
