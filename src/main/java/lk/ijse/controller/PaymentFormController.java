package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Payment;
import lk.ijse.model.Tm.PaymentTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.PaymentRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PaymentFormController {

    public TextField txtPaymentSarch;
    @FXML
    private AnchorPane apnPayment;

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
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtPaymentDate;

    @FXML
    private ChoiceBox<String> choiseType;

    @FXML
    private TextField txtPaymentID;
    @FXML
    private TextField txtStatus;

    public void initialize() {
        setCellValueFactory();
        loadAllPayments();

        ObservableList<String> paymentTypes = FXCollections.observableArrayList("Cash","Card");
        choiseType.setItems(paymentTypes);
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));


    }

    private void loadAllPayments() {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();
        try {
            List<Payment> payList = PaymentRepo.getAll();
            for (Payment payment : payList){
                PaymentTm tm = new PaymentTm(
                        payment.getPaymentId(),
                       payment.getPaymentDate(),
                        payment.getAmount(),
                        payment.getType()
                );
                obList.add(tm);
            }
            tblPayment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));


        apnPayment.getChildren().clear();
        apnPayment.getChildren().add(dashboardPane);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtPaymentID.setText("");
        txtPaymentDate.setText("");
        txtAmount.setText("");
        choiseType.setValue(null);
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtPaymentID.getText();

        try {
            boolean isDeleted = PaymentRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"payment is deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtPaymentID.getText();
        String date = txtPaymentDate.getText();
        
        double amount = 0;
        if (!txtAmount.getText().isEmpty()) {
            amount = Double.parseDouble(txtAmount.getText());
        }

        String type =choiseType.getValue();

        Payment payment = new Payment(id,date,amount,type);

        try {
            boolean isSaved = PaymentRepo.save(payment);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"payment is saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtPaymentID.getText();
        String date = txtPaymentDate.getText();

        double amount = 0;
        if (!txtAmount.getText().isEmpty()) {
            amount = Double.parseDouble(txtAmount.getText());
        }


        String type = choiseType.getValue();

        Payment payment = new Payment(id,date,amount,type);

        try {
            boolean isUpdate = PaymentRepo.update(payment);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"payment is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    @FXML
    public void txtPaymentSarchOnAction(ActionEvent actionEvent) {
        String id = txtPaymentID.getText();

        try {
            Payment payment = PaymentRepo.searchById(id);
            if (payment != null){
                txtPaymentID.setText(payment.getPaymentId());
                txtPaymentDate.setText(payment.getPaymentDate());
                txtAmount.setText(String.valueOf(payment.getAmount()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"payment is not found").show();
        }
    }
}
