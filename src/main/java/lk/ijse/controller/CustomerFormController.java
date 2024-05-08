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
import lk.ijse.Util.Regex;
import lk.ijse.model.Customer;
import lk.ijse.model.Tm.CustomerTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.OrderRepo;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.jfoenix.svg.SVGGlyphLoader.clear;

public class CustomerFormController {

    @FXML
    private AnchorPane anpCustomerManage;

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
        getCurrentCusIds();

    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for(Customer customer : customerList){
                CustomerTm tm = new CustomerTm(
                        customer.getId(),
                        customer.getName(),
                        customer.getTel(),
                        customer.getAddress()
                        );
                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerTel.setText("");
        txtCustomerAddress.setText("");
    }


    @FXML
    void txtCustomerNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtCustomerName);
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtCustomerName)) return false;
        return true;
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
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();

       // Customer customer = new Customer(id,name,tel,address);

        if (isValied()) {
            boolean isSaved = CustomerRepo.save(new Customer(id,name,tel,address));

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                clear();
            }
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


    private void getCurrentCusIds() {
        try {
            String currentId = CustomerRepo.getCurrentId();

            String nextCusId = generateNexrCusId(currentId);
            txtCustomerId.setText(nextCusId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNexrCusId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("C");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "C" + String.format("%03d", ++idNum);
        }
        return"C001";
    }
}
