package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainFormController {

    @FXML
    private AnchorPane anp1;

    @FXML
    private AnchorPane anp2;

    @FXML
    private AnchorPane anpMain;

    @FXML
    private JFXButton btnBatch;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashbord;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnMachine;

    @FXML
    private JFXButton btnMaterials;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnPlaceOrder;


    public void initialize() throws IOException {
        loadDashboardForm();
    }

    private void loadDashboardForm() throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(dashboardPane);
    }

    @FXML
    void btnBatchOnAction(ActionEvent event) throws IOException {
        AnchorPane batchPane = FXMLLoader.load(this.getClass().getResource("/view/BatchForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(batchPane);

    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/view/CustomerForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(customerPane);

    }

    @FXML
    void btnDashbordOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(dashboardPane);

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        AnchorPane employeePane = FXMLLoader.load(this.getClass().getResource("/view/EmployeeForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(employeePane);
    }

    @FXML
    void btnMachineOnAction(ActionEvent event) throws IOException {
        AnchorPane machinePane = FXMLLoader.load(this.getClass().getResource("/view/MachineForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(machinePane);
    }

    @FXML
    void btnMaterialsOnAction(ActionEvent event) throws IOException {
        AnchorPane materialsPane = FXMLLoader.load(this.getClass().getResource("/view/MaterialForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(materialsPane);
    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) throws IOException {
        AnchorPane orderPane = FXMLLoader.load(this.getClass().getResource("/view/ OderForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(orderPane);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        AnchorPane paymentPane = FXMLLoader.load(this.getClass().getResource("/view/PaymentForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(paymentPane);
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane supplierPane = FXMLLoader.load(this.getClass().getResource("/view/SupplierForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(supplierPane);
    }


    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane supplierPane = FXMLLoader.load(this.getClass().getResource("/view/PlaceOrderForm.fxml"));


        anpMain.getChildren().clear();
        anpMain.getChildren().add(supplierPane);

    }


}
