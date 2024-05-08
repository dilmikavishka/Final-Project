package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;
import lk.ijse.model.*;
import lk.ijse.model.Tm.PlaceOrderTm;
import lk.ijse.repository.BatchRepo;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.OrderRepo;
import lk.ijse.repository.PlaceOrderRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderFormController {

    @FXML
    private AnchorPane anpPlaceOrder;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnNewCustomer;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXComboBox<?> comCode;

    @FXML
    private JFXComboBox<String> comBatchId;

    @FXML
    private JFXComboBox<String> comCustomerName;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<PlaceOrderTm> tblPlaceOrder;

    @FXML
    private TextField txtQty;

    @FXML
    private JFXComboBox<String> comCustomerTel;
    private ObservableList<PlaceOrderTm> obList = FXCollections.observableArrayList();


    public void initialize() {
        getCurrentOrderIds();
        getCustomerTel();
        getBatchIds();
        setDate();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("baId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    private void getBatchIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> batIdList = BatchRepo.getIds();

            for (String id : batIdList) {
                obList.add(id);
            }
            comBatchId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerTel() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> telList = CustomerRepo.geTel();

            for (String tel : telList) {
                obList.add(tel);
            }

            comCustomerTel.setItems(obList);

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    String nextOrderId = "";
    private void getCurrentOrderIds() {
        try {
            String currentId = OrderRepo.getCurrentId();

            nextOrderId = generateNexrOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNexrOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O");
            int idNum = Integer.parseInt(split[1]);
            return "O" + String.format("%03d", ++idNum);
        }
        return "O001";
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String batId = comBatchId.getValue();
        String description = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> types = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(types.orElse(no) == yes) {
                int selectedIndex = tblPlaceOrder.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblPlaceOrder.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0;i<tblPlaceOrder.getItems().size(); i++){
            if (batId.equals(colCode.getCellData(i))) {

                PlaceOrderTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotal(total);

                tblPlaceOrder.refresh();

                calculateNetTotal();
                return;
            }
        }

        PlaceOrderTm tm = new PlaceOrderTm(batId, description, qty, unitPrice, total, btnRemove);
        obList.add(tm);

        tblPlaceOrder.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");

    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));


    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/view/CustomerForm.fxml"));


        anpPlaceOrder.getChildren().clear();
        anpPlaceOrder.getChildren().add(customerPane);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = nextOrderId;
        String cusId = lblCustomerId.getText();
        Date date = Date.valueOf(LocalDate.now());


        var order = new Order(orderId,date,cusId);

        List<OredrDetail> odList = new ArrayList<>();

        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            PlaceOrderTm tm = obList.get(i);

            OredrDetail od = new OredrDetail(

                    tm.getBaId(),
                    orderId,
                    tm.getQty()
            );

            odList.add(od);
        }

        PlaceOrder po = new PlaceOrder(order, odList);


        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(po);
            System.out.println("pppp");
            if (isPlaced){
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                obList.clear();
                tblPlaceOrder.setItems(obList);
                calculateNetTotal();
                getCurrentOrderIds();
                generateBill(orderId);

            }else{
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);

    }

    @FXML
    void comBatchIdOnAction(ActionEvent event) {
        String batId = comBatchId.getValue();

        try {
            Batch batch = BatchRepo.searchById(batId);
            if(batch != null) {
                lblDescription.setText(batch.getDes());
                lblUnitPrice.setText(String.valueOf(batch.getPrice()));
                lblQtyOnHand.setText(String.valueOf(batch.getQtyOnHand()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void comCustomerNameOnAction(ActionEvent event) {
        String name = comCustomerName.getValue();
        try {
            Customer customer = CustomerRepo.searchByName(name);

            lblCustomerId.setText(customer.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void comCustomerTelOnAction(ActionEvent event) {
        String tel = comCustomerTel.getValue();
        try {
            Customer customer = CustomerRepo.searchByTel(tel);

            lblCustomerId.setText(customer.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateBill(String orderId) {
        try {
            String netTotal = calculateNetTotal(orderId);

            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/PlaceOrder.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("orderId", orderId);
            parameters.put("total", netTotal);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String calculateNetTotal(String orderId) throws SQLException {
        return PlaceOrderRepo.calculateNetTotal(orderId);
    }
}
