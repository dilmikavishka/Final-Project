package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Order;
import lk.ijse.model.Payment;
import lk.ijse.model.Supplier;
import lk.ijse.model.Tm.SupplierTm;
import lk.ijse.repository.OrderRepo;
import lk.ijse.repository.SupplierRepo;
import lk.ijse.repository.PaymentRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private AnchorPane anpSupplierManage;

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
    private TableColumn<?, ?> colPayId;

    @FXML
    private TableColumn<?, ?> colSupDate;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableColumn<?, ?> colSupTel;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private JFXComboBox<String> comPayId;

    @FXML
    private TextField txtSupDate;

    @FXML
    private TextField txtSupId;

    @FXML
    private TextField txtSupName;

    @FXML
    private TextField txtSupTel;


    public void initialize() {
        setCellValueFactory();
        loadAllSupplier();
        getPayIds();
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("SupId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colPayId.setCellValueFactory(new PropertyValueFactory<>("payId"));
    }

    private void getPayIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> payList = PaymentRepo.getIds();
            for (String id : payList){
                obList.add(id);
            }
            comPayId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));


        anpSupplierManage.getChildren().clear();
        anpSupplierManage.getChildren().add(dashboardPane);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtSupId.setText("");
        txtSupName.setText("");
        txtSupDate.setText("");
        txtSupTel.setText("");
        comPayId.setValue(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String SupId = txtSupId.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(SupId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllSupplier();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String SupId = txtSupId.getText();
        String name = txtSupName.getText();
        Date date = Date.valueOf(txtSupDate.getText());
        String tel = txtSupTel.getText();
        String payId = comPayId.getValue();


        Supplier supplier = new Supplier(SupId,name,date,tel,payId);

        try {
            boolean isSaved = SupplierRepo.save(supplier);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier is saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllSupplier();

    }


    @FXML
    void comPayIdOnAction(ActionEvent event) {
        String payId = comPayId.getValue();

        try {
            Payment payment = PaymentRepo.searchById(payId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void loadAllSupplier() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            for( Supplier supplier : supplierList){
                SupplierTm tm = new SupplierTm(
                        supplier.getSupId(),
                        supplier.getName(),
                        supplier.getDate(),
                        supplier.getTel(),
                        supplier.getPayId()
                );
                obList.add(tm);
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String SupId = txtSupId.getText();
        String name = txtSupName.getText();
        Date date = Date.valueOf(txtSupDate.getText());
        String tel = txtSupTel.getText();
        String payId = comPayId.getValue();


        Supplier supplier = new Supplier(SupId,name,date,tel,payId);

        try {
            boolean isUpdate = SupplierRepo.update(supplier);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier is update").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllSupplier();

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtSupId.getText();

        Supplier supplier = SupplierRepo.searchById(id);

        if (supplier != null) {
            txtSupId.setText(supplier.getSupId());
            txtSupName.setText(supplier.getName());
            txtSupDate.setText(String.valueOf(supplier.getDate()));
            txtSupTel.setText(supplier.getTel());
            comPayId.setValue(supplier.getPayId());

        }else {
            new Alert(Alert.AlertType.INFORMATION,"Supplier is not found !").show();
        }

    }

}
